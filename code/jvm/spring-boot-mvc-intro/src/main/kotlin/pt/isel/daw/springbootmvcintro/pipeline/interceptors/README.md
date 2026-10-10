# Interceptors

Illustrates the use of a Spring MVC `HandlerInterceptor`, which runs *inside* the `DispatcherServlet`,
after the handler is selected and around its execution.

## Contents

* [`ExampleInterceptor.kt`](ExampleInterceptor.kt) - an interceptor whose `preHandle` function stores the
  selected `HandlerMethod` in a request attribute. Its companion object provides functions to set and get
  that attribute.

## Observations

* Unlike filters, interceptors have access to the selected handler (e.g. the controller method and its annotations).
* Returning `false` from `preHandle` would stop the processing, without calling the handler.
* The stored handler is used by the filter in [`../filters`](../filters) to log which handler processed the request,
  showing how request attributes can be used to share information between pipeline elements.
* Interceptors need to be explicitly registered, which is done in
  [`SpringBootMvcIntroApplication`](../../SpringBootMvcIntroApplication.kt).
