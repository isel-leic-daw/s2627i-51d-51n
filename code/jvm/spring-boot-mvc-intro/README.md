# Project containing Spring MVC usage examples

## Folder and files

* [SpringBootMvcIntroApplication.kt](src/main/kotlin/pt/isel/daw/springbootmvcintro/SpringBootMvcIntroApplication.kt) - 
application startup, including the registration of pipeline elements such as argument resolvers.
* [ExampleController](src/main/kotlin/pt/isel/daw/springbootmvcintro/ExampleController.kt) - Simple controller
illustrating handler mapping and constructor dependency injection.
* [controllers](src/main/kotlin/pt/isel/daw/springbootmvcintro/controllers) - folder with controllers exemplifying 
argument resolution and message conversion, namely from handler return values into response messages.
* [pipeline](src/main/kotlin/pt/isel/daw/springbootmvcintro/pipeline) - folder with some example pipeline elements,
such as argument resolvers and message converters.
* [ExampleJacksonCustomizer](src/main/kotlin/pt/isel/daw/springbootmvcintro/ExampleJacksonCustomizer.kt) - customizes
how the Jackson-based message converter behaves.

Each source folder has its own `README.md` with a more detailed description:

* [`springbootmvcintro`](src/main/kotlin/pt/isel/daw/springbootmvcintro/README.md) - application root package.
* [`controllers`](src/main/kotlin/pt/isel/daw/springbootmvcintro/controllers/README.md) - argument resolution and message conversion examples.
* [`pipeline`](src/main/kotlin/pt/isel/daw/springbootmvcintro/pipeline/README.md) - overview of the pipeline elements.
  * [`filters`](src/main/kotlin/pt/isel/daw/springbootmvcintro/pipeline/filters/README.md) - Servlet filter.
  * [`interceptors`](src/main/kotlin/pt/isel/daw/springbootmvcintro/pipeline/interceptors/README.md) - Spring MVC interceptor.
  * [`argumentresolvers`](src/main/kotlin/pt/isel/daw/springbootmvcintro/pipeline/argumentresolvers/README.md) - custom argument resolver.
  * [`messageconverters`](src/main/kotlin/pt/isel/daw/springbootmvcintro/pipeline/messageconverters/README.md) - custom message converters.
