package com.example.projek;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import java.awt.*;
import java.awt.print.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import java.util.ArrayList;

public class BusPane extends GridPane {
    private final ComboBox<String> busComboBox;
    private final TextField namaField;
    private final ComboBox<String> kelasComboBox;
    private final ListView<String> tiketListView;
    private final Label totalHargaLabel;
    private final SistemPemesananBus sistemPemesananBus;

    public BusPane() {
        sistemPemesananBus = new SistemPemesananBus();
        initTransportasiData();

        setPadding(new Insets(10, 10, 10, 10));
        setVgap(10);
        setHgap(10);
        setAlignment(Pos.CENTER);

        Label transportasiLabel = new Label("Pilih Transportasi:");
        transportasiLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #333; -fx-font-weight: bold;");
        busComboBox = new ComboBox<>();
        for (Bus bus : sistemPemesananBus.getBusList()) {
            busComboBox.getItems().add(bus.toString());
        }
        busComboBox.getSelectionModel().selectFirst();
        busComboBox.setStyle("-fx-background-color: #ffffff; -fx-border-color: #ccc; -fx-padding: 5;");

        Label namaLabel = new Label("Nama Penumpang:");
        namaLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #333; -fx-font-weight: bold;");
        namaField = new TextField();
        namaField.setStyle("-fx-background-color: #ffffff; -fx-border-color: #ccc; -fx-padding: 5;-fx-font-weight: bold;");

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

        Label listLabel = new Label("Tiket yang dipesan:");
        listLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #333; -fx-font-weight: bold;");

        tiketListView = new ListView<>();
        tiketListView.setStyle("-fx-background-color: #ffffff; -fx-border-color: #ccc;");

        totalHargaLabel = new Label("Total Harga: Rp0");
        totalHargaLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #B22222;");

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #DC143C; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 10;");
        backButton.setOnAction(e -> {
            SelectionPane selectionPane = new SelectionPane();
            Scene scene = new Scene(selectionPane, 400, 200);
            Stage stage = (Stage) getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Pilih Jenis Tiket");
        });

        add(transportasiLabel, 0, 0);
        add(busComboBox, 1, 0);
        add(namaLabel, 0, 1);
        add(namaField, 1, 1);
        add(kelasLabel, 0, 2);
        add(kelasComboBox, 1, 2);
        add(pesanButton, 1, 3);
        add(cetakButton, 1, 4);
        add(listLabel, 0, 5);
        add(tiketListView, 1, 5);
        add(totalHargaLabel, 1, 6);
        add(backButton,1,7);
    }

    private void initTransportasiData() {
        sistemPemesananBus.tambahBus(new Bus("BUS MALAM JAYA", "Jakarta", "Surabaya", "20:00", "06:00", 250000, 350000));
        sistemPemesananBus.tambahBus(new Bus("BUS SINAR JAYA", "Jakarta", "Bandung", "08:00", "12:00", 100000, 150000));
        sistemPemesananBus.tambahBus(new Bus("BUS MURNI JAYA", "Surabaya", "Yogyakarta", "09:00", "17:00", 150000, 250000));
    }

    private void pesanTiket() {
        int indeksBus = busComboBox.getSelectionModel().getSelectedIndex();
        String namaPenumpang = namaField.getText();
        String kelas = kelasComboBox.getSelectionModel().getSelectedItem();

        if (namaPenumpang.isEmpty()) {
            showErrorMessage("Nama penumpang tidak boleh kosong.");
            return;
        }
        sistemPemesananBus.pesanTiket(indeksBus, namaPenumpang, kelas);
        updateTiketListView();
    }
    private void updateTiketListView() {
        tiketListView.getItems().clear();
        int totalHarga = 0;
        for (TiketBus tiket : sistemPemesananBus.getTiketBusList()) {
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

                String header = "=== Tiket Bus ===";
                g2d.setFont(new Font("Arial", Font.BOLD, 14));
                int headerWidth = g2d.getFontMetrics().stringWidth(header);
                g2d.drawString(header, x + (tiketWidth - headerWidth) / 2, y - 5);

                y += cellHeight;
                g2d.drawLine(x, y, x + tiketWidth, y);
                y += cellHeight / 2;
                g2d.setFont(new Font("Arial", Font.PLAIN, 12));

                TiketBus selectedTicket = sistemPemesananBus.getTiketBusList().stream()
                        .filter(t -> t.toString().equals(selectedTiket))
                        .findFirst()
                        .orElse(null);

                if (selectedTicket != null) {
                    int labelX = x + 10;
                    int valueX = labelX + labelWidth + columnGap;

                    String[] labels = {"Nama Bus", "Nama Penumpang", "Asal", "Tujuan", "Berangkat", "Tiba", "Kelas", "Harga"};
                    String[] values = {
                            selectedTicket.bus().namaBus(),
                            selectedTicket.namaPenumpang(),
                            selectedTicket.bus().asal(),
                            selectedTicket.bus().tujuan(),
                            selectedTicket.bus().waktuBerangkat(),
                            selectedTicket.bus().waktuTiba(),
                            selectedTicket.tipeKelas(),
                            "Rp" + selectedTicket.harga()
                    };

                    for (int i = 0; i < labels.length; i++) {
                        g2d.setColor(new Color(131, 170, 126));
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
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Kesalahan");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

record Bus(String namaBus, String asal, String tujuan, String waktuBerangkat, String waktuTiba, int hargaEkonomi, int hargaBisnis) {

    @Override
    public String toString() {
        return namaBus + " dari " + asal + " ke " + tujuan + " | Berangkat: " + waktuBerangkat + " | Tiba: " + waktuTiba;
    }
}

record TiketBus(Bus bus, String namaPenumpang, String tipeKelas, int harga) {

    @Override
    public String toString() {
        return "Bus: " + bus.namaBus() + ", Nama: " + namaPenumpang + ", Kelas: " + tipeKelas + ", Harga: Rp" + harga;
    }
}


class SistemPemesananBus {
    private final ArrayList<Bus> busList;
    private final ArrayList<TiketBus> tiketBusList;

    public SistemPemesananBus() {
        busList = new ArrayList<>();
        tiketBusList = new ArrayList<>();
    }

    public void tambahBus(Bus bus) {
        busList.add(bus);
    }

    public ArrayList<Bus> getBusList() {
        return busList;
    }

    public ArrayList<TiketBus> getTiketBusList() {
        return tiketBusList;
    }

    public void pesanTiket(int indeksBus, String namaPenumpang, String tipeKelas) {
        if (indeksBus >= 0 && indeksBus < busList.size()) {
            Bus bus = busList.get(indeksBus);
            int harga = tipeKelas.equalsIgnoreCase("Ekonomi") ? bus.hargaEkonomi() : bus.hargaBisnis();
            TiketBus tiketBus = new TiketBus(bus, namaPenumpang, tipeKelas, harga);
            tiketBusList.add(tiketBus);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Konfirmasi Pemesanan");
            alert.setHeaderText(null);
            alert.setContentText("Tiket berhasil dipesan untuk " + namaPenumpang + " pada bus " + bus.namaBus() + ".");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Kesalahan Pemesanan");
            alert.setHeaderText(null);
            alert.setContentText("Pilihan bus tidak valid.");
            alert.showAndWait();
        }
    }
}

