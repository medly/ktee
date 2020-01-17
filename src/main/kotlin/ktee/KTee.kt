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
fun <T> T.tee(marker: String = ""): T {
    println(marker + this)
    return this
}

/**
 *
 * executes the lambda with the value of the chain and writes the
 *
 */
inline fun <T> T.tee(fn: (T) -> String): T {
    println(fn(this))
    return this
}

/**
 * logs the value to the given logger at info level. Message can be customized using message parameter
 */
fun <T> T.teeToInfo(logger: Logger, message: String = "{}"): T {
    logger.info(message, this)
    return this
}

/**
 * Evaluates the lambda and logs the result (of evaluation) to the given logger at info level
 *
 */
inline fun <T> T.teeToInfo(logger: Logger, fn: (T) -> String): T {
    logger.info(fn(this), this)
    return this
}

/**
 * logs the value to the given logger at info level. Message can be customized using message parameter
 */
fun <T> T.teeToDebug(logger: Logger, message: String = "{}"): T {
    logger.debug(message, this)
    return this
}

/**
 * Evaluates the lambda and logs the result (of evaluation) to the given logger at debug level
 *
 */
inline fun <T> T.teeToDebug(logger: Logger, fn: (T) -> String): T {
    logger.debug(fn(this), this)
    return this
}

/**
 * logs the value to the given logger at trace level. Message can be customized using message parameter
 */
fun <T> T.teeToTrace(logger: Logger, message: String = "{}"): T {
    logger.trace(message, this)
    return this
}

/**
 * Evaluates the lambda and logs the result (of evaluation) to the given logger at trace level
 *
 */
inline fun <T> T.teeToTrace(logger: Logger, fn: (T) -> String): T {
    logger.trace(fn(this), this)
    return this
}




