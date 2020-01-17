package ktee

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class KTeeTest {

    val logger: Logger = LoggerFactory.getLogger(javaClass)

    @Test
    fun `should return the original value in chains`() {
        val result = listOf(1, 2, 3).map { it * 2 }.tee { ">> $it" }.reduce(Int::plus).tee()
        assertEquals(12, result)
    }

    @Test
    fun `should write to stdout`() {
        assertEquals("myval\n", trapOut { "myval".tee() })
    }

    @Test
    fun `should evaluate lambda and write to stdout`() {
        assertEquals("value is myval\n", trapOut { "myval".tee { v -> "value is $v" } })
    }

    @Test
    fun `should write to logger`() {
        assertTrue(trapErr { "myval".teeToInfo(logger) }.endsWith("myval\n"))
    }

    @Test
    fun `should log with info level`() {
        listOf(
                trapErr { "myval".teeToInfo(logger, "hello {}") },
                trapErr { "myval".teeToInfo(logger) { "hello $it" } },
                trapErr { "myval".teeToInfo(logger) { "hello {}" } }
        )
                .also { it.all { output -> output.contains("INFO") } }
                .also { it.all { output -> output.endsWith("hello myval\n") } }
    }

    @Test
    fun `should log with trace() level`() {
        listOf(
                trapErr { "myval".teeToTrace(logger, "hello {}") },
                trapErr { "myval".teeToTrace(logger) { "hello $it" } },
                trapErr { "myval".teeToTrace(logger) { "hello {}" } }
        )
                .also { it.all { output -> output.contains("TRACE") } }
                .also { it.all { output -> output.endsWith("hello myval\n") } }
    }

    @Test
    fun `should log with debug level`() {
        listOf(
                trapErr { "myval".teeToDebug(logger, "hello {}") },
                trapErr { "myval".teeToDebug(logger) { "hello $it" } },
                trapErr { "myval".teeToDebug(logger) { "hello {}" } }
        )
                .also { it.all { output -> output.contains("DEBUG") } }
                .also { it.all { output -> output.endsWith("hello myval\n") } }
    }

}
