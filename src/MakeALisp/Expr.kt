package MakeALisp

interface Expr

typealias Tokens = List<String>
typealias Exprs  = List<Expr>

interface EAtom : Expr
class ENum(val value : Number) : EAtom
class ESymbol(val value : String) : EAtom

fun parseEAtom(token : String) : EAtom {
    return when {
        token.chars().allMatch(Character::isDigit) -> ENum(token.toInt())
        else -> ESymbol(token)
    }
}

class EList(val elements : List<Expr>) : Expr
