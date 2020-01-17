package ktee

import java.io.ByteArrayOutputStream
import java.io.PrintStream

/**
 * executes the fn and returns any output written to stdout
 */
fun trapOut(fn: () -> Unit) = trap(fn, System.out, System::setOut)

/**
 * executes the fn and returns any output written to stderr
 */
fun trapErr(fn: () -> Unit) = trap(fn, System.err, System::setErr)

fun trap(fn: () -> Unit, originalStream: PrintStream, setter: (PrintStream) -> Unit): String {
    val streamCatcher = ByteArrayOutputStream()
    setter(PrintStream(streamCatcher))
    fn()
    setter(originalStream)
    return streamCatcher.toString()
}
