# Spring Context examples

Examples illustrating Inversion of Control and Dependency Injection, first without any container and then
using the [Spring Context](https://docs.spring.io/spring-framework/reference/core/beans.html) container
(`org.springframework:spring-context`), without Spring Boot.

Each example lives in its own package, with the `main` code in `src/main/kotlin/pt/isel/daw/eN/...`
and the corresponding tests in `src/test/kotlin/pt/isel/daw/eN/...`.

| Example | Description |
|---------|-------------|
| [`e0/intro`](src/main/kotlin/pt/isel/daw/e0/intro/README.md) | Step-by-step introduction to Inversion of Control, from manual composition to the Spring Context container. |
| [`e1/first`](src/main/kotlin/pt/isel/daw/e1/first/README.md) | First use of the Spring Context, with explicitly registered components. |
| [`e2/scanning`](src/main/kotlin/pt/isel/daw/e2/scanning/README.md) | Component discovery via package scanning and the `@Component` annotation. |
| [`e3/lists`](src/main/kotlin/pt/isel/daw/e3/lists/README.md) | Injecting a `List` with all the implementations of an interface. |
| [`e4/router`](src/main/kotlin/pt/isel/daw/e4/router/README.md) | Custom annotations and obtaining beans by annotation, as the basis for a simple router. |
| [`e5/beanfunctions`](src/main/kotlin/pt/isel/daw/e5/beanfunctions/README.md) | `@Bean` annotated functions, to create beans from classes we don't control. |
| [`e6/scopes`](src/main/kotlin/pt/isel/daw/e6/scopes/README.md) | Bean scopes: `singleton` (the default) versus `prototype`. |

## Running

* Run all the tests with `./gradlew test`.
* Run the tests of a single example with, e.g., `./gradlew test --tests 'pt.isel.daw.e3.*'`.
* Most examples also have a `main` function that can be run from the IDE and that logs what the container is doing.

Note that the `e5/beanfunctions` tests perform an HTTP request to `https://httpbin.org` and therefore require network access.

The `build.gradle.kts` file also defines two demo tasks (`demoTask` and `anotherDemoTask`), unrelated to the Spring
Context, which illustrate the difference between Gradle's configuration and execution phases.
