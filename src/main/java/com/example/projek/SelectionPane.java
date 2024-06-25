package com.example.projek;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;

public class SelectionPane extends GridPane {

    public SelectionPane() {
        setPadding(new Insets(20));
        setVgap(10);
        setHgap(10);
        setAlignment(Pos.CENTER);

        Label selectionLabel = new Label("Pilih Jenis Tiket");
        selectionLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

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

        add(selectionLabel, 0, 0, 3, 1);
        add(busButton, 0, 1);
        add(trainButton, 1, 1);
        add(planeButton, 2, 1);
        add(exitButton, 1, 2);

        setHalignment(exitButton, javafx.geometry.HPos.CENTER);
        setAlignment(Pos.CENTER);
    }

    private void showBusPane() {
        BusPane busPane = new BusPane();
        Scene scene = new Scene(busPane, 670, 400);
        busPane.setStyle("-fx-background-color: #90EE90;");
        Stage stage = (Stage) getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Sistem Pemesanan Tiket Bus");
    }

    private void showTrainPane() {
        KeretaPane trainPane = new KeretaPane();
        Scene scene = new Scene(trainPane, 670, 400);
        trainPane.setStyle("-fx-background-color: #4169E1;");
        Stage stage = (Stage) getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Sistem Pemesanan Tiket Kereta");
    }

    private void showPlanePane() {
        PesawatPane pesawatPane = new PesawatPane();
        Scene scene = new Scene(pesawatPane, 670, 400);
        pesawatPane.setStyle("-fx-background-color: #E9967A;");
        Stage stage = (Stage) getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Sistem Pemesanan Tiket Pesawat");
    }
}