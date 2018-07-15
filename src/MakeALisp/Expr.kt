package MakeALisp

interface Expr

class EAtom() : Expr

class EList(val elements : List<Expr>) : Expr {

}
