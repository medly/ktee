package ktee

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class KTeeTest {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    @Test
    fun `should return the original value in chains`() {
        val result = listOf(1, 2, 3).map { it * 2 }.tee { ">> $it" }.reduce(Int::plus).tee()
        assertEquals(12, result)
    }

    @Test
    fun `should write to stdout`() {
        assertEquals("myval" + System.lineSeparator(), trapOut { "myval".tee() })
    }

    @Test
    fun `should evaluate lambda and write to stdout`() {
        assertEquals("value is myval" + System.lineSeparator(), trapOut { "myval".tee { v -> "value is $v" } })
    }

    @Test
    fun `should write to logger`() {
        assertTrue(trapErr { "myval".teeToInfo(logger) }.endsWith("myval" + System.lineSeparator()))
    }

    @Test
    fun `should log with info level`() {
        val outputs = listOf(
                trapErr { "myval".teeToInfo(logger, "hello {}") },
                trapErr { "myval".teeToInfo(logger) { "hello $it" } },
                trapErr { "myval".teeToInfo(logger) { "hello {}" } }
        )
        outputs.forEach { assertTrue { it.contains("INFO") } }
        outputs.forEach { assertTrue { it.endsWith("hello myval" + System.lineSeparator()) } }
    }

    @Test
    fun `should log with trace level`() {
        val outputs = listOf(
                trapErr { "myval".teeToTrace(logger, "hello {}") },
                trapErr { "myval".teeToTrace(logger) { "hello $it" } },
                trapErr { "myval".teeToTrace(logger) { "hello {}" } }
        )
        outputs.forEach { assertTrue { it.contains("TRACE") } }
        outputs.forEach { assertTrue { it.endsWith("hello myval" + System.lineSeparator()) } }
    }

    @Test
    fun `should log with debug level`() {
        val outputs = listOf(
                trapErr { "myval".teeToDebug(logger, "hello {}") },
                trapErr { "myval".teeToDebug(logger) { "hello $it" } },
                trapErr { "myval".teeToDebug(logger) { "hello {}" } }
        )
        outputs.forEach { assertTrue { it.contains("DEBUG") } }
        outputs.forEach { assertTrue { it.endsWith("hello myval" + System.lineSeparator()) } }
    }

}
