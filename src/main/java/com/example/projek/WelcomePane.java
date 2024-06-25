package com.example.projek;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class WelcomePane extends StackPane {
    private final double PREF_WIDTH = 500;  // Ganti dengan lebar gambar
    private final double PREF_HEIGHT = 700; // Ganti dengan tinggi gambar

    public WelcomePane() {
        // Load background image
        Image backgroundImage = new Image("https://www.pixelstalk.net/wp-content/uploads/2016/08/Deadpool-Iphone-HD-Background.jpg");
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitWidth(PREF_WIDTH);
        backgroundImageView.setFitHeight(PREF_HEIGHT);

        // Create UI elements
        Label welcomeLabel = new Label("Welcome to Ticket Booking System");
        welcomeLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: white; -fx-font-weight: bold;");

        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: #4682b4; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 10;");
        loginButton.setOnAction(e -> {
            SelectionPane selectionPane = new SelectionPane();
            Scene scene = new Scene(selectionPane, selectionPane.getPrefWidth(), selectionPane.getPrefHeight());
            Stage stage = (Stage) getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Pilih Jenis Tiket");
        });

        // Create layout
        StackPane layout = new StackPane();
        layout.getChildren().addAll(backgroundImageView, welcomeLabel, loginButton);
        StackPane.setAlignment(welcomeLabel, Pos.TOP_CENTER);
        StackPane.setMargin(welcomeLabel, new Insets(20));
        StackPane.setAlignment(loginButton, Pos.CENTER);

        getChildren().add(layout);
        setPrefSize(PREF_WIDTH, PREF_HEIGHT);
    }
}
