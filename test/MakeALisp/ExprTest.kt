package MakeALisp

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class ExprTest {
    @Test
    fun parseString() {
        val t = "\"foo \\\\ bar \\n fiz \\t buz\""
        val r = parseEString(t)!!
        assertEquals("foo \\ bar \n fiz \t buz", r.value)
    }
}