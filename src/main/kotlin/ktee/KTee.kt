package ktee

import ktee.KTee.prefix
import org.slf4j.Logger

/** Prefix prepended to every tee output. */
object KTee { var prefix = "" }

/**
 * Prints the value to the standard output and returns the same value.
 * Useful when chaining methods.
 *
 * Note: This function would be replaced with a no-op (no operation) version at build time.
 *
 * Example usage:
 * ```
 * myList.map(fn).tee().reduce(fn)
 *
 * myList.map(fn).tee(">>> ").reduce(fn)
 * ```
 *
 * @param marker An optional string to prepend to the value when printing.
 * @return The original value.
 */
fun <T> T.tee(marker: String = "") = apply { println(prefix + marker + this) }

/**
 * Executes the provided lambda with the value and prints the result to the standard output.
 * Returns the original value.
 *
 * Note: This function would be replaced with a no-op (no operation) version at build time.
 *
 * Example usage:
 * ```
 * myList.map(fn).tee { "Value: $it" }.reduce(fn)
 * ```
 *
 * @param fn A lambda function that takes the value and returns a string to be printed.
 * @return The original value.
 */
inline fun <T> T.tee(fn: (T) -> String) = apply { println(prefix + fn(this)) }

/**
 * Logs the value at the INFO level using the provided logger.
 * The message can be customized with the `message` parameter.
 *
 * Note: This function would be replaced with a no-op (no operation) version at build time.
 *
 * Example usage:
 * ```
 * myValue.teeToInfo(logger, "Processed value: {}")
 * ```
 *
 * @param logger The SLF4J logger to use.
 * @param message The log message template. Defaults to "{}".
 * @return The original value.
 */
fun <T> T.teeToInfo(logger: Logger, message: String = "{}") = apply { logger.info(prefix + message, this) }

/**
 * Evaluates the lambda with the value and logs the result at the INFO level using the provided logger.
 * Returns the original value.
 *
 * Note: This function would be replaced with a no-op (no operation) version at build time.
 *
 * Example usage:
 * ```
 * myValue.teeToInfo(logger) { "Processed value: $it" }
 * ```
 *
 * @param logger The SLF4J logger to use.
 * @param fn A lambda function that takes the value and returns a string to be logged.
 * @return The original value.
 */
inline fun <T> T.teeToInfo(logger: Logger, fn: (T) -> String) = apply { logger.info(prefix + fn(this), this) }

/**
 * Logs the value at the DEBUG level using the provided logger.
 * The message can be customized with the `message` parameter.
 *
 * Note: This function would be replaced with a no-op (no operation) version at build time.
 *
 * Example usage:
 * ```
 * myValue.teeToDebug(logger, "Debugging value: {}")
 * ```
 *
 * @param logger The SLF4J logger to use.
 * @param message The log message template. Defaults to "{}".
 * @return The original value.
 */
fun <T> T.teeToDebug(logger: Logger, message: String = "{}") = apply { logger.debug(prefix + message, this) }

/**
 * Evaluates the lambda with the value and logs the result at the DEBUG level using the provided logger.
 * Returns the original value.
 *
 * Note: This function would be replaced with a no-op (no operation) version at build time.
 *
 * Example usage:
 * ```
 * myValue.teeToDebug(logger) { "Debugging value: $it" }
 * ```
 *
 * @param logger The SLF4J logger to use.
 * @param fn A lambda function that takes the value and returns a string to be logged.
 * @return The original value.
 */
inline fun <T> T.teeToDebug(logger: Logger, fn: (T) -> String) = apply { logger.debug(prefix + fn(this), this) }

/**
 * Logs the value at the TRACE level using the provided logger.
 * The message can be customized with the `message` parameter.
 *
 * Note: This function would be replaced with a no-op (no operation) version at build time.
 *
 * Example usage:
 * ```
 * myValue.teeToTrace(logger, "Tracing value: {}")
 * ```
 *
 * @param logger The SLF4J logger to use.
 * @param message The log message template. Defaults to "{}".
 * @return The original value.
 */
fun <T> T.teeToTrace(logger: Logger, message: String = "{}") = apply { logger.trace(prefix + message, this) }

/**
 * Evaluates the lambda with the value and logs the result at the TRACE level using the provided logger.
 * Returns the original value.
 *
 * Note: This function would be replaced with a no-op (no operation) version at build time.
 *
 * Example usage:
 * ```
 * myValue.teeToTrace(logger) { "Tracing value: $it" }
 * ```
 *
 * @param logger The SLF4J logger to use.
 * @param fn A lambda function that takes the value and returns a string to be logged.
 * @return The original value.
 */
inline fun <T> T.teeToTrace(logger: Logger, fn: (T) -> String) = apply { logger.trace(prefix + fn(this), this) }

/**
 * Executes the given lambda function. Typically used for debugging purposes to
 * encapsulate a block of code that should be executed only during debugging.
 *
 * Note: This function would be replaced with a no-op (no operation) version at build time.
 *
 * Example usage:
 * ```
 * debug {
 *     println("Debugging information: $someVariable")
 * }
 * ```
 *
 * @param fn A lambda function that takes no parameters and returns no value.
 */
inline fun debug(fn: () -> Unit) = fn()

/**
 * Executes the given lambda function, which contains code that should only run in release builds.
 *
 * Note: This function will be replaced with an **op** (operational) version in debug builds.
 *
 * Example usage:
 * ```
 * release {
 *     println("This runs only in release mode")
 * }
 * ```
 *
 * @param fn A lambda function that takes no parameters and returns no value.
 */
inline fun release(fn: () -> Unit) = Unit

