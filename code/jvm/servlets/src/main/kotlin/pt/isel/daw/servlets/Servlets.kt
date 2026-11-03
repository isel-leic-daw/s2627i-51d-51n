package pt.isel.daw.servlets

import jakarta.servlet.AsyncEvent
import jakarta.servlet.AsyncListener
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpFilter
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/*
 * The servlet API (interfaces and base classes) were initially defined by [Java Enterprise Edition]
 * (https://www.oracle.com/java/technologies/java-ee-glance.html).
 * They are now maintained by project [Jakarta](https://projects.eclipse.org/projects/ee4j)
 *
 * The code in this file only depends on the servlet API and *is* portable.
 * See the jakarta.servlet.* imports.
 */

/**
 * A servlet is responsible for ultimately handling an HTTP request by populating an [HttpServletResponse]
 * using information from a [HttpServletRequest].
 */
class ExampleServlet : HttpServlet() {
    override fun doGet(
        request: HttpServletRequest,
        response: HttpServletResponse,
    ) {
        log.info(
            "doGet: method='{}', uri='{}'",
            request.method,
            request.requestURI,
        )

        response.status = 200
        // QUESTION what happens if 'charset=utf-8' is removed
        // QUESTION what happens if 'Content-Type' is set to 'application/json'
        response.addHeader("Content-Type", "text/plain; charset=utf-8")
        response.addHeader("Example-Header", "Example-Value")
        response.outputStream.apply {
            write("Olá mundo".toByteArray(Charsets.UTF_8))
            flush()
        }
        log.info("doGet: ending")
    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(ExampleServlet::class.java)
    }
}

/**
 * A servlet filter contributes to the handling of HTTP responses, by using [HttpServletRequest]
 * and eventually mutating [HttpServletResponse] *before* and *after* the request is handled by a server.
 * Multiple filters are organized in a pipeline.
 */
class ExampleFilter : HttpFilter() {
    override fun doFilter(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain,
    ) {
        // Performed before the request is handled by the servlet
        val start = System.nanoTime()
        log.info("doFilter: before chain call")

        // Call the rest of the pipeline
        // QUESTION what happens if a filter does not call chain.doFilter?
        chain.doFilter(request, response)

        // Performed after the servlet returned
        log.info("doFilter: after chain call, took: {} us", elapsedMicros(start))

        // If the servlet started async processing, the response is not yet complete.
        // An AsyncListener is used to be notified when the async processing ends.
        // Note that the filter must be registered with async support for the servlet to be able to start async.
        if (request.isAsyncStarted) {
            request.asyncContext.addListener(
                object : AsyncListener {
                    override fun onComplete(event: AsyncEvent) {
                        log.info("onComplete: async request completed, took: {} us", elapsedMicros(start))
                    }

                    override fun onTimeout(event: AsyncEvent) {
                        log.info("onTimeout: async request timed out, took: {} us", elapsedMicros(start))
                    }

                    override fun onError(event: AsyncEvent) {
                        log.info("onError: async request failed, took: {} us", elapsedMicros(start), event.throwable)
                    }

                    override fun onStartAsync(event: AsyncEvent) {
                        // Only called if startAsync is called again on the same request
                    }
                },
            )
        }
    }

    private fun elapsedMicros(start: Long) = (System.nanoTime() - start) / 1_000

    companion object {
        private val log: Logger = LoggerFactory.getLogger(ExampleFilter::class.java)
    }
}