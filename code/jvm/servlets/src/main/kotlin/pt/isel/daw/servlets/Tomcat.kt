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

fun main() {
    val tomcat = Tomcat().apply {
        connector = Connector()
        connector.port = 8088
        val context = addContext("", null)
        addServlet("", SERVLET_NAME, ExampleServlet())
        context.addServletMappingDecoded("/*", SERVLET_NAME)

        val tmpDir = System.getProperty("java.io.tmpdir")
        setBaseDir(tmpDir)

        context.addFilterDef(
            FilterDef().apply {
                filter = ExampleFilter()
                filterName = FILTER_NAME
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