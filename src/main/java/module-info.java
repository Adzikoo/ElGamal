module org.example.elgamal {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.elgamal to javafx.fxml;
    exports org.example.elgamal;
}