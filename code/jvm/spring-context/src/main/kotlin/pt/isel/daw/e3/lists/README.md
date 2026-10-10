# List dependencies

Illustrates how a component can depend on *all* the available implementations of an interface,
by having a constructor parameter of type `List<T>`.

## Contents

* [`Translator.kt`](Translator.kt) defines:
  * The `LanguageTranslator` interface, with a `targetLanguage` property and a `translate` function.
  * The `Translator` component, which receives a `List<LanguageTranslator>` and builds a map from target language
    to translator. Its `translate` function delegates to the translator for the requested language, or returns a
    "Sorry, no available translation..." message.
  * A `main` function that scans the `pt.isel.daw.e3` package and translates `Hello` into multiple languages.
* One file per language translator, each annotated with `@Component`:
  `ChineseTranslator`, `DutchTranslator`, `FinnishTranslator`, `FrenchTranslator`, `GermanTranslator`,
  `ItalianTranslator`, `PortugueseTranslator`, `SpanishTranslator`, and `SwedishTranslator`.

## Observations

* The container injects a list with an instance of every bean implementing `LanguageTranslator`.
* Adding support for a new language only requires adding a new class implementing `LanguageTranslator`
  and annotated with `@Component`. Neither `Translator` nor the composition code needs to change.
