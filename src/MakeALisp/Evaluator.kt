package MakeALisp

fun eval(e: Expr, env: Env) : Expr = when {
    e is ESymbol -> env[e]!!
    e is EAtom -> e
    e is EList -> {
        val elems = e.elements.map { elem -> eval(elem, env) }
            (elems[0] as EFn).invoke(elems.drop(1))
        }
    else -> ENil()
}