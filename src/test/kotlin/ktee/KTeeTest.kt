package ktee

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class KTeeTest {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    @Test
    fun `should not execute release function`() {
        val message = "Debugging function executed"
        assertEquals("",
            trapOut { release { println(message) } })
    }

    @Test
    fun `should execute debug function`() {
        val message = "Debugging function executed"
        assertEquals(
            message + System.lineSeparator(),
            trapOut { debug { println(message) } })
    }

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
    fun `should prepend global prefix to all tee output`() {
        val previous = KTee.prefix
        KTee.prefix = "[app] "
        try {
            assertEquals("[app] Processed: request-42" + System.lineSeparator(), trapOut { "request-42".tee("Processed: ") })
            assertEquals("[app] request-42" + System.lineSeparator(), trapOut { "request-42".tee() })
            assertEquals("[app] Processed: request-42" + System.lineSeparator(), trapOut { "request-42".tee { "Processed: $it" } })
        } finally {
            KTee.prefix = previous
        }
    }

    @Test
    fun `should prepend global prefix to all logger output`() {
        val previous = KTee.prefix
        KTee.prefix = "[app] "
        try {
            val outputs = listOf(
                trapErr { "request-42".teeToInfo(logger, "Processed: {}") },
                trapErr { "request-42".teeToInfo(logger) { "Processed: $it" } },
                trapErr { "request-42".teeToDebug(logger, "Processed: {}") },
                trapErr { "request-42".teeToDebug(logger) { "Processed: $it" } },
                trapErr { "request-42".teeToTrace(logger, "Processed: {}") },
                trapErr { "request-42".teeToTrace(logger) { "Processed: $it" } }
            )
            outputs.forEach { assertTrue(it.endsWith("[app] Processed: request-42" + System.lineSeparator())) }
            val defaults = listOf(
                trapErr { "request-42".teeToInfo(logger) },
                trapErr { "request-42".teeToDebug(logger) },
                trapErr { "request-42".teeToTrace(logger) }
            )
            defaults.forEach { assertTrue(it.endsWith("[app] request-42" + System.lineSeparator())) }
        } finally {
            KTee.prefix = previous
        }
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
