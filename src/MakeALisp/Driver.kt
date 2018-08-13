package MakeALisp

fun repl() {
    val env = initialEnv()

    while (true) {
        val raw = readLine() ?: "exit"
        if (raw == "exit" || raw == "quit") return
        if (raw != "") {
            val ast = read(raw)
            val res = eval(ast, env)
            println(res.print())
        }
    }
}

fun main(args: Array<String>) {
    repl()
}