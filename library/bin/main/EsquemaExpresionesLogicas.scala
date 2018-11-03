  package com.ggzor.logmat.expresiones

  object EsquemaExpresionesLogicas {
    val prioridades: Map[Char, Int] = { 
      for {
        (grupo, prioridad) <- List("⇒⇔", "∧∨", "¬").zipWithIndex
        operador <- grupo
      } yield (operador, prioridad)
    }.toMap

    val operadoresUnarios = Map (
      '¬' -> Negacion
    )

    val operadoresBinarios = Map (
      '∧' -> Conjuncion,
      '∨' -> Disyuncion,
      '⇒' -> Condicional,
      '⇔' -> Bicondicional
    )
  }
  