package com.ggzor.logmat;

import com.ggzor.logmat.expresiones.ErrorInterpretacion;
import com.ggzor.logmat.expresiones.Logica;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Main {
  @FXML
  private VBox contenedor;

  @FXML
  private JFXTextField entrada;

  @FXML
  private Label error;

  @FXML
  private JFXButton boton;

  @FXML
  private void initialize() {
    entrada.textProperty().addListener((__, ___, valor) -> {
      try {
        Logica.interpretar(valor);

        error.setText("");
        boton.setDisable(false);
      } catch (ErrorInterpretacion errorInterpretacion) {
        error.setText(errorInterpretacion.mensaje());
        boton.setDisable(true);
      }
    });

    entrada.setText("A ⇒ B");
  }

  @FXML
  private void calcularResultado() {

  }

  @FXML
  private void avanzarPalabra() {
    if (!entrada.isFocused()) {
      entrada.requestFocus();
      entrada.selectEnd();
    }

    entrada.selectEndOfNextWord();
    entrada.selectRange(entrada.getSelection().getEnd(), entrada.getSelection().getEnd());
  }

  @FXML
  private void retrocederPalabra() {
    if (!entrada.isFocused()) {
      entrada.requestFocus();
      entrada.selectHome();
    }

    entrada.selectPreviousWord();
    entrada.selectRange(entrada.getSelection().getStart(), entrada.getSelection().getStart());
  }

  @FXML
  private void borrarPalabra() {
    if (!entrada.isFocused()) {
      entrada.requestFocus();
      entrada.selectEnd();
    }

    if (entrada.getSelectedText().length() == 0)
      entrada.selectPreviousWord();

    entrada.replaceSelection("");
  }

  @FXML
  private void borrarTodo() {
    if(!entrada.isFocused()) {
      entrada.requestFocus();
    }

    entrada.selectAll();
    entrada.replaceSelection("");
  }

  @FXML
  private void agregarSimbolo(ActionEvent evento) {
    if (evento.getSource() instanceof JFXButton) {
      JFXButton fuente = (JFXButton) evento.getSource();
      String simbolo = fuente.getText();

      if (!entrada.isFocused()) {
        entrada.requestFocus();
        entrada.selectEnd();
      }

      if (!simbolo.equals("(") && !simbolo.equals(")")) {
        int inicio = entrada.getSelection().getStart();
        if (inicio > 0 && entrada.getText().charAt(inicio - 1) != ' ') {
          simbolo = ' ' + simbolo;
        }

        int fin = entrada.getSelection().getEnd();
        if (fin < entrada.getLength() && entrada.getText().charAt(fin) != ' '
            || fin == entrada.getLength()) {
          simbolo = simbolo + ' ';
        }
      }

      entrada.replaceSelection(simbolo);
    }
  }
}
