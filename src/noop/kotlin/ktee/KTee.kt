@file:Suppress("unused", "NOTHING_TO_INLINE", "UNUSED_PARAMETER")

package ktee

import org.slf4j.Logger

object KTee { var prefix = "" }

inline fun debug(fn: () -> Unit) = Unit
inline fun release(fn: ()->Unit) = fn()
inline fun <T> T.tee(fn: (T) -> String) = this
inline fun <T> T.tee(marker: String = "") = this
inline fun <T> T.teeToInfo(logger: Logger, fn: (T) -> String) = this
inline fun <T> T.teeToDebug(logger: Logger, fn: (T) -> String) = this
inline fun <T> T.teeToTrace(logger: Logger, fn: (T) -> String) = this
inline fun <T> T.teeToInfo(logger: Logger, message: String = "{}") = this
inline fun <T> T.teeToDebug(logger: Logger, message: String = "{}") = this
inline fun <T> T.teeToTrace(logger: Logger, message: String = "{}") = this



