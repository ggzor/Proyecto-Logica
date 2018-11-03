package com.ggzor.logmat.expresiones
import org.scalatest._
import prop._
import scala.util.{Success, Failure}

class ExpresionesLogicasSpec extends FunSpec with TableDrivenPropertyChecks with Matchers {
  val simbolos = List(Simbolo('A'), Simbolo('B'), Simbolo ('C'))
  val List(vA, vB, vC) = simbolos
  val A = vA; val B = vB; val C = vC

  val valores = 
    Table(
      "expresion"                         -> "esperado",
      "A"                                 -> A,
      "¬A"                                -> Negacion(A),
      "¬((¬(¬A)))"                        -> Negacion(Negacion(Negacion(A))),
      "A ∧ B"                             -> Conjuncion(A, B),
      "A ∨ B"                             -> Disyuncion(A, B),
      "A ⇒ B"                             -> Condicional(A, B),
      "A ⇔ B"                             -> Bicondicional(A, B),
      "A ⇒ ¬A"                            -> Condicional(A, Negacion(A)),
      "((A ⇒ B) ⇒ A) ⇒ A"                 -> Condicional(Condicional(Condicional(A, B), A), A),
      "(A ⇒ B) ⇔ (¬B ⇒ ¬A)"               -> Bicondicional(Condicional(A, B), Condicional(Negacion(B), Negacion(A))),
      "(A ⇒ C) ⇒ ((B ⇒ C) ⇒ (A ∨ B ⇒ C))" -> Condicional(Condicional(A, C), Condicional(Condicional(B, C), Condicional(Disyuncion(A, B), C)))
    )

  val valoresInvalidos = 
    Table(
      ("expresion", "mensaje"),
      ("", ExpresionVacia),
      ("¬¬", OperandosFaltantes),
      ("A¬", OperandosFaltantes),
      ("A¬((()))", OperandosFaltantes),
      ("A¬(¬)", OperandosFaltantes),
      ("A ∨", OperandosFaltantes),
      ("A¬B", OperadoresFaltantes),
      ("A ∨ (B", ParentesisFaltantes)
    )

  describe("interpretar") {
    it("deberia regresar una fórmula cuando se llama con expresión") {
      forAll (valores) { (expr, formula) =>
        ExpresionesLogicas.interpretar(expr) shouldBe Success(formula)
      }
    }
  }

  describe("interpretar") {
    it("deberia lanzar excepcion cuando se llama con expresión inválida") {
      forAll(valoresInvalidos) { (expr, excepcion) =>
        ExpresionesLogicas.interpretar(expr) shouldBe Failure(excepcion)
      }
    }
  }
}