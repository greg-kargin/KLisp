package MakeALisp

typealias Invokable = (Expr, Expr) -> Expr
typealias Env = Map<ESymbol, Invokable>

fun basicNumOp(sym : String, fn : (Long, Long) -> Long) : Pair<ESymbol, (Expr, Expr) -> Expr> {
    return Pair(ESymbol(sym), { x : Expr, y : Expr -> ENum(fn((x as ENum).value, (y as ENum).value))})
}

fun initialEnv() : Env = mutableMapOf(
        basicNumOp("+", Long::plus),
        basicNumOp("-", Long::minus),
        basicNumOp("/", Long::div),
        basicNumOp("*", Long::times),
        basicNumOp("rem", Long::rem))

fun eval(e: Expr, env: Env) : Expr = when {

    e is EAtom -> e
    e is EList -> (env[e[0]] as Invokable).invoke(eval(e[1], env), eval(e[2], env))
    else -> ENil()
}