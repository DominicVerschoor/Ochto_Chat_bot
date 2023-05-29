module com.example.ochto {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.datatransfer;
    requires java.desktop;
    requires java.sql;
    requires opencv;


    opens com.example.ochto to javafx.fxml;
    exports com.example.ochto;
}