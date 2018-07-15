package MakeALisp

fun readString() {}

fun tokenize(str : String) : List<String> {
    val matcher = Regex("""[\s,]*(~@|[\[\]{}()'`~^@]|"(?:\\.|[^\\"])*"|;.*|[^\s\[\]{}('"`,;)]*)""")
    return matcher.findAll(str, 0).map { e -> e.value }.toList()
}

fun readList(tokens : List<String>) : Expr {
    return EAtom()
}

fun readAtom(tokens : List<String>) : Expr {
    return EAtom()
}

fun readForm(tokens : List<String>) : Expr {
    return if (tokens.first() == "(") {
        readList(tokens)
    } else {
        readAtom(tokens)
    }
}



fun main(args: Array<String>) {
    val a = tokenize("(foo (bar (baz)))")
}
