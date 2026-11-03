package pt.isel.daw.servlets

import org.apache.catalina.connector.Connector
import org.apache.catalina.startup.Tomcat
import org.apache.tomcat.util.descriptor.web.FilterDef
import org.apache.tomcat.util.descriptor.web.FilterMap
import org.slf4j.LoggerFactory

private val log = LoggerFactory.getLogger("main")

/**
 * Creating a server using the Tomcat library.
 * This code is specific to the Tomcat library and *not* portable.
 */
const val FILTER_NAME = "example-filter"
const val SERVLET_NAME = "example-servlet"
const val SSE_SERVLET_NAME = "example-sse"
const val SSE_ASYNC_SERVLET_NAME = "example-sse-async"

fun main() {
    val tomcat = Tomcat().apply {
        connector = Connector()
        connector.port = 8088
        val context = addContext("", null)
        addServlet("", SERVLET_NAME, ExampleServlet())
        context.addServletMappingDecoded("/api/servlet", SERVLET_NAME)
        addServlet("", SSE_SERVLET_NAME, SseServlet())
        context.addServletMappingDecoded("/api/sse", SSE_SERVLET_NAME)
        addServlet("", SSE_ASYNC_SERVLET_NAME, SseAsyncServlet()).isAsyncSupported = true
        context.addServletMappingDecoded("/api/sse-async", SSE_ASYNC_SERVLET_NAME)

        val tmpDir = System.getProperty("java.io.tmpdir")
        setBaseDir(tmpDir)

        context.addFilterDef(
            FilterDef().apply {
                filter = ExampleFilter()
                filterName = FILTER_NAME
                // All filters in the chain must also support async, otherwise startAsync throws IllegalStateException
                asyncSupported = "true"
            },
        )
        context.addFilterMap(
            FilterMap().apply {
                filterName = FILTER_NAME
                addURLPattern("/*")
            },
        )
    }
    tomcat.start()
    log.info("server started, waiting for termination...")
    tomcat.server.await()
}