package org.hugo.ejercicioi;

import BBDD.conexionBBDD;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.Objects;
import java.util.Properties;
import java.util.ResourceBundle;
public class HelloApplication extends Application {
    private static ResourceBundle bundle;
    public void start(Stage stage) throws IOException {
        Properties connConfig = conexionBBDD.loadProperties() ;
        String lang = connConfig.getProperty("language");
        Locale locale = new Locale.Builder().setLanguage(lang).build();
        bundle = ResourceBundle.getBundle("properties/lang", locale);
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
    public static ResourceBundle getBundle() {
        return bundle;
    }
    public static void main(String[] args) {
        launch();
    }
}