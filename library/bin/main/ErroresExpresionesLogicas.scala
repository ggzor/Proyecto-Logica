package com.ggzor.logmat.expresiones

import scala.util.{Failure, Success, Try}
import EsquemaExpresionesLogicas._

sealed abstract class InterpretacionException extends IllegalArgumentException
case object OperandosFaltantes extends InterpretacionException
case object OperadoresFaltantes extends InterpretacionException
case object ParentesisFaltantes extends InterpretacionException
case object ExpresionVacia extends InterpretacionException

object ErroresExpresionesLogicas {
  private def obtenerSiguienteParantesisCierre(expr: String): Option[Int] = {
    def rec(indice: Int, profundidad: Int): Option[Int] = {
      if(profundidad == 0)
        Some(indice)
      else if(indice == expr.length)
        None
      else
        rec(indice + 1, expr(indice) match {
          case '(' => profundidad + 1
          case ')' => profundidad - 1
          case _   => profundidad
        })
    }

    rec(1, 1)
  }

  private def hayUnaExpresionEnSeguida(expr: String): Boolean = {
    if(expr.head == '(') {
      obtenerSiguienteParantesisCierre(expr)
      .map(expr.substring(1, _) exists { c => c != '(' && c != ')' })
      .getOrElse(false)
    } else {
      expr.head != ')'
    }
  }

  private def detectarCadenaVacia(expr: String) = 
    if(expr == "") 
      Failure(ExpresionVacia) 
    else 
      Success(expr)

  private def detectarNegacionesSinExpresion(expr: String): Try[String] = {
    def go(i: Int): Try[String] = {
      if(i == expr.length)
        Success(expr)
      else if(operadoresUnarios.contains(expr(i)) 
              && (i == expr.length - 1 || !hayUnaExpresionEnSeguida(expr substring i + 1)))
        Failure(OperandosFaltantes)
      else
        go(i + 1)
    }

    go(0)
  }

  private def detectarParentesisFaltantes(expr: String): Try[String] = {
    def reducir(pila: List[Char], c: Char): List[Char] = pila match {
      case '(' :: resto if c == ')' => resto
      case _ => c :: pila
    }

    val parentesis = expr.filter(c => c == '(' || c == ')').toList
    if(parentesis.foldLeft(List.empty[Char])(reducir) == Nil)
      Success(expr)
    else
      Failure(ParentesisFaltantes)
  }

  def encontrarErrores(expr: String): Try[String] =
    List(
      detectarCadenaVacia _,
      detectarParentesisFaltantes _,
      detectarNegacionesSinExpresion _
    ).map(f => f(expr))
    .find(_.isFailure)
    .getOrElse(Success(expr))
}
