package ktee

import org.slf4j.Logger

/**
 * Prints the value to the stdout and returns the same value. Useful when chaining
 * methods. For example:
 *
 * myList.map(fn).tee().reduce(fn)
 *
 * myList.map(fn).tee(">>> ").reduce(fn)
 *
 */
fun <T> T.tee(marker: String = "") = apply { println(marker + this) }

/**
 *
 * executes the lambda with the value of the chain and writes the
 *
 */
inline fun <T> T.tee(fn: (T) -> String) = apply { println(fn(this)) }

/**
 * logs the value to the given logger at info level. Message can be customized using message parameter
 */
fun <T> T.teeToInfo(logger: Logger, message: String = "{}")
        = apply { logger.info(message, this) }

/**
 * Evaluates the lambda and logs the result (of evaluation) to the given logger at info level
 *
 */
inline fun <T> T.teeToInfo(logger: Logger, fn: (T) -> String)
        = apply { logger.info(fn(this), this) }

/**
 * logs the value to the given logger at info level. Message can be customized using message parameter
 */
fun <T> T.teeToDebug(logger: Logger, message: String = "{}")
        = apply { logger.debug(message, this) }

/**
 * Evaluates the lambda and logs the result (of evaluation) to the given logger at debug level
 *
 */
inline fun <T> T.teeToDebug(logger: Logger, fn: (T) -> String)
        = apply { logger.debug(fn(this), this) }

/**
 * logs the value to the given logger at trace level. Message can be customized using message parameter
 */
fun <T> T.teeToTrace(logger: Logger, message: String = "{}")
        = apply { logger.trace(message, this) }

/**
 * Evaluates the lambda and logs the result (of evaluation) to the given logger at trace level
 *
 */
inline fun <T> T.teeToTrace(logger: Logger, fn: (T) -> String)
        = apply { logger.trace(fn(this), this) }




