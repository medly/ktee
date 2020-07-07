# KTee

KTee is Tee for Kotlin pipelines. 

> If you have ever used unix command line `tee`, you know what we mean.


## Why?

Often times we need to break a perfect computation pipeline just to be able to log the intermediate values. For example, lets take a look at this code:

```kotlin
(1..10)
    .filter { it % 2 == 0 }
    .map { it * 2 }
    .reduce(Int::plus)
```

If we want to print the result of `filter` or `map` we need to either capture the result into an intermediate `val` or add a `.let { }` with logging statements.

KTee simplifies printing intermediate values dramatically.

## How?

Just `.tee()` it. Seriously! Try this:

```kotlin
(1..10)
    .filter { it % 2 == 0 }.tee()
    .map { it * 2 }.tee()
    .reduce(Int::plus).tee()
```

Which produces following output on the console:

```text
[2, 4, 6, 8, 10]
[4, 8, 12, 16, 20]
60
```

## Can I Customize the output?

We can customize the way `tee` prints using markers and lambda blocks to return custom log messages.

```kotlin
(1..10)
    .filter { it % 2 == 0 }.tee("even numbers: ")
    .map { it * 2 }.tee("doubles >>>>> ")
    .reduce(Int::plus).tee {"the result is $it"}
```               

Produces:

```
even numbers: [2, 4, 6, 8, 10]
doubles >>>>> [4, 8, 12, 16, 20]
the result is 60
```

## Can I tee to a `logger`?

We can also log to a custom logger instance (slf4j) instead of `stdout`

```kotlin
(1..10)
    .filter { it % 2 == 0 }.teeToDebug(logger)
    .map { it * 2 }.teeToTrace(logger)
    .reduce(Int::plus).teeToInfo(logger) { "the result is $it" }
```

Produces:

```
[main] DEBUG ktee.KTeeTest - [2, 4, 6, 8, 10]
[main] TRACE ktee.KTeeTest - [4, 8, 12, 16, 20]
[main] INFO ktee.KTeeTest - the result is 60
```

> This output was produced using `slf4j-simple` binding. Your output pattern may look different depending on logger's configuration

## How do I add it to my project?

Just add [jitpack](https://jitpack.io/#com.medly/ktee/v1.0.0) as the last repo in your repositories section of `build.gradle` and add `com.medly:ktee:1.0.0` as a dependency:

```
repositories {		
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.medly:ktee:1.0.0'
}
```        
