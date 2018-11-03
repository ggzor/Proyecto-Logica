package com.ggzor.logmat.expresiones

import scala.util.{Failure, Success, Try}
import EsquemaExpresionesLogicas._
import ErroresExpresionesLogicas.encontrarErrores

object ExpresionesLogicas {
  private val infijoPostfijoLogico = InfijoPostfijo(prioridades) _

  private def convertirSiguienteSimbolo(pila: List[Formula], simbolo: Char): List[Formula] = pila match {
    case lista if 'A' to 'Z' contains simbolo =>
      Simbolo(simbolo) :: lista

    case op :: resto if operadoresUnarios contains simbolo => 
      operadoresUnarios(simbolo)(op) :: resto

    case op2 :: op1 :: resto if operadoresBinarios contains simbolo =>
      operadoresBinarios(simbolo)(op1, op2) :: resto

    case _ => throw OperandosFaltantes
  }

  def interpretar(expr: String): Try[Formula] =
    encontrarErrores(expr) flatMap { expr => {
      val tokens = infijoPostfijoLogico(expr)
      val pila = tokens.foldLeft(List.empty[Formula])(convertirSiguienteSimbolo)

      if(pila.size > 1)
        Failure(OperadoresFaltantes)
      else
        Success(pila.head)
    }}
}