package MakeALisp

class Env {

    private val table : Map<ESymbol, Expr>
    private val outer : Env?

    constructor(outer: Env) {
        this.table = mapOf()
        this.outer = outer
    }

    constructor(table: Map<ESymbol, Expr>) {
        this.table = table
        this.outer = null
    }

    operator fun get(sym : ESymbol) : Expr? {
        val item = table[sym]
        return when {
            item != null -> item
            outer != null -> outer[sym]
            else -> null
        }
    }
}

fun basicNumOp(sym : String, fn : (Long, Long) -> Long) : Pair<ESymbol, Expr> {
    return Pair(ESymbol(sym), EFn({ args: List<Expr> ->
        ENum(args.stream()
                 .map { e: Expr? -> (e as ENum).value }
                 .reduce(fn).get())
    }))
}

fun initialEnv() : Env = Env(mutableMapOf(
        basicNumOp("+", Long::plus),
        basicNumOp("-", Long::minus),
        basicNumOp("/", Long::div),
        basicNumOp("*", Long::times),
        basicNumOp("rem", Long::rem)))