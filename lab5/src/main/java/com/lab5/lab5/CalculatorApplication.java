package com.lab5.lab5;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CalculatorApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CalculatorApplication.class.getResource("controller-view.fxml"));
        String stylesheet = getClass().getResource("/styles.css").toExternalForm();
        Scene scene = new Scene(fxmlLoader.load(), 330, 360);
        stage.setResizable(false);
        scene.getStylesheets().add(stylesheet);
        stage.setTitle("Calculator");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}