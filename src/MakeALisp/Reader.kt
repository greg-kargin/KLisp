package MakeALisp

fun readString() {}

fun tokenize(str : String) : Tokens {
    val matcher = Regex("""[\s,]*(~@|[\[\]{}()'`~^@]|"(?:\\.|[^\\"])*"|;.*|[^\s\[\]{}('"`,;)]*)""")
    return matcher.findAll(str, 0).map { e -> e.value.trim() }.toList()
}

fun readList(tokens : Tokens) : Pair<EList, Tokens> {

    val acc : MutableList<Expr> = mutableListOf()
    var tokens = tokens

    while (true) {
        val nextToken = tokens.first()
        if (nextToken == ")") {
            return Pair(EList(acc), tokens.drop(1))
        } else {
            val result = readExpr(tokens)
            acc.add(result.first)
            tokens = result.second
        }
    }
}

fun readAtom(tokens : Tokens) : Pair<EAtom, Tokens> {
    return Pair(parseEAtom(tokens.first()), tokens.drop(1))
}

fun readExpr(tokens : Tokens) : Pair<Expr, Tokens> {
    print(tokens.first() + (tokens.first() == "("))
    return if (tokens.first() == "(") {
        readList(tokens.drop(1))
    } else {
        readAtom(tokens)
    }
}

fun main(args: Array<String>) {
    val tokens = tokenize("(foo (bar (baz)))")
    val exprs = readExpr(tokens)

    print(tokens)
    print(exprs)
}
