# Custom annotations and a simple router

Illustrates how the Spring Context can be used to find all the beans marked with a custom annotation.
This is a simplified version of how a web framework (e.g. Spring MVC) discovers controllers and their handler methods.

## Contents

* [`Example.kt`](Example.kt) defines:
  * `@MyController`, a class annotation that is itself annotated with `@Component`,
    so classes annotated with it are also found by component scanning.
  * `@MyHandler(method, pathTemplate)`, a function annotation to mark handler methods with the HTTP method
    and path they handle.
  * Two controllers, `SomeController` and `AnotherController`, with `@MyHandler` annotated methods.
  * `NotAController`, a plain `@Component` that must not be considered a controller.
  * `ControllerProvider`, an interface providing the list of controllers, and `SpringContextBasedControllerProvider`,
    an implementation that uses `ApplicationContext.getBeansWithAnnotation(MyController::class.java)`.
  * `Router`, a component that depends on a `ControllerProvider`.
  * A `main` function that uses reflection to list, for each controller, the method and path handled by each
    `@MyHandler` annotated method.

## Observations

* Annotations can be composed: annotating `MyController` with `@Component` makes every `@MyController`
  class a component.
* `SpringContextBasedControllerProvider` depends on the `ApplicationContext` itself. This should be avoided, since
  application components should not depend on the container. Hiding it behind the `ControllerProvider` interface at
  least keeps `Router` independent of Spring.
* The test checks that only `SomeController` and `AnotherController` are found as controllers.
