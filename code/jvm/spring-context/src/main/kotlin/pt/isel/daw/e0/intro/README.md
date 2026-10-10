# Introductory example to Inversion of Control

This package contains an introductory example to Inversion of Control,
divided in multiple steps

* Step 0
  * Initial design for a `PasswordService` class with no inversion of control.

* Step 1
  * Applying the inversion of control design technique to the `PasswordService` class.

* Step 2
  * Applying the inversion of control design technique to the example `PasswordTransformer` implementation.

* Step 3
  * Only in tests. No changes in the `main` code.
  * Using the Spring Context container for the composition.

* Step 4
    * `main` code: adds `@Component` annotation to some classes
    * Using component scanning with the Spring Context container.