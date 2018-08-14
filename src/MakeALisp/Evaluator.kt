package MakeALisp

typealias Invokable = (List<Expr>) -> Expr
typealias Env = Map<ESymbol, Invokable>

fun basicNumOp(sym : String, fn : (Long, Long) -> Long) : Pair<ESymbol, (List<Expr>) -> Expr> {
    return Pair(ESymbol(sym), { args: List<Expr> ->
        ENum(args.stream()
                .map { e: Expr? -> (e as ENum).value }
                .reduce(fn).get())
    })
}

fun initialEnv() : Env = mutableMapOf(
        basicNumOp("+", Long::plus),
        basicNumOp("-", Long::minus),
        basicNumOp("/", Long::div),
        basicNumOp("*", Long::times),
        basicNumOp("rem", Long::rem))

fun eval(e: Expr, env: Env) : Expr = when {
    e is EAtom -> e
    e is EList -> (env[e[0]] as Invokable).invoke(e.elements.drop(1).map { elem -> eval(elem, env) })
    else -> ENil()
}