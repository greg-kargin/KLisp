package MakeALisp

fun eval(e: Expr, env: Env) : Expr = when (e) {
    is ESymbol -> env[e]!!
    is EAtom -> e
    is EList -> {
        when ((e.elements[0] as ESymbol).value) {
            "def!" -> {
                env[e.elements[1] as ESymbol] = e.elements[2]
                e.elements[2]
            }
            "let*" -> {
                val newEnv = Env(env)
                val bindings = e.elements[1]
                (bindings as EList).elements
                        .chunked(2)
                        .map { p -> newEnv[p[0] as ESymbol] = p[1]}
                eval(e.elements[2], newEnv)
            }
            else   -> {
                val elems = e.elements.map { elem -> eval(elem, env) }
                (elems[0] as EFn).invoke(elems.drop(1))
            }
        }
    }
    else -> ENil
}