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

    @Test
    fun ifEval() {
        val raw = "(if true 42 0)"
        val env = initialEnv()
        val ast = read(raw)
        val res = eval(ast, env)
        res
        assertEquals(ENum(42), res)
    }

    @Test
    fun doEval() {
        val raw = "(do (def! foo 50) (def! bar 92) (- bar foo))"
        val env = initialEnv()
        val ast = read(raw)
        val res = eval(ast, env)
        res
        assertEquals(ENum(42), res)
    }

    @Test
    fun fnEval() {
        val raw = "(do (def! f (fn* (a) a)) (f 42))"
        val env = initialEnv()
        val ast = read(raw)
        val res = eval(ast, env)
        res
        assertEquals(ENum(42), res)
    }

    @Test
    fun closureEval() {
        val raw = "(do (def! f (let* (ans 42) (fn* () ans))) (def! ans 32) (f))"
        val env = initialEnv()
        val ast = read(raw)
        val res = eval(ast, env)
        res
        assertEquals(ENum(42), res)
    }
}