# Application root package

Contains the Spring Boot application class, two simple controllers, and the supporting types they use.
The sub-folders contain more focused examples.

## Contents

* [`SpringBootMvcIntroApplication.kt`](SpringBootMvcIntroApplication.kt) - the `@SpringBootApplication` class and
  the `main` function. The class also implements `WebMvcConfigurer`, receiving all the `HandlerMethodArgumentResolver`
  and `HandlerInterceptor` beans via constructor injection and registering them in the Spring MVC pipeline.
* [`ExampleController.kt`](ExampleController.kt) - a `@RestController` handling `GET /`, which depends on a
  `GreetingsService` provided via constructor injection.
* [`AnotherController.kt`](AnotherController.kt) - a `@RestController` handling `GET /another`, showing that
  multiple controllers are discovered and mapped automatically.
* [`GreetingsService.kt`](GreetingsService.kt) and [`DefaultGreetingsService.kt`](DefaultGreetingsService.kt) -
  a service interface and its `@Component` implementation, used by `ExampleController`.
* [`ClientIp.kt`](ClientIp.kt) - a simple type representing the client's IP address, which handlers can receive
  as an argument thanks to a custom argument resolver (see [`pipeline/argumentresolvers`](pipeline/argumentresolvers)).
* [`ExampleJacksonCustomizer.kt`](ExampleJacksonCustomizer.kt) - customizes the Spring Boot managed Jackson
  `ObjectMapper` so that JSON property names use `snake_case` (e.g. `studentName` becomes `student_name`).

## Sub-folders

* [`controllers`](controllers) - controllers illustrating argument resolution and message conversion.
* [`pipeline`](pipeline) - custom elements plugged into the request processing pipeline.

## Observations

* Controllers, services, and pipeline elements are all found by component scanning, because they are in this
  package or in its sub-packages.
* Controllers only depend on the `GreetingsService` interface; the container chooses the implementation.
