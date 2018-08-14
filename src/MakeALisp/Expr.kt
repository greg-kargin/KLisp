package MakeALisp

import kotlin.text.StringBuilder

typealias Token = String

interface Expr { fun print() : String }
interface EAtom : Expr
interface EBool : EAtom

data class EFn(val fn: (List<Expr>) -> Expr) : Expr {
    override fun print() : String = fn.toString()
    fun invoke(args : List<Expr>) : Expr = fn(args)
}

data class ENum(val value: Long) : EAtom {
    override fun print() : String = value.toString()
}

data class ESymbol(val value : String) : EAtom {
    override fun print() : String = value
}

data class EString(val value : String) : EAtom {
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

data class EList(val elements : List<Expr>) : Expr {

    operator fun get(i : Int): Expr = elements[i]

    override fun print(): String {
        return elements.fold("(", { acc, expr -> acc + " " + expr.print()}) + " )"
    }
}

fun parseENum(token : Token) : ENum? {
    return when {
        token.chars().allMatch(Character::isDigit) -> ENum(token.toLong())
        else -> null
    }
}

fun parseESymbol(token : Token) : ESymbol? {
    return ESymbol(token)
}

fun parseEString(token : Token) : EString? {
    if (token.first() != '\"' && token.last() != '\'') return null
    val token = token.drop(1).dropLast(1)
    val sb = StringBuilder()

    var idx = 0
    while (idx < token.length) {
        val c = token[idx]
        if (c == '\\') {
            // todo handle parsing errors
            assert(idx + 1 < token.length)
            when (token[idx + 1]) {
                '\\' -> sb.append('\\')
                'n'  -> sb.append('\n')
                't'  -> sb.append('\t')
                else -> error("unknown escape sequence in string literal")
            }
            idx += 2
        } else {
            sb.append(c)
            idx += 1
        }
    }
    return EString(sb.toString())
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
