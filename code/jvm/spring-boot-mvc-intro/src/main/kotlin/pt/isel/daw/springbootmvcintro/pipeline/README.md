# Pipeline

Custom elements plugged into the request processing pipeline, i.e., the sequence of steps performed between
receiving an HTTP request and sending the HTTP response.

## Sub-folders

* [`filters`](filters) - a Servlet filter, running *before* and *after* the Spring MVC `DispatcherServlet`.
* [`interceptors`](interceptors) - a Spring MVC interceptor, running after the handler is selected.
* [`argumentresolvers`](argumentresolvers) - a custom argument resolver, producing handler arguments.
* [`messageconverters`](messageconverters) - custom message converters, turning handler return values
  into response bodies.

## Observations

* All these elements are `@Component` classes, discovered by component scanning.
* Filters and message converters are registered automatically by Spring Boot. Interceptors and argument
  resolvers are explicitly registered by
  [`SpringBootMvcIntroApplication`](../SpringBootMvcIntroApplication.kt), via `WebMvcConfigurer`.
