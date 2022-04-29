module com.example.colorcluster {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.desktop;

    opens com.example.colorcluster to javafx.fxml;
    exports com.example.colorcluster;
}