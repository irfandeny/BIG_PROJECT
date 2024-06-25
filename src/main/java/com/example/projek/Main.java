package com.example.projek;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    public void start(Stage primaryStage) {
        WelcomePane welcomePane = new WelcomePane();
        Scene scene = new Scene(welcomePane, welcomePane.getPrefWidth(), welcomePane.getPrefHeight());

        primaryStage.setTitle("Sistem Pemesanan Tiket Transportasi");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
