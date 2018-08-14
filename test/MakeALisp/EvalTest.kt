package MakeALisp

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class EvalTest {
    @Test
    fun simpleEval() {
        val raw = "(+ 1 (+ 2 (+ 3 4 5) 7) 8 12)"
        val env = initialEnv()
        val ast = read(raw) 
        val res = eval(ast, env)
        res
        assertEquals(ENum(42), res)
    }

    @Test
    fun equalsEval() {
        val raw = "(= 0 0 0 0)"
        val env = initialEnv()
        val ast = read(raw)
        val res = eval(ast, env)
        res
        assertEquals(ETrue, res)
    }

    @Test
    fun letEval() {
        val raw = "(let* (a 20) (+ a (let* (a 22) a)))"
        val env = initialEnv()
        val ast = read(raw)
        val res = eval(ast, env)
        res
        assertEquals(ENum(42), res)
    }
}