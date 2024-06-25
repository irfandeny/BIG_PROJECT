package com.example.projek;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.awt.*;
import java.awt.print.*;
import java.util.ArrayList;

public class KeretaPane extends StackPane {
    private final ComboBox<String> keretaComboBox;
    private final TextField namaField;
    private final ComboBox<String> kelasComboBox;
    private final ListView<String> tiketListView;
    private final Label totalHargaLabel;
    private final SistemPemesananKereta sistemPemesananKereta;

    private final double PREF_WIDTH = 650;  // Ganti dengan lebar gambar
    private final double PREF_HEIGHT = 700; // Ganti dengan tinggi gambar

    private double selectionPaneWidth;
    private double selectionPaneHeight;

    public KeretaPane() {
        sistemPemesananKereta = new SistemPemesananKereta();
        initKeretaData();

        // Load background image
        Image backgroundImage = new Image(getClass().getResource("/images/train_background.jpg").toExternalForm());
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitWidth(PREF_WIDTH);
        backgroundImageView.setFitHeight(PREF_HEIGHT);

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setAlignment(Pos.CENTER);

        Label keretaLabel = new Label("Pilih Kereta:");
        keretaLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #333; -fx-font-weight: bold;");
        keretaComboBox = new ComboBox<>();
        for (Kereta kereta : sistemPemesananKereta.getKeretaList()) {
            keretaComboBox.getItems().add(kereta.toString());
        }
        keretaComboBox.getSelectionModel().selectFirst();
        keretaComboBox.setStyle("-fx-background-color: #ffffff; -fx-border-color: #ccc; -fx-padding: 5; ");

        Label namaLabel = new Label("Nama Penumpang:");
        namaLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #333; -fx-font-weight: bold;");
        namaField = new TextField();
        namaField.setStyle("-fx-background-color: #ffffff; -fx-border-color: #ccc; -fx-padding: 5; -fx-font-weight: bold;");

        Label kelasLabel = new Label("Pilih Kelas:");
        kelasLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #333; -fx-font-weight: bold;");
        kelasComboBox = new ComboBox<>();
        kelasComboBox.getItems().addAll("Ekonomi", "Bisnis");
        kelasComboBox.getSelectionModel().selectFirst();
        kelasComboBox.setStyle("-fx-background-color: #ffffff; -fx-border-color: #ccc; -fx-padding: 5; -fx-font-weight: bold;");

        Button pesanButton = new Button("Pesan Tiket");
        pesanButton.setStyle("-fx-background-color: #4682b4; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 10;");
        pesanButton.setOnAction(e -> pesanTiket());

        Button cetakButton = new Button("Cetak Tiket");
        cetakButton.setStyle("-fx-background-color: #4682b4; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 10;");
        cetakButton.setOnAction(e -> cetakTiket());

        Label listLabel = new Label("Tiket yang \nDipesan:");
        listLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #8B0000; -fx-font-weight: bold;");

        tiketListView = new ListView<>();
        tiketListView.setStyle("-fx-background-color: #ffffff; -fx-border-color: #ccc;");
        tiketListView.setOpacity(0.9);

        totalHargaLabel = new Label("Total Harga: Rp0");
        totalHargaLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #B22222;");

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #DC143C; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 10;");
        backButton.setOnAction(e -> {
            SelectionPane selectionPane = new SelectionPane();

            // Buat Scene baru dengan ukuran yang sudah disimpan sebelumnya
            Scene scene = new Scene(selectionPane, selectionPaneWidth, selectionPaneHeight);
            Stage stage = (Stage) getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Pilih Jenis Tiket");
        });

        gridPane.add(keretaLabel, 0, 0);
        gridPane.add(keretaComboBox, 1, 0);
        gridPane.add(namaLabel, 0, 1);
        gridPane.add(namaField, 1, 1);
        gridPane.add(kelasLabel, 0, 2);
        gridPane.add(kelasComboBox, 1, 2);
        gridPane.add(pesanButton, 1, 3);
        gridPane.add(cetakButton, 1, 4);
        gridPane.add(listLabel, 0, 5);
        gridPane.add(tiketListView, 1, 5);
        gridPane.add(totalHargaLabel, 1, 6);
        gridPane.add(backButton, 1, 7);

        getChildren().addAll(backgroundImageView, gridPane);
        setPrefSize(PREF_WIDTH, PREF_HEIGHT);
    }

    private void initKeretaData() {
        sistemPemesananKereta.tambahKereta(new Kereta("KERETA API ARGO BROMO", "Jakarta", "Surabaya", "08:00", "16:00", 500000, 1000000));
        sistemPemesananKereta.tambahKereta(new Kereta("KERETA API GAJAYANA", "Jakarta", "Malang", "09:00", "17:00", 550000, 1100000));
        sistemPemesananKereta.tambahKereta(new Kereta("KERETA API TEGAL BAHARI", "Jakarta", "Tegal", "10:00", "14:00", 300000, 600000));
        selectionPaneWidth = 500;
        selectionPaneHeight = 700;
    }

    private void pesanTiket() {
        int indeksKereta = keretaComboBox.getSelectionModel().getSelectedIndex();
        String namaPenumpang = namaField.getText();
        String tipeKelas = kelasComboBox.getSelectionModel().getSelectedItem();

        if (namaPenumpang.isEmpty()) {
            showErrorMessage("Nama penumpang tidak boleh kosong.");
            return;
        }
        sistemPemesananKereta.pesanTiket(indeksKereta, namaPenumpang, tipeKelas);
        updateTiketListView();
    }

    private void updateTiketListView() {
        tiketListView.getItems().clear();
        int totalHarga = 0;
        for (TiketKereta tiket : sistemPemesananKereta.getTiketKeretaList()) {
            tiketListView.getItems().add(tiket.toString());
            totalHarga += tiket.harga();
        }
        totalHargaLabel.setText("Total Harga: Rp" + totalHarga);
    }

    private void cetakTiket() {
        String selectedTiket = tiketListView.getSelectionModel().getSelectedItem();
        if (selectedTiket != null) {
            PrinterJob printerJob = PrinterJob.getPrinterJob();
            printerJob.setPrintable((graphics, pageFormat, pageIndex) -> {
                if (pageIndex > 0) {
                    return Printable.NO_SUCH_PAGE;
                }

                Graphics2D g2d = (Graphics2D) graphics;
                g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

                int x = 50;
                int y = 50;
                int cellHeight = 25;
                int labelWidth = 150;
                int valueWidth = 200;
                int tiketWidth = labelWidth + valueWidth + 20;
                int columnGap = 10;
                int totalHeight = 10 * cellHeight;

                g2d.setFont(new Font("Arial", Font.BOLD, 12));
                g2d.setColor(Color.BLACK);

                g2d.setColor(new Color(135, 206, 250));
                g2d.fillRect(x, y, tiketWidth, totalHeight);
                g2d.setColor(Color.BLACK);
                g2d.drawRect(x, y, tiketWidth, totalHeight);
                y += cellHeight;

                String header = "=== Tiket Kereta ===";
                g2d.setFont(new Font("Arial", Font.BOLD, 16));
                g2d.drawString(header, x + (tiketWidth - g2d.getFontMetrics().stringWidth(header)) / 2, y);
                y += cellHeight;

                TiketKereta selectedTicket = sistemPemesananKereta.getTiketKeretaList().stream()
                        .filter(tiket -> tiket.toString().equals(selectedTiket))
                        .findFirst()
                        .orElse(null);

                if (selectedTicket != null) {
                    int labelX = x + 10;
                    int valueX = labelX + labelWidth + columnGap;

                    String[] labels = {"Nama Kereta", "Nama Penumpang", "Asal", "Tujuan", "Berangkat", "Tiba", "Kelas", "Harga"};
                    String[] values = {
                            selectedTicket.kereta().namaKereta(),
                            selectedTicket.namaPenumpang(),
                            selectedTicket.kereta().asal(),
                            selectedTicket.kereta().tujuan(),
                            selectedTicket.kereta().waktuBerangkat(),
                            selectedTicket.kereta().waktuTiba(),
                            selectedTicket.tipeKelas(),
                            "Rp" + selectedTicket.harga()
                    };

                    for (int i = 0; i < labels.length; i++) {
                        g2d.setColor(new Color(131, 154, 244));
                        int y1 = y + (i * cellHeight) - cellHeight / 2;
                        g2d.fillRect(x, y1, tiketWidth, cellHeight);
                        g2d.setFont(new Font("Arial", Font.BOLD, 12));
                        g2d.setColor(Color.BLACK);
                        g2d.drawString(labels[i], labelX, y + (i * cellHeight));
                        g2d.setFont(new Font("Arial", Font.PLAIN, 12));
                        g2d.setColor(Color.BLACK);
                        g2d.drawString(values[i], valueX, y + (i * cellHeight));

                        g2d.drawLine(x, y1, x + tiketWidth, y1);
                        g2d.drawLine(labelX - 10, y1, labelX - 10, y + (i * cellHeight) + cellHeight / 2);
                        g2d.drawLine(valueX - columnGap, y1, valueX - columnGap, y + (i * cellHeight) + cellHeight / 2);
                    }
                    int y2 = y + (labels.length * cellHeight) - cellHeight / 2;
                    g2d.drawLine(x, y2, x + tiketWidth, y2);
                }

                return Printable.PAGE_EXISTS;
            });

            boolean doPrint = printerJob.printDialog();
            if (doPrint) {
                try {
                    printerJob.print();
                } catch (PrinterException e) {
                    showErrorMessage("Gagal mencetak tiket: " + e.getMessage());
                }
            }
        } else {
            showErrorMessage("Pilih tiket yang ingin dicetak.");
        }
    }

    private void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Kesalahan");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

    record Kereta(String namaKereta, String asal, String tujuan, String waktuBerangkat, String waktuTiba,
                  int hargaEkonomi, int hargaBisnis) {

        @Override
        public String toString() {
            return namaKereta + " dari " + asal + " ke " + tujuan + " | Berangkat: " + waktuBerangkat + " | Tiba: " + waktuTiba;
        }
    }

    record TiketKereta(Kereta kereta, String namaPenumpang, String tipeKelas, int harga) {

        @Override
        public String toString() {
            return "Tiket untuk: " + namaPenumpang + " pada " + kereta.namaKereta() + " | Kelas: " + tipeKelas + " | Harga: Rp" + harga;
        }
    }
class SistemPemesananKereta {
    private final ArrayList<Kereta> keretaList;
    private final ArrayList<TiketKereta> tiketKeretaList;

    public SistemPemesananKereta() {
        keretaList = new ArrayList<>();
        tiketKeretaList = new ArrayList<>();
    }

    public void tambahKereta(Kereta kereta) {
        keretaList.add(kereta);
    }

    public ArrayList<Kereta> getKeretaList() {
        return keretaList;
    }

    public ArrayList<TiketKereta> getTiketKeretaList() {
        return tiketKeretaList;
    }

    public void pesanTiket(int indeksKereta, String namaPenumpang, String tipeKelas) {
        if (indeksKereta >= 0 && indeksKereta < keretaList.size()) {
            Kereta kereta = keretaList.get(indeksKereta);
            int harga = tipeKelas.equalsIgnoreCase("Ekonomi") ? kereta.hargaEkonomi() : kereta.hargaBisnis();
            TiketKereta tiketKereta = new TiketKereta(kereta, namaPenumpang, tipeKelas, harga);
            tiketKeretaList.add(tiketKereta);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Konfirmasi Pemesanan");
            alert.setHeaderText(null);
            alert.setContentText("Tiket berhasil dipesan untuk " + namaPenumpang + " pada kereta " + kereta.namaKereta() + ".");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Kesalahan Pemesanan");
            alert.setHeaderText(null);
            alert.setContentText("Pilihan kereta tidak valid.");
            alert.showAndWait();
        }
    }
}