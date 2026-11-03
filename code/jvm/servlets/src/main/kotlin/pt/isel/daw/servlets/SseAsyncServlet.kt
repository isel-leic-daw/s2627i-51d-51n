package pt.isel.daw.servlets

import jakarta.servlet.AsyncContext
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.PrintWriter
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

class SseAsyncServlet : HttpServlet() {
    override fun doGet(
        req: HttpServletRequest,
        resp: HttpServletResponse,
    ) {
        // QUESTION which thread runs doGet and which thread runs send?
        log.info("doGet: starting on thread '{}'", Thread.currentThread().name)
        resp.status = 200
        resp.addHeader("content-type", "text/event-stream")
        // QUESTION what happens if this servlet or one of the filters is not marked as async supported?
        val asyncContext = req.startAsync()
        // QUESTION what happens if the timeout is not changed and the stream lasts more than 30 seconds?
        asyncContext.timeout = 0
        val writer = PrintWriter(asyncContext.response.outputStream)
        send(asyncContext, writer, 0)
        // QUESTION why does ExampleFilter log 'after chain call' before the stream ends?
        log.info("doGet: ending, response is still open")
    }

    private fun send(
        asyncContext: AsyncContext,
        writer: PrintWriter,
        index: Int,
    ) {
        if (index >= sseLines.size) {
            log.info("send: all lines sent, completing")
            writer.close()
            asyncContext.complete()
            return
        }
        val line = sseLines[index]
        log.info("send: line {} on thread '{}'", index, Thread.currentThread().name)
        writer.println(line.value)
        writer.flush()
        // PrintWriter does not throw IOException; checkError is used to detect write errors (e.g. client disconnected)
        if (writer.checkError()) {
            log.info("send: write error, completing")
            asyncContext.complete()
            return
        }
        scheduler.schedule(
            { send(asyncContext, writer, index + 1) },
            line.delay.inWholeMilliseconds,
            TimeUnit.MILLISECONDS,
        )
    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(SseAsyncServlet::class.java)
        private val scheduler: ScheduledExecutorService =
            Executors.newSingleThreadScheduledExecutor { runnable ->
                Thread(runnable, "sse-scheduler").apply { isDaemon = true }
            }
    }
}