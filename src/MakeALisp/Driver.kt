package MakeALisp

fun eval(e: Expr) : Expr = e

fun repl() {
    while (true) {
        val raw = readLine() ?: "exit"
        if (raw == "exit" || raw == "quit") return
        if (raw != "") {
            val ast = readString(raw)
            val res = eval(ast)
            println(res.print())
        }
    }
}

fun main(args: Array<String>) {
    repl()
}