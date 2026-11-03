package pt.isel.daw.servlets

import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.PrintWriter

/**
 * Servlet class that uses the SSE specification to send a sequence of events.
 * This servlet will use a request-handling thread for *all* the duration of the SSE Connection.
 * To avoid that we need a way to complete the call to `doGet`, while retaining the ability to
 * write into the `PrintWriter` from a different thread.
 * For that we need to use the async servlet model - see [SseAsyncServlet].
 */
class SseServlet : HttpServlet() {
    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        log.info("SseServer: started handling GET on {}", Thread.currentThread().name)
        PrintWriter(resp.outputStream).use { writer ->
            resp.status = 200
            resp.addHeader("content-type", "text/event-stream")
            sseLines.forEach { line ->
                log.info("SseServer: writing to output stream")
                writer.println(line.value)
                writer.flush()
                if (writer.checkError()) {
                    log.info("SseServer: error writing to output stream, ending GET processing")
                    return@use
                }
                if (line.delay.isPositive()) {
                    Thread.sleep(line.delay.inWholeMilliseconds)
                }
            }
        }
        log.info("SseServer: ending handling GET on {}", Thread.currentThread().name)
    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(SseServlet::class.java)
    }
}