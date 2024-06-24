package com.example.projek;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {
    public void start(Stage primaryStage) {
        WelcomePane welcomePane = new WelcomePane();
        Scene scene = new Scene(welcomePane, 400, 200);
        welcomePane.setStyle("-fx-background-color: #E6E6FA;");

        primaryStage.setTitle("Sistem Pemesanan Tiket Transportasi");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}


class WelcomePane extends GridPane {

    public WelcomePane() {
        setPadding(new Insets(20));
        setVgap(10);
        setHgap(10);
        setAlignment(Pos.CENTER);

        Label welcomeLabel = new Label("Welcome to Ticket Booking System");
        welcomeLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: #4682b4; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 10;");
        loginButton.setOnAction(e -> {
            SelectionPane selectionPane = new SelectionPane();
            Scene scene = new Scene(selectionPane, 400, 200);
            selectionPane.setStyle("-fx-background-color: #DCDCDC;");
            Stage stage = (Stage) getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Pilih Jenis Tiket");
        });

        add(welcomeLabel, 0, 0);
        add(loginButton, 0, 1);

        // Set alignment for the button to center horizontally
        setHalignment(loginButton, javafx.geometry.HPos.CENTER);
    }
}

