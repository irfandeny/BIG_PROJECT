module com.example.projek {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.swing;


    opens com.example.projek to javafx.fxml;
    exports com.example.projek;
}