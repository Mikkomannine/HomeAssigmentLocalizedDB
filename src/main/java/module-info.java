module org.example.homeassigmentdblocal {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens org.example.homeassigmentdblocal to javafx.fxml;
    exports org.example.homeassigmentdblocal;
}