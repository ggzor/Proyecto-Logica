package com.ggzor.logmat.expresiones

object InfijoPostfijo {
  def apply(prioridadOperadores: Map[Char, Int])(cadena: String): Vector[Char] = {
    val esOperador: Set[Char] = prioridadOperadores.keySet ++ "()"

    @annotation.tailrec
    def rec(valores: List[Char], resultado: Vector[Char], pila: List[Char]): Vector[Char] = valores match {
      case Nil => resultado ++ pila
      case valor :: resto => {
        if(esOperador(valor)) {
          if(pila == Nil || valor == '(') {
            rec(resto, resultado, valor :: pila)
          } else {
            if(valor == ')') {
              val (quitados, _ :: sobrantes) = pila span { _ != '(' }
              rec(resto, resultado ++ quitados, sobrantes)
            } else {
              val (quitados, sobrantes) = pila span { 
                c => c != '(' && !(prioridadOperadores(c) < prioridadOperadores(valor)) 
              }
              rec(resto, resultado ++ quitados, valor :: sobrantes)
            }
          }
        } else if(valor == ' ')
          rec(resto, resultado, pila)
        else 
          rec(resto, resultado :+ valor, pila)
      }
    }

    rec(cadena.toList, Vector.empty, Nil)
  }
}