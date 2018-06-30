package Lexer
enum class Token {
    LEFT_PAREN, RIGHT_PAREN, SYMBOL, INTEGER, FLOAT
}

fun parseOne(pred : (Char) -> Boolean) : (String) -> Pair<String, String> {
    return fun (s : String) : Pair<String, String> {
        return if (pred(s.first())) {
            Pair(s.take(1), s.drop(1))
        } else {
            Pair("", s)
        }
    }
}

// todo refactor to take parser instead of pred
fun parseMany(pred : (Char) -> Boolean) : (String) -> Pair<String, String> {
    return fun (s : String) : Pair<String, String> {
        var acc = ""
        var s = s
        while (true) {
            val (c, s_) = parseOne(pred)(s)
            if (c != "") {
                acc += c.first()
                s = s_
            } else {
                break
            }
        }
        return Pair(acc, s)
    }
}

//fun parsePlus(parsers : List<(String) -> Pair<List<Char>?, String>>) : (String) -> Pair<List<Char>?, String> {
//    return fun (s : String) : (String) -> Pair<List<Char>?, String>{
//        return Pair(null, s)
//    }
//}

fun parse(s : String) : List<Token>? {



    return null
}