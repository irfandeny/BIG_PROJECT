package com.example.projek;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

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
        setBackground(busPane, "/com/example/projek/bus_background.jpg");
        Stage stage = (Stage) getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Sistem Pemesanan Tiket Bus");
    }

    private void showTrainPane() {
        KeretaPane trainPane = new KeretaPane();
        Scene scene = new Scene(trainPane, 670, 400);
        setBackground(trainPane, "/com/example/projek/train_background.jpg");
        Stage stage = (Stage) getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Sistem Pemesanan Tiket Kereta");
    }

    private void showPlanePane() {
        PesawatPane pesawatPane = new PesawatPane();
        Scene scene = new Scene(pesawatPane, 670, 400);
        setBackground(pesawatPane, "/com/example/projek/plane_background.jpg");
        Stage stage = (Stage) getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Sistem Pemesanan Tiket Pesawat");
    }

    private void setBackground(GridPane pane, String imagePath) {
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
        BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true);
        BackgroundImage backgroundImage = new BackgroundImage(
                image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                backgroundSize);
        Background background = new Background(backgroundImage);
        pane.setBackground(background);
    }
}
