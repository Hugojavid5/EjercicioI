module org.hugo.ejercicioi {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens org.hugo.ejercicioi to javafx.fxml;
    exports org.hugo.ejercicioi;
}