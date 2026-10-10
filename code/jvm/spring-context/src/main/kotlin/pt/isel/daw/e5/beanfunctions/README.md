# `@Bean` functions

Illustrates the use of `@Bean` annotated functions to provide the container with a *recipe* to create a bean.
This is useful for classes that cannot be annotated with `@Component`, such as library classes
(e.g. `java.net.http.HttpClient`), or that need custom construction logic (e.g. using a builder).

## Contents

* [`BeanFunctions.kt`](BeanFunctions.kt) defines:
  * The `HttpClientService` interface and the `DefaultHttpClientService` component, which depends on an `HttpClient`.
  * The `BeanConfig` component, with two `@Bean` functions:
    * `cookieHandler()`, creating a `CookieManager`.
    * `httpClient(cookieHandler)`, creating an `HttpClient` using its builder. The `CookieHandler` parameter is
      provided by the container, using the previous `@Bean` function.
  * A `main` function that obtains an `HttpClientService` and makes a `GET` request to `https://httpbin.org/get`.

## Observations

* `@Bean` functions can have parameters, which are resolved by the container just like constructor parameters.
* The class containing the `@Bean` functions must itself be known by the context (here, via `@Component` and scanning).
* Both the `main` function and the test perform a real HTTP request, so they require network access.
