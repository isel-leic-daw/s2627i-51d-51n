# Argument resolvers

Illustrates how to extend Spring MVC so that handlers can have parameters of custom types.

## Contents

* [`ClientIpArgumentResolver.kt`](ClientIpArgumentResolver.kt) - a `HandlerMethodArgumentResolver` that:
  * Supports parameters of type [`ClientIp`](../../ClientIp.kt) (`supportsParameter`).
  * Creates a `ClientIp` from the remote address of the underlying `HttpServletRequest` (`resolveArgument`).

## Observations

* With this resolver, a handler just declares a `ClientIp` parameter, without depending on `HttpServletRequest`
  (see `handler8` in [`ArgumentResolutionExamplesController`](../../controllers/ArgumentResolutionExamplesController.kt)).
* The same technique is useful for other request derived information, such as the authenticated user.
* Argument resolvers need to be explicitly registered, which is done in
  [`SpringBootMvcIntroApplication`](../../SpringBootMvcIntroApplication.kt).
