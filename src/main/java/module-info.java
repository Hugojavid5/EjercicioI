module org.hugo.ejercicioi {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.hugo.ejercicioi to javafx.fxml;
    exports org.hugo.ejercicioi;
}