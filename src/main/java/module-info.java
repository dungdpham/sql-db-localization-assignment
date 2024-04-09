module com.example.homeassignment020424 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.homeassignment020424 to javafx.fxml;
    exports com.example.homeassignment020424;
}