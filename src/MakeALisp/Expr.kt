package MakeALisp

typealias Token = String

interface Expr { fun print() : String }
interface EAtom : Expr
interface EBool : EAtom

class ENum(val value : Number) : EAtom {
    override fun print() : String = value.toString()
}

class ESymbol(val value : String) : EAtom {
    override fun print() : String = value
}

class EString(val value : String) : EAtom {
    override fun print() : String = value
}

class ETrue : EBool {
    override fun print() : String = "true"
}

class EFalse : EBool {
    override fun print() : String = "false"
}

class ENil : EAtom {
    override fun print() : String = "nil"
}

class EList(val elements : List<Expr>) : Expr {
    override fun print(): String {
        return elements.fold("(", { acc, expr -> acc + " " + expr.print()}) + " )"
    }
}

fun parseENum(token : Token) : ENum? {
    return when {
        token.chars().allMatch(Character::isDigit) -> ENum(token.toInt())
        else -> null
    }
}

fun parseESymbol(token : Token) : ESymbol? {
    return ESymbol(token)
}

// todo
fun parseEString(token : Token) : EString? {
    return null
}

fun parseETrue(token : Token) : ETrue? {
    return if (token == "true") ETrue() else null
}

fun parseEFalse(token : Token) : EFalse? {
    return if (token == "false") EFalse() else null
}

fun parseENil(token : Token) : ENil? {
    return if (token == "nil") ENil() else null
}

fun parseEAtom(token : String) : EAtom {
    return sequenceOf( // not lazy :(
            parseENil(token),
            parseETrue(token),
            parseEFalse(token),
            parseENum(token),
            parseEString(token),
            parseESymbol(token)
    ).filterNotNull().first()
}
