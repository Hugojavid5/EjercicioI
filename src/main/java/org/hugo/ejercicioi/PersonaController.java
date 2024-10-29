package org.hugo.ejercicioi;

import Dao.DaoPersonas;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import Model.Personas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.Objects;
import java.io.IOException;
import java.sql.SQLException;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import java.util.List;

/**
 * Controlador para la gestión de personas en la interfaz de usuario.
 * Proporciona funcionalidades para agregar, modificar, eliminar y filtrar personas.
 */
public class PersonaController {
    @FXML
    private TableView<Personas> tablaPersonas;

    @FXML
    private TextField txt_filtrar;

    @FXML
    private TableColumn<Personas, String> c_nombre;

    @FXML
    private TableColumn<Personas, String> c_apellidos;

    @FXML
    private TableColumn<Personas, Integer> c_edad;

    @FXML
    private Button btt_agregar;

    @FXML
    private Button btt_modificar;

    @FXML
    private Button btt_eliminar;

    @FXML
    private ImageView imgPersonas;

    @FXML
    private ImageView imgMas;

    @FXML
    private ImageView imgEditar;

    @FXML
    private ImageView imgMenos;

    private ObservableList<Personas> personasList = FXCollections.observableArrayList();
    private DaoPersonas daoPersona = new DaoPersonas();

    /**
     * Inicializa el controlador y carga los datos de personas desde la base de datos.
     */
    @FXML
    public void initialize() {
        c_nombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        c_apellidos.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getApellido()));
        c_edad.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getEdad()).asObject());

        cargarPersonasDesdeBD();
        btt_agregar.setTooltip(new Tooltip("Agregar una nueva persona"));
        btt_modificar.setTooltip(new Tooltip("Modificar una persona"));
        btt_eliminar.setTooltip(new Tooltip("Eliminar una persona"));
        txt_filtrar.setTooltip(new Tooltip("Filtrar personas por su nombre"));
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Imagenes/contactos.jpeg")));
        imgPersonas.setImage(image);
        Image image2 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Imagenes/mas.png")));
        imgMas.setImage(image2);
        Image image3 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Imagenes/editar.png")));
        imgEditar.setImage(image3);
        Image image4 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Imagenes/menos.png")));
        imgMenos.setImage(image4);
    }

    /**
     * Carga la lista de personas desde la base de datos y actualiza la tabla.
     */
    private void cargarPersonasDesdeBD() {
        try {
            List<Personas> personas = daoPersona.lstPersonas();
            personasList.setAll(personas); // Actualiza la lista observable con los datos obtenidos
            tablaPersonas.setItems(personasList); // Asigna la lista a la tabla
        } catch (SQLException e) {
            mostrarAlerta("Error", "No se pudieron cargar los datos desde la base de datos: " + e.getMessage());
        }
    }

    /**
     * Maneja la acción de agregar o modificar una persona.
     * Abre un modal para ingresar los datos de la persona.
     *
     * @param event Evento de acción que desencadena la apertura del modal.
     */
    @FXML
    private void agregar(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/hugo/ejercicioh/NuevaPersona.fxml"));
            Parent modalRoot = loader.load();
            Stage modalStage = new Stage();
            modalStage.initModality(Modality.WINDOW_MODAL);
            modalStage.initOwner(btt_agregar.getScene().getWindow());

            NuevaPersonaController modalController = loader.getController();
            modalController.setPersonasList(personasList);
            modalController.setDaoPersona(daoPersona);  // Pasamos el DAO al modal

            if (event.getSource() == btt_agregar) {
                modalStage.setTitle("Agregar Persona");
            } else if (event.getSource() == btt_modificar || event.getSource() instanceof MenuItem) {
                Personas personaSeleccionada = tablaPersonas.getSelectionModel().getSelectedItem();
                if (personaSeleccionada == null) {
                    mostrarAlerta("No hay ninguna persona seleccionada", "Por favor, seleccione una persona para editar.");
                    return;
                }
                modalStage.setTitle("Editar Persona");
                modalController.setPersonaAEditar(personaSeleccionada); // Configura la persona a editar
            }

            modalStage.setScene(new Scene(modalRoot));
            modalStage.showAndWait();

            cargarPersonasDesdeBD();

        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo abrir la ventana: " + e.getMessage());
        }
    }

    /**
     * Maneja la acción de eliminar una persona seleccionada de la tabla.
     *
     * @param event Evento de acción que desencadena la eliminación de la persona.
     */
    @FXML
    private void eliminar(ActionEvent event) {
        Personas personaSeleccionada = tablaPersonas.getSelectionModel().getSelectedItem();
        if (personaSeleccionada == null) {
            mostrarAlerta("No hay ninguna persona seleccionada", "Por favor, seleccione una persona para eliminar.");
        } else {
            try {
                daoPersona.eliminar(personaSeleccionada.getId());
                personasList.remove(personaSeleccionada); // Actualiza la lista observable eliminando la persona
                mostrarAlerta("Persona eliminada", "La persona ha sido eliminada con éxito.");
            } catch (SQLException e) {
                mostrarAlerta("Error", "No se pudo eliminar la persona: " + e.getMessage());
            }
        }
    }
    @FXML
    private void manejoMenu(ActionEvent event) {
        MenuItem menuItem = (MenuItem) event.getSource();
        if ("Modificar".equals(menuItem.getText())) {
            agregar(new ActionEvent(btt_modificar, null));
        } else if ("Eliminar".equals(menuItem.getText())) {
            eliminar(new ActionEvent(btt_eliminar, null));
        }
    }
    /**
     * Muestra una alerta con un mensaje y título especificado.
     *
     * @param titulo  El título de la alerta.
     * @param mensaje El mensaje a mostrar en la alerta.
     */
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait(); // Muestra la alerta y espera a que el usuario la cierre
    }

    /**
     * Filtra la lista de personas en la tabla según el texto ingresado en el campo de filtro.
     */
    public void filtrar() {
        String textoFiltro = txt_filtrar.getText().toLowerCase();

        ObservableList<Personas> personasFiltradas = FXCollections.observableArrayList();

        // Filtrar la lista de personas según el nombre
        for (Personas persona : personasList) {
            if (persona.getNombre().toLowerCase().contains(textoFiltro)) {
                personasFiltradas.add(persona);
            }
        }

        tablaPersonas.setItems(personasFiltradas); // Actualiza la tabla con la lista filtrada
    }
}