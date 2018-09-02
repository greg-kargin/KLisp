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

fun prnImpl(args : List<Expr>) : Expr {
    println(args.map { expr: Expr -> expr.print() }.joinToString(" "))
    return ENil
}

fun list(args : List<Expr>) : Expr = EList(args)

fun listHuh(args : List<Expr>) : Expr = if (args[0] is EList) ETrue else EFalse

fun emptyHuh(args : List<Expr>) : Expr = if ((args[0] as EList).elements.count() == 0) ETrue else EFalse

fun cons(args : List<Expr>) : Expr {
    val head = args[0]
    val tail = (args[1] as EList).elements
    val list = mutableListOf(head)
    list.addAll(tail)
    return EList(list)
}

fun initialEnv() : Env = Env(mutableMapOf(
        varargNumOp("+", Long::plus),
        varargNumOp("-", Long::minus),
        varargNumOp("/", Long::div),
        varargNumOp("*", Long::times),
        varargNumOp("rem", Long::rem),
        Pair(ESymbol("="), EFn(::varargEquals)),
        Pair(ESymbol("prn"), EFn(::prnImpl)),
        Pair(ESymbol("list"), EFn(::list)),
        Pair(ESymbol("list?"), EFn(::listHuh)),
        Pair(ESymbol("empty?"), EFn(::emptyHuh)),
        Pair(ESymbol("cons"), EFn(::cons))))