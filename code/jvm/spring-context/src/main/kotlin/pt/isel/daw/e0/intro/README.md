# Introductory example to Inversion of Control

This package contains an introductory example to Inversion of Control (IoC), divided in multiple steps.
The example is a `PasswordService` that sets and checks user passwords, using two dependencies:

* A `PasswordTransformer`, which transforms a plain-text password (e.g. by hashing it) and checks a plain-text
  password against a transformed one.
* A `PasswordStorage`, which stores the transformed password for each user.

The types shared by all steps (`UserId`, `PlainTextPassword`, `TransformedPassword`, `PasswordTransformer`,
`PasswordStorage`, and `HashFunctionSupplier`) are defined in [`Common.kt`](Common.kt).
Each step has a `main` package (e.g. `step0`) and a corresponding test package with a `PasswordServiceTests` class,
where the composition of the objects is done.

## Step 0 - No inversion of control

* [`main`](step0/Services.kt): `PasswordService` creates its own dependencies, `InsecureSha1BasedPasswordTransformer`
  and `InMemoryPasswordStorage`.
* `PasswordService` is coupled to concrete implementations, which cannot be replaced without changing its code.
* The test just creates a `PasswordService` with `PasswordService()`.

## Step 1 - Inversion of control applied to `PasswordService`

* [`main`](step1/Services.kt): `PasswordService` receives its dependencies via constructor parameters,
  typed with the `PasswordTransformer` and `PasswordStorage` interfaces.
* `PasswordService` no longer decides which implementations to use; that responsibility moves to whoever creates it.
* The test does the composition manually, creating the dependencies and passing them to the `PasswordService` constructor.

## Step 2 - Inversion of control applied to the password transformer

* [`main`](step2/Services.kt): the transformer, now called `InsecureHashBasedPasswordTransformer`,
  no longer hardcodes the hash algorithm. Instead, it receives a `HashFunctionSupplier`.
* The test still does the composition manually, choosing the hash function (e.g. `SHA1`) when creating the transformer.

## Step 3 - Composition using the Spring Context container

* Only in tests: there are no changes in the `main` code, which is reused from step 2.
* The test creates an `AnnotationConfigApplicationContext`, registering the `PasswordService`,
  `InsecureHashBasedPasswordTransformer`, and `InMemoryPasswordStorage` classes, and obtains the `PasswordService`
  from it. The container is responsible for creating the instances and passing in the dependencies.
* The `HashFunctionSupplier` is provided by a `@Bean` annotated function, defined in a `BeanProvider` class,
  since it is a functional interface instance and not a class we want to register.

## Step 4 - Component scanning

* [`main`](step4/Services.kt): adds the `@Component` annotation to `PasswordService`,
  `InsecureHashBasedPasswordTransformer`, and `InMemoryPasswordStorage`.
* The test uses `context.scan("pt.isel.daw.e0.intro.step4")` instead of explicitly registering each class.
  The `BeanProvider` class is also annotated with `@Component` so it is found by the scan.
