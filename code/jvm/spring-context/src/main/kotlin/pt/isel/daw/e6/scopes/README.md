# Bean scopes

Illustrates the difference between the `singleton` (default) and `prototype` bean scopes.

## Contents

* [`Example.kt`](Example.kt) defines:
  * `ComponentA`, annotated with `@Scope("prototype")`.
  * `ComponentB`, depending on `ComponentA`.
  * `ComponentC`, depending on `ComponentA` and `ComponentB`.
  * `ComponentD`, depending on `ComponentA`, `ComponentB`, and `ComponentC`.
  * A `main` function that obtains a `ComponentD` and logs how many distinct `ComponentA` and `ComponentB`
    instances exist in its dependency graph.

## Observations

* `ComponentA` has the `prototype` scope, so a new instance is created each time it is needed:
  `ComponentB`, `ComponentC`, and `ComponentD` each receive a different instance (3 instances in total).
* `ComponentB` has the default `singleton` scope, so the same instance is shared by `ComponentC` and `ComponentD`
  (1 instance in total).
