package pt.isel.daw.servlets

import org.eclipse.jetty.ee10.servlet.FilterHolder
import org.eclipse.jetty.ee10.servlet.ServletContextHandler
import org.eclipse.jetty.ee10.servlet.ServletHolder
import org.eclipse.jetty.server.Server
import org.slf4j.LoggerFactory

private val log = LoggerFactory.getLogger("main")

/**
 * Creating a server using the Jetty library.
 * This code is specific to the Jetty library and *not* portable.
 */
fun main() {
    val server = Server(8080).apply {
        handler = ServletContextHandler(ServletContextHandler.NO_SESSIONS).apply {
            setContextPath("/")
            addServlet(ServletHolder("example-servlet", ExampleServlet()), "/api/servlet")
            addServlet(ServletHolder("example-sse", SseServlet()), "/api/sse")
            addServlet(
                ServletHolder("example-sse-async", SseAsyncServlet()).apply { isAsyncSupported = true },
                "/api/sse-async",
            )
            // All filters in the chain must also support async, otherwise startAsync throws IllegalStateException
            addFilter(FilterHolder(ExampleFilter()).apply { isAsyncSupported = true }, "/*", null)
        }
    }
    server.start()
    log.info("server started, waiting for termination...")
    server.join()
}