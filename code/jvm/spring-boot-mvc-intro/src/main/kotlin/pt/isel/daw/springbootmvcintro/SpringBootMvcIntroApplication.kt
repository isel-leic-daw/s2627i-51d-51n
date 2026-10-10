package pt.isel.daw.springbootmvcintro

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@SpringBootApplication
class SpringBootMvcIntroApplication(
    private val argumentResolvers: List<HandlerMethodArgumentResolver>,
    private val interceptors: List<HandlerInterceptor>,
) : WebMvcConfigurer {
    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        argumentResolvers.forEach {
            resolvers.add(it)
        }
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        interceptors.forEach {
            registry.addInterceptor(it)
        }
    }
}

fun main(args: Array<String>) {
    runApplication<SpringBootMvcIntroApplication>(*args)
}
