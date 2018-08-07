package MakeALisp

interface Expr { fun print() : String }

typealias Tokens = List<String>
typealias Exprs  = List<Expr>

interface EAtom : Expr
class ENum(val value : Number) : EAtom {
    override fun print(): String {
        return value.toString()
    }
}

class ESymbol(val value : String) : EAtom {
    override fun print(): String {
        return value
    }
}

class EList(val elements : List<Expr>) : Expr {
    override fun print(): String {
        return elements.fold("(", { acc, expr -> acc + " " + expr.print()}) + " )"
    }
}
