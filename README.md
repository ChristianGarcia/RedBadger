# RedBadger

This solution Coroutines (with Flow) and Live Data to provide a Two-way data binding

Important notes:
* This solution showcases a TDD approach to implementing business logic, as seen in the commit history.
* Especial attention has been put to handle edge cases:
  * Incomplete input: Robot ignored
  * Invalid input: Error logged (using Timber) and displayed
  * Empty lines in input
* I/O is performed by using `InputStream` and `OutputStream`, so that I/O operations can be used in in diverse types (files, plain `String`s, etc) and decided on the call site client.
* I/O uses `Reader#useLines()` as opposed to `Reader#readLines()` to allow for very large inputs.
  * `readLines` reads all lines eagerly as a `List<String>`
  * `useLines` reads lines lazily as a `Sequence<String>`
* Because the core logic uses plain Kotlin, this test can be executed either as:
  * An Android app with UI (by launching the app module)
  * A standalone class (by launching Main.kt)
* DI using Dagger-Android

## Android app
Uses architecture components to interact between UI and business logic:

> Data binding <-> ViewModel <-> Flow (coroutines) <-> Business logic

## Standalone Main class
Reads and writes to files
> `app/src/main/res/raw/robots_input.txt` -> `app/build/robots_output.txt`)
