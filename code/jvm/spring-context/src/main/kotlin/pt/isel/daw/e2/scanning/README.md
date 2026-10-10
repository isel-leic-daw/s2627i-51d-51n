# Component scanning

Illustrates the use of the Spring Context with *component scanning*, instead of explicitly registering each class.

## Contents

* [`Example.kt`](Example.kt) defines:
  * `ComponentA`, implementing `InterfaceA`, annotated with `@Component`.
  * `ComponentB`, depending on an `InterfaceA`, also annotated with `@Component`.
  * A `main` function that calls `context.scan("pt.isel.daw.e2")` followed by `context.refresh()`, and then obtains
    a `ComponentB` instance.

## Observations

* The bean definitions are not added explicitly to the context. Instead, the package is scanned for classes
  annotated with `@Component`.
* The end result is similar to explicitly registering the `ComponentA` and `ComponentB` classes, as done in `e1/first`.

## Experiments

* Remove the call to `context.scan` and run the example.
* Remove the `@Component` annotation from one of the classes and run the example.
