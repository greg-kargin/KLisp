package MakeALisp

typealias Tokens = List<Token>

fun readString(str : String) : Expr {
    return readExpr(tokenize(str)).first
}

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
    return if (tokens.first() == "(") {
        readList(tokens.drop(1))
    } else {
        readAtom(tokens)
    }
}

fun main(args: Array<String>) {
    val tokens = tokenize("(nil true false 42 baz)")
    Thread.sleep(1000)
    val exprs = readExpr(tokens)

    print(tokens)
    print(exprs)
}
