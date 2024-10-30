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

/**
 * La clase HelloApplication es la aplicación principal de la interfaz gráfica
 * de usuario (GUI) que gestiona la carga y configuración de la ventana
 * principal de la aplicación.
 */
public class HelloApplication extends Application {
    private static ResourceBundle bundle;

    /**
     * Método que inicia la aplicación y configura la ventana principal.
     *
     * @param stage el escenario principal de la aplicación.
     * @throws IOException si ocurre un error al cargar el archivo FXML.
     */
    public void start(Stage stage) throws IOException {
        // Cargar las propiedades de configuración de la base de datos
        Properties connConfig = conexionBBDD.loadProperties();
        String lang = connConfig != null ? connConfig.getProperty("language", "en") : "en"; // Usa "en" como idioma por defecto

        // Configurar el locale con el idioma seleccionado
        Locale locale = new Locale(lang);
        bundle = ResourceBundle.getBundle("Properties.lang", locale);

        // Configurar el cargador de la interfaz FXML
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("EjercicioI.fxml"));
        fxmlLoader.setResources(bundle);
        Scene scene = new Scene(fxmlLoader.load(), 800, 500);

        // Configurar el icono de la aplicación
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Imagenes/agenda.png")));
        stage.getIcons().add(icon);

        // Aplicar estilos CSS
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/Estilos/estilos.css")).toExternalForm());

        // Configuración de la ventana principal
        stage.setMaxWidth(800);
        stage.setMinWidth(500);
        stage.setMinHeight(400);
        stage.setTitle("Personas");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Método estático que obtiene el ResourceBundle utilizado para la
     * localización de la aplicación.
     *
     * @return el ResourceBundle de la aplicación.
     */
    public static ResourceBundle getBundle() {
        return bundle;
    }

    /**
     * El método main es el punto de entrada de la aplicación.
     *
     * @param args argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        launch();
    }
}
