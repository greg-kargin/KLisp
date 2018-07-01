package Kombinators

/*
todo: return null as a result of parsing; think about polymorphic parsing
 */

fun parseOne(pred : (Char) -> Boolean) : (String) -> Pair<String, String> {
    return fun (s : String) : Pair<String, String> {
        return if (pred(s.first())) {
            Pair(s.take(1), s.drop(1))
        } else {
            Pair("", s)
        }
    }
}

fun parseMany(parser : (String) -> Pair<String, String>) : (String) -> Pair<String, String> {
    return fun (s : String) : Pair<String, String> {
        var acc = ""
        var s = s
        while (true) {
            val (c, s_) = parser(s)
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

fun parseOr(parsers : List<(String) -> Pair<String, String>>) : (String) -> Pair<String, String> {
    return fun (s : String) : Pair<String, String>{
        for (parser in parsers) {
            val (c, s_) = parser(s)
            if (c != "") return Pair(c, s_)
        }
        return Pair("", s)
    }
}

fun parseAnd(parsers : List<(String) -> Pair<String, String>>) : (String) -> Pair<String, String> {
    return fun (s : String) : Pair<String, String>{
        var result = Pair("", s)
        var s = s
        for (parser in parsers) {
            val (c, s_) = parser(s)
            s = s_
            val parsed = result.first
            result = Pair(parsed + c, s_)
        }
        return result
    }
}