package Kombinators

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

internal class KombinatorsTest {

    @Test
    fun parseOne() {
        val s = "(defn foo [x y] (= x y))"
        val pred = fun(x : Char) : Boolean { return x == '('}
        assertEquals(
                Pair("(", s.drop(1)),
                parseOne(pred)(s)
        )
    }

    @Test
    fun parseOneFail() {
        val s = "(defn foo [x y] (= x y))"
        val pred = fun(x : Char) : Boolean { return x == ')'}
        assertEquals(
                Pair("", s),
                parseOne(pred)(s)
        )
    }

    @Test
    fun parseMany() {
        val s = "defn foo [x y] (= x y))"
        val pred = fun(x : Char) = Character.isAlphabetic(x.toInt())
        val parser = parseOne(pred);
        assertEquals(
                Pair("defn", s.drop(4)),
                parseMany(parser)(s)
        )
    }

    @Test
    fun parseAnd() {
        val s = "(defn foo [x y] (= x y))"
        val pred1 = fun(x : Char) = x == '('
        val pred2 = fun(x : Char) = Character.isAlphabetic(x.toInt())
        val parser1 = parseOne(pred1)
        val parser2 = parseMany(parseOne(pred2))
        val parsers = listOf(parser1, parser2)
        assertEquals(
                Pair("(defn", s.drop(5)),
                parseAnd(parsers)(s))

    }

    @Test
    fun parseOr() {
        val s = "a1"
        val pred1 = fun(x : Char) = x == '1'
        val pred2 = fun(x : Char) = x == 'a'
        val parser1 = parseOne(pred1)
        val parser2 = parseOne(pred2)
        val parsers = listOf(parser1, parser2)
        assertEquals(
                Pair("a", "1"),
                parseOr(parsers)(s)
        )
    }
}