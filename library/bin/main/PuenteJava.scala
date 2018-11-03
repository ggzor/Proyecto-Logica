package com.ggzor.logmat.expresiones

import scala.util.{Failure, Success}

case class ErrorInterpretacion(mensaje: String) extends RuntimeException

object Logica {
  def interpretar(expresion: String): Formula = {
    ExpresionesLogicas interpretar expresion match {
      case Success(resultado) => resultado
      case Failure(error) => throw ErrorInterpretacion(convertirError(error))
    } 
  } 

  def convertirError(excepcion: Throwable): String = excepcion match {
    case ExpresionVacia => "La expresión no debe estar vacía."
    case ParentesisFaltantes => "Faltan paréntesis en la expresión."
    case OperadoresFaltantes => "Faltan operadores para poder interpretar la expresion."
    case OperandosFaltantes => "Algunos operadores no están aplicados con el número correcto de argumentos."
    case _ => "Ocurrió un error al interpretar la expresión."
  }
}