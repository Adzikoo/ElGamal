package org.example.elgamal;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class KryptoApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(KryptoApplication.class.getResource("view-ElGamal.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Triple DES Algorithm");
        stage.setScene(scene);
        stage.setMinHeight(scene.getHeight());
        stage.setMinWidth(scene.getWidth());
        stage.show();
    }


    public static void main(String[] args) {
        launch();

    }
}

