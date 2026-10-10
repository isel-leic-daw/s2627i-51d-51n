# First Spring Context example

Introductory example to the Spring Context container, using explicitly registered components (beans).

## Contents

* [`Example.kt`](Example.kt) defines:
  * Two interfaces, `InterfaceA` and `InterfaceB`.
  * `ComponentA`, implementing `InterfaceA`, with no dependencies.
  * `ComponentB`, implementing `InterfaceB` and depending on an `InterfaceA`.
  * `ComponentC`, depending on both an `InterfaceB` and an `InterfaceA`.
  * A `main` function that:
    * creates an `AnnotationConfigApplicationContext`.
    * registers the three component classes with `context.register`, in an arbitrary order.
    * calls `context.refresh()` so that the registered bean definitions are taken into account.
    * obtains `ComponentC` and `ComponentB` instances via `context.getBean`.
* Each component logs a message when its constructor runs, making it visible when instances are created.

## Observations

* Dependencies are expressed only as constructor parameters typed with *interfaces*. The container finds the
  registered class implementing each interface and resolves the full dependency graph, regardless of the registration order.
* By default beans are *singletons*: calling `getBean` twice for `ComponentC` returns the same instance,
  and the `InterfaceA` instance received by `ComponentB` and `ComponentC` is the same `ComponentA` instance.
  Compare the logged object identities to confirm.
* The test ([`ExampleTests.kt`](../../../../../../../test/kotlin/pt/isel/daw/e1/first/ExampleTests.kt)) checks that
  the dependency graph is correctly resolved.
