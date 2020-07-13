package ktee

import ktee.KTee.Companion.debug
import org.slf4j.Logger

class KTee { companion object { var debug = true } }

/**
 * Prints the value to the stdout and returns the same value. Useful when chaining
 * methods. For example:
 *
 * myList.map(fn).tee().reduce(fn)
 *
 * myList.map(fn).tee(">>> ").reduce(fn)
 *
 */
fun <T> T.tee(marker: String = "") = apply { if (debug) println(marker + this) }

/**
 *
 * executes the lambda with the value of the chain and writes the
 *
 */
inline fun <T> T.tee(fn: (T) -> String) = apply { if (debug) println(fn(this)) }

/**
 * logs the value to the given logger at info level. Message can be customized using message parameter
 */
fun <T> T.teeToInfo(logger: Logger, message: String = "{}")
        = apply { if (debug) logger.info(message, this) }

/**
 * Evaluates the lambda and logs the result (of evaluation) to the given logger at info level
 *
 */
inline fun <T> T.teeToInfo(logger: Logger, fn: (T) -> String)
        = apply { if (debug) logger.info(fn(this), this) }

/**
 * logs the value to the given logger at info level. Message can be customized using message parameter
 */
fun <T> T.teeToDebug(logger: Logger, message: String = "{}")
        = apply { if (debug) logger.debug(message, this) }

/**
 * Evaluates the lambda and logs the result (of evaluation) to the given logger at debug level
 *
 */
inline fun <T> T.teeToDebug(logger: Logger, fn: (T) -> String)
        = apply { if (debug) logger.debug(fn(this), this) }

/**
 * logs the value to the given logger at trace level. Message can be customized using message parameter
 */
fun <T> T.teeToTrace(logger: Logger, message: String = "{}")
        = apply { if (debug) logger.trace(message, this) }

/**
 * Evaluates the lambda and logs the result (of evaluation) to the given logger at trace level
 *
 */
inline fun <T> T.teeToTrace(logger: Logger, fn: (T) -> String)
        = apply { if (debug) logger.trace(fn(this), this) }




