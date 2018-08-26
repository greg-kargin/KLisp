package MakeALisp

class Env {

    private val table : MutableMap<ESymbol, Expr>
    private val outer : Env?

    constructor(outer: Env) {
        this.table = mutableMapOf()
        this.outer = outer
    }

    constructor(table: MutableMap<ESymbol, Expr>) {
        this.table = table
        this.outer = null
    }

    constructor(outer: Env, table: MutableMap<ESymbol, Expr>) {
        this.outer = outer
        this.table = table
    }

    operator fun get(sym : ESymbol) : Expr? {
        val item = table[sym]
        return when {
            item != null -> item
            outer != null -> outer[sym]
            else -> null
        }
    }

    operator fun set(sym : ESymbol, value : Expr) {
        table[sym] = value
    }
}

fun varargNumOp(sym : String, fn : (Long, Long) -> Long) : Pair<ESymbol, Expr> {
    return Pair(ESymbol(sym), EFn({ args: List<Expr> ->
        ENum(args.stream()
                 .map { e: Expr? -> (e as ENum?)!!.value }
                 .reduce(fn).get())
    }))
}

fun varargEquals(args : List<Expr>) : Expr {
    for (arg in args) {
        if (arg != args[0]) {
            return EFalse
        }
    }
    return ETrue
}

fun initialEnv() : Env = Env(mutableMapOf(
        varargNumOp("+", Long::plus),
        varargNumOp("-", Long::minus),
        varargNumOp("/", Long::div),
        varargNumOp("*", Long::times),
        varargNumOp("rem", Long::rem),
        Pair(ESymbol("="), EFn(::varargEquals))))