package com.ggzor.logmat.expresiones

sealed trait Formula

case class Simbolo(valor: Char) extends Formula
case class Negacion(formula: Formula) extends Formula

case class Conjuncion(izquierda: Formula, derecha: Formula) extends Formula
case class Disyuncion(izquierda: Formula, derecha: Formula) extends Formula

case class Condicional(izquierda: Formula, derecha: Formula) extends Formula
case class Bicondicional(izquierda: Formula, derecha: Formula) extends Formula