# Filters

Illustrates the use of a Servlet filter, which wraps the processing of every request, including the
processing done by the Spring MVC `DispatcherServlet`.

## Contents

* [`ExampleFilter.kt`](ExampleFilter.kt) - an `HttpFilter` that measures the request processing duration and,
  after calling `chain.doFilter`, logs the method, URI, response status, duration, and the selected handler.

## Observations

* Code before `chain.doFilter` runs before the request is handled; code after it runs once the request is handled.
* Filters run *outside* Spring MVC, so they don't know which handler was selected. This filter obtains that
  information from a request attribute set by the interceptor in [`../interceptors`](../interceptors).
* Filter beans are automatically registered by Spring Boot in the Servlet container.
