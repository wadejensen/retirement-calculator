### FP
##### A very hacky fork of a subset of Mario Arias' https://github.com/MarioAriasC/funKTionale
##### All kudos belongs to him.
##### The fork was necessary in order to compile against the Kotlin common platform module.
Since Arrow and the other Kotlin functional libraries only support the JVM,
this was necessary to allow the use of several typeclasses in Kotlin JS.

Includes:
* Disjunction (based on Scalaz \/ )
* Either (Based on the Scala std lib 2.11 with no right biasing )
* Option (Based on Scala std lib )
* Try (Not at all like Scala std lib Try)

Note that in Scala the following does not throw but instead swallows the exception:
```scala
val tryString = Try {
  "Hello world"
}
// tryString: Success[String]

val mappedOverExceptional = tryString.map { throw new Exception("Bang!!") }
// mappedOverExceptional: Failure(Throwable)
```

This also means the Scala Try is not technically a *Real Monad&trade;*.

Instead I created a type alias over Disjunction<Throwable, T>. 
(This snippet is my only real contribution here)
```kotlin
typealias Try<T> = Disjunction<Throwable, T>

fun <T> Try<T>.isSuccess(): Boolean = this.isRight()
fun <T> Try<T>.isFailure(): Boolean = this.isLeft()

fun <T> Try<T>.success(): T         = this.component2()!!
fun <T> Try<T>.failure(): Throwable = this.component1()!!

inline fun <T> Try(body: () -> T): Try<T> = try {
    Disjunction.Right(body())
} catch (t: Throwable) {
    Disjunction.Left(t)
}
```

Note:
I definitely hacked up several tests cases and removed some functionality.
Please don't use this for anything serious.

The real goal would be to close this ticket and get [Arrow](https://github.com/arrow-kt/arrow) to become a multiplatform module:
https://github.com/arrow-kt/arrow/issues/1007
