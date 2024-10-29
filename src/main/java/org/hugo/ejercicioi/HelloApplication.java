package org.hugo.ejercicioi;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
public class HelloApplication extends Application {
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("EjercicioI.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 500);
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Imagenes/agenda.png")));
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/Estilos/styles.css")).toExternalForm());
        stage.getIcons().add(icon);
        stage.setMaxWidth(800);
        stage.setMinWidth(500);
        stage.setMinHeight(400);
        stage.setTitle("Personas");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}