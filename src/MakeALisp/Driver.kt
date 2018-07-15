package MakeALisp

fun read() : String? {
    return readLine()
}

fun eval(a: Any) = a

fun print(a: Any) = println(a)

fun repl() {
    while (true) {
        val ast = read() ?: return
        val res = eval(ast)
        print(ast)

        if (res == "exit") {
            return
        }
    }
}

fun main(args: Array<String>) {
    repl()
}