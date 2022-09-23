module com.lab5.lab5 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.lab5.lab5 to javafx.fxml;
    exports com.lab5.lab5;
}