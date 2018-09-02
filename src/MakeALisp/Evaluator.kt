package MakeALisp

fun truthy(e: Expr): Boolean = (!(e is ENil)) && (!(e is EFalse))

fun eval(e: Expr, env: Env) : Expr = when (e) {
    is ESymbol -> env[e]!!
    is EAtom -> e
    is EList -> {
        when ((e.elements[0] as ESymbol).value) {
            "def!" -> {
                env[e.elements[1] as ESymbol] = eval(e.elements[2], env)
                e.elements[1]
            }
            "let*" -> {
                val newEnv = Env(env)
                val bindings = e.elements[1]
                (bindings as EList).elements
                        .chunked(2)
                        .map { p -> newEnv[p[0] as ESymbol] = eval(p[1], newEnv)}
                eval(e.elements[2], newEnv)
            }
            "do" -> {
                e.elements
                        .drop(1)
                        .map { e -> eval(e, env) }
                        .last()
            }
            "if" -> {
                val pred = e.elements[1]
                val expr1 = e.elements[2]
                val expr2 = e.elements[3]

                val result = eval(pred, env)
                if (truthy(result)) {
                    eval(expr1, env)
                } else {
                    eval(expr2, env)
                }
            }
            "fn*" -> {
                val args = e.elements[1]
                val body = e.elements[2]

                EFn(fun (vals : List<Expr>) : Expr {
                    val bindings = (args as EList).elements.asSequence()
                            .map { e -> (e as ESymbol)}
                            .zip(vals.asSequence())
                            .associate { e -> e }
                            .toMutableMap()
                    val newEnv = Env(env, bindings)
                    return eval(body, newEnv)
                })
            }
            else -> {
                val elems = e.elements.map { elem -> eval(elem, env) }
                (elems[0] as EFn).invoke(elems.drop(1))
            }
        }
    }
    else -> ENil
}