package Lexer

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

internal class LexerTest {

    @Test
    fun parseOne() {
        val s = "(defn foo [x y] (= x y))"
        val pred = fun(x : Char) : Boolean { return x == '('}
        assertEquals(
                Lexer.parseOne(pred)(s),
                Pair("(", s.drop(1))
        )
    }

    @Test
    fun parseOneFail() {
        val s = "(defn foo [x y] (= x y))"
        val pred = fun(x : Char) : Boolean { return x == ')'}
        assertEquals(
                Lexer.parseOne(pred)(s),
                Pair("", s)
        )
    }

    @Test
    fun parseMany() {
        val s = "defn foo [x y] (= x y))"
        val pred = fun(x : Char) = Character.isAlphabetic(x.toInt())
        assertEquals(
                Lexer.parseMany(pred)(s),
                Pair("defn", s.drop(4))
        )
    }
}