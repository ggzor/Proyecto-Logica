package com.ggzor.logmat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class App extends Application {
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    Font.loadFont(App.class.getResourceAsStream("Fuentes/DejaVuSansMono.ttf"), 16);

    Rectangle2D medidas = Screen.getPrimary().getBounds();
    Parent contenido = FXMLLoader.load(App.class.getResource("Main.fxml"));
    Scene escena = new Scene(contenido, medidas.getWidth(), medidas.getHeight());
    primaryStage.setScene(escena);
    primaryStage.show();
  }
}
