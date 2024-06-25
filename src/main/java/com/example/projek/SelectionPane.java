package com.example.projek;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class SelectionPane extends AnchorPane {

    public SelectionPane() {
        Image backgroundImage = new Image("https://www.pixelstalk.net/wp-content/uploads/2016/08/Deadpool-Iphone-HD-Background.jpg");
        ImageView backgroundImageView = new ImageView(backgroundImage);
        double PREF_WIDTH = 500;
        backgroundImageView.setFitWidth(PREF_WIDTH);
        double PREF_HEIGHT = 700;
        backgroundImageView.setFitHeight(PREF_HEIGHT);

        Label selectionLabel = new Label("Pilih Jenis Tiket");
        selectionLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: white; -fx-font-weight: bold;");

        Button busButton = new Button("Bus");
        busButton.setStyle("-fx-background-color: #4682b4; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 10;");
        busButton.setOnAction(e -> showBusPane());

        Button trainButton = new Button("Kereta");
        trainButton.setStyle("-fx-background-color: #4682b4; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 10;");
        trainButton.setOnAction(e -> showTrainPane());

        Button planeButton = new Button("Pesawat");
        planeButton.setStyle("-fx-background-color: #4682b4; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 10;");
        planeButton.setOnAction(e -> showPlanePane());

        Button exitButton = new Button("Exit");
        exitButton.setStyle("-fx-background-color: #DC143C; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 10;");
        exitButton.setOnAction(e -> System.exit(0));

        StackPane layout = new StackPane();
        layout.getChildren().addAll(backgroundImageView, selectionLabel, busButton, trainButton, planeButton, exitButton);
        StackPane.setAlignment(selectionLabel, Pos.TOP_CENTER);
        StackPane.setMargin(selectionLabel, new Insets(20));
        StackPane.setAlignment(busButton, Pos.CENTER_LEFT);
        StackPane.setAlignment(trainButton, Pos.CENTER);
        StackPane.setAlignment(planeButton, Pos.CENTER_RIGHT);
        StackPane.setAlignment(exitButton, Pos.BOTTOM_CENTER);
        StackPane.setMargin(busButton, new Insets(0, 0, 0, 50));
        StackPane.setMargin(planeButton, new Insets(0, 50, 0, 0));
        StackPane.setMargin(exitButton, new Insets(20));

        getChildren().add(layout);
        setPrefSize(PREF_WIDTH, PREF_HEIGHT);

        AnchorPane.setTopAnchor(backgroundImageView, 0.0);
        AnchorPane.setBottomAnchor(backgroundImageView, 0.0);
        AnchorPane.setLeftAnchor(backgroundImageView, 0.0);
        AnchorPane.setRightAnchor(backgroundImageView, 0.0);
    }

    private void showBusPane() {
        BusPane busPane = new BusPane();
        Scene scene = new Scene(busPane, busPane.getPrefWidth(), busPane.getPrefHeight());
        Stage stage = (Stage) getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Sistem Pemesanan Tiket Bus");
    }

    private void showTrainPane() {
        KeretaPane trainPane = new KeretaPane();
        Scene scene = new Scene(trainPane, trainPane.getPrefWidth(), trainPane.getPrefHeight());
        Stage stage = (Stage) getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Sistem Pemesanan Tiket Kereta");
    }

    private void showPlanePane() {
        PesawatPane pesawatPane = new PesawatPane();
        Scene scene = new Scene(pesawatPane, pesawatPane.getPrefWidth(), pesawatPane.getPrefHeight());
        Stage stage = (Stage) getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Sistem Pemesanan Tiket Pesawat");
    }
}
