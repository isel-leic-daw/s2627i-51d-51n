# Controllers

Controllers illustrating the two ends of handler invocation: how handler *arguments* are obtained from the
request, and how handler *return values* are converted into the response.

## Contents

* [`ArgumentResolutionExamplesController.kt`](ArgumentResolutionExamplesController.kt) - handlers mapped under
  `/examples/args`, each one illustrating a way of obtaining arguments:
  * `@PathVariable` - values from the path (e.g. `GET /examples/args/0/123`, `GET /examples/args/7/1/b/abc`).
  * `@RequestParam` - values from the query string, mandatory or optional (nullable),
    or all of them as a `MultiValueMap` (e.g. `GET /examples/args/1?id=42`).
  * `HttpServletRequest` - direct access to the underlying Servlet request.
  * `@RequestBody` - an input model (`StudentInputModel`) deserialized from the JSON request body and validated
    with `@Valid` and Bean Validation annotations (e.g. `POST /examples/args/5`).
  * `ClientIp` - a custom type, resolved by the custom argument resolver in
    [`pipeline/argumentresolvers`](../pipeline/argumentresolvers) (`GET /examples/args/8`).
* [`MessageConversionController.kt`](MessageConversionController.kt) - handlers mapped under `/examples/ret`,
  each one returning a different kind of value:
  * A `String`, written as plain text.
  * An output model (`StudentOutputModel`), serialized to JSON.
  * A `ResponseEntity`, which also controls the status code, the media type, and the response headers.
  * A `URI`, written as a PNG QR code image by a custom message converter.
  * A `CustomOutputModel`, written by a custom JSON message converter with a custom media type.

## Observations

* Handlers just declare the parameters they need and return values; obtaining those parameters and writing those
  values is done by the pipeline (argument resolvers and message converters).
* Due to [`ExampleJacksonCustomizer`](../ExampleJacksonCustomizer.kt), JSON property names use `snake_case`,
  both in request bodies (e.g. `enrollment_year`) and in response bodies (e.g. `student_name`).
* Invalid request bodies (e.g. an empty `name` or an `enrollment_year` before 1970) result in a `400` response,
  without the handler being called.
