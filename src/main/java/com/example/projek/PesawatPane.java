package com.example.projek;

import javafx.embed.swing.SwingFXUtils;
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
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.MediaPrintableArea;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

public class PesawatPane extends StackPane {
    private final ComboBox<String> penerbanganComboBox;
    private final TextField namaField;
    private final ComboBox<String> kelasComboBox;
    private final ListView<String> tiketListView;
    private final Label totalHargaLabel;
    private final SistemPemesanan sistemPemesanan;
    private double selectionPaneWidth;
    private double selectionPaneHeight;
    private final DatePicker datePicker;

    public PesawatPane() {
        sistemPemesanan = new SistemPemesanan();
        initPenerbanganData();

        Image backgroundImage = new Image(Objects.requireNonNull(getClass().getResource("/images/plane_background.jpg")).toExternalForm());
        ImageView backgroundImageView = new ImageView(backgroundImage);
        double PREF_WIDTH = 650;
        backgroundImageView.setFitWidth(PREF_WIDTH);
        double PREF_HEIGHT = 700;
        backgroundImageView.setFitHeight(PREF_HEIGHT);

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setAlignment(Pos.CENTER);

        Label penerbanganLabel = new Label("Pilih Penerbangan:");
        penerbanganLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #333; -fx-font-weight: bold;");
        penerbanganComboBox = new ComboBox<>();
        for (Penerbangan penerbangan : sistemPemesanan.getPenerbanganList()) {
            penerbanganComboBox.getItems().add(penerbangan.toString());
        }
        penerbanganComboBox.getSelectionModel().selectFirst();
        penerbanganComboBox.setStyle("-fx-background-color: #ffffff; -fx-border-color: #ccc; -fx-padding: 5;");

        Label namaLabel = new Label("Nama Penumpang:");
        namaLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #333; -fx-font-weight: bold;");
        namaField = new TextField();
        namaField.setStyle("-fx-background-color: #ffffff; -fx-border-color: #ccc; -fx-padding: 5;");

        Label kelasLabel = new Label("Pilih Kelas:");
        kelasLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #333; -fx-font-weight: bold;");
        kelasComboBox = new ComboBox<>();
        kelasComboBox.getItems().addAll("Ekonomi", "Bisnis");
        kelasComboBox.getSelectionModel().selectFirst();
        kelasComboBox.setStyle("-fx-background-color: #ffffff; -fx-border-color: #ccc; -fx-padding: 5; -fx-font-weight: bold;");

        datePicker = new DatePicker();
        datePicker.setConverter(new javafx.util.StringConverter<>() {
            final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy");

            @Override
            public String toString(java.time.LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public java.time.LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return java.time.LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });

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
        tiketListView.setOpacity(0.9);

        totalHargaLabel = new Label("Total Harga: Rp0");
        totalHargaLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #B22222;");

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #DC143C; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 10;");
        backButton.setOnAction(e -> {
            SelectionPane selectionPane = new SelectionPane();
            Scene scene = new Scene(selectionPane, selectionPaneWidth, selectionPaneHeight);
            Stage stage = (Stage) getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Pilih Jenis Tiket");
        });

        gridPane.add(penerbanganLabel, 0, 0);
        gridPane.add(penerbanganComboBox, 1, 0);
        gridPane.add(namaLabel, 0, 1);
        gridPane.add(namaField, 1, 1);
        gridPane.add(kelasLabel, 0, 2);
        gridPane.add(kelasComboBox, 1, 2);
        gridPane.add(datePicker, 1, 3); // Adding DatePicker to the gridPane
        gridPane.add(pesanButton, 1, 4);
        gridPane.add(cetakButton, 1, 5);
        gridPane.add(listLabel, 0, 6);
        gridPane.add(tiketListView, 1, 6);
        gridPane.add(totalHargaLabel, 1, 7);
        gridPane.add(backButton, 1, 8);

        getChildren().addAll(backgroundImageView, gridPane);
        setPrefSize(PREF_WIDTH, PREF_HEIGHT);
    }

    private String generateBookingID() {
        return String.format("%08d", (int) (Math.random() * 100000000));
    }

    private void initPenerbanganData() {
        sistemPemesanan.tambahPenerbangan(new Penerbangan("AIR ASIA", "Jakarta", "Bali", "08:00", "10:00", 1000000, 2000000));
        sistemPemesanan.tambahPenerbangan(new Penerbangan("GARUDA", "Surabaya", "Makasar", "09:00", "10:30", 800000, 1600000));
        sistemPemesanan.tambahPenerbangan(new Penerbangan("LION AIR", "Jakarta", "Medan", "11:00", "13:30", 1200000, 2400000));
        sistemPemesanan.tambahPenerbangan(new Penerbangan("BATIK AIR", "Surabaya", "Bali", "08:00", "10:00", 1000000, 2000000));
        sistemPemesanan.tambahPenerbangan(new Penerbangan("CITILINK", "Jakarta", "Surabaya", "09:00", "10:30", 800000, 1600000));
        sistemPemesanan.tambahPenerbangan(new Penerbangan("SRIWIJAYA", "Makasar", "Surabaya", "11:00", "13:30", 1200000, 2400000));
        selectionPaneWidth = 512;
        selectionPaneHeight = 512;
    }

    private void pesanTiket() {
        int indeksPenerbangan = penerbanganComboBox.getSelectionModel().getSelectedIndex();
        String namaPenumpang = namaField.getText();
        String tipeKelas = kelasComboBox.getSelectionModel().getSelectedItem();
        LocalDate tanggalKeberangkatan = datePicker.getValue();

        if (namaPenumpang.isEmpty() || tanggalKeberangkatan == null) {
            showErrorMessage("Nama penumpang dan tanggal keberangkatan harus diisi.");
            return;
        }
        String bookingID = generateBookingID();
        sistemPemesanan.pesanTiket(indeksPenerbangan, namaPenumpang, tipeKelas, tanggalKeberangkatan, bookingID);
        updateTiketListView();
    }

    private void updateTiketListView() {
        tiketListView.getItems().clear();
        int totalHarga = 0;
        for (Tiket tiket : sistemPemesanan.getTiketList()) {
            tiketListView.getItems().add(tiket.toString());
            totalHarga += tiket.harga();
        }
        totalHargaLabel.setText("Total Harga: Rp" + totalHarga);
    }

    private void cetakTiket() {
        String selectedTiket = tiketListView.getSelectionModel().getSelectedItem();
        java.time.LocalDate selectedDate = datePicker.getValue(); // Get selected date from DatePicker

        if (selectedTiket != null) {
            PrinterJob printerJob = PrinterJob.getPrinterJob();
            printerJob.setPrintable((graphics, pageFormat, pageIndex) -> {
                if (pageIndex > 0) {
                    return Printable.NO_SUCH_PAGE;
                }

                Graphics2D g2d = (Graphics2D) graphics;
                g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

                try {
                    Image backgroundImage = new Image(Objects.requireNonNull(getClass().getResource("/images/tiket_plane_background.png")).toExternalForm());
                    BufferedImage bufferedImage = SwingFXUtils.fromFXImage(backgroundImage, null);
                    g2d.drawImage(bufferedImage, 0, 0, (int) pageFormat.getWidth(), (int) pageFormat.getHeight(), null);

                    g2d.setFont(new Font("Arial", Font.BOLD, 16));
                    g2d.setColor(Color.BLACK);

                    Tiket selectedTicket = sistemPemesanan.getTiketList().stream()
                            .filter(t -> t.toString().equals(selectedTiket))
                            .findFirst()
                            .orElse(null);

                    if (selectedTicket != null) {
                        int y = 150;
                        int lineHeight = 30;
                        int leftMargin = 50;

                        String[] headerInfo = {
                                "E-ticket",
                                "Return Ticket",
                                "",
                                selectedTicket.penerbangan().namaPenerbangan(),
                                selectedDate.getDayOfWeek()+","+selectedDate.toString(),
                                "",
                                "Booking ID: "+generateBookingID(),
                        };

                        for (String line : headerInfo) {
                            g2d.drawString(line, leftMargin, y);
                            y += lineHeight;
                        }

                        y += lineHeight; // Extra space

                        String[] ticketInfo = {
                                "Pesawat: " + selectedTicket.penerbangan().namaPenerbangan(),
                                "Asal: " + selectedTicket.penerbangan().asal(),
                                "Tujuan: " + selectedTicket.penerbangan().tujuan(),
                                "Berangkat: " + selectedTicket.penerbangan().waktuBerangkat(),
                                "Tiba: " + selectedTicket.penerbangan().waktuTiba(),
                                "Penumpang: " + selectedTicket.namaPenumpang(),
                                "Kelas: " + selectedTicket.tipeKelas(),
                                "Harga: Rp" + selectedTicket.harga(),
                        };

                        for (String line : ticketInfo) {
                            g2d.drawString(line, leftMargin, y);
                            y += lineHeight;
                        }
                    } else {
                        showErrorMessage("Tiket tidak ditemukan.");
                        return Printable.NO_SUCH_PAGE;
                    }

                } catch (Exception e) {
                    showErrorMessage("Gagal memuat gambar: " + e.getMessage());
                    return Printable.NO_SUCH_PAGE;
                }

                return Printable.PAGE_EXISTS;
            });

            PrintRequestAttributeSet attrs = new HashPrintRequestAttributeSet();
            attrs.add(new MediaPrintableArea(0, 0, 72, 72, MediaPrintableArea.INCH));
            printerJob.setJobName("Cetak Tiket");

            boolean doPrint = printerJob.printDialog(attrs);
            if (doPrint) {
                try {
                    printerJob.print(attrs);
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


record Penerbangan(String namaPenerbangan, String asal, String tujuan, String waktuBerangkat, String waktuTiba, int hargaEkonomi, int hargaBisnis) {

    @Override
    public String toString() {
        return namaPenerbangan + " dari " + asal + " ke " + tujuan + " | Berangkat: " + waktuBerangkat + " | Tiba: " + waktuTiba;
    }
}

record Tiket(Penerbangan penerbangan, String namaPenumpang, String tipeKelas, int harga, LocalDate tanggalKeberangkatan, String bookingID) {
    @Override
    public String toString() {
        return "Tiket untuk: " + namaPenumpang + " pada " + penerbangan.namaPenerbangan() +
                " | Tanggal Keberangkatan: " + tanggalKeberangkatan.toString() +
                " | Kelas: " + tipeKelas + " | Harga: Rp" + harga +
                " | Booking ID: " + bookingID;
    }
}

class SistemPemesanan {
    private final ArrayList<Penerbangan> penerbanganList;
    private final ArrayList<Tiket> tiketList;

    public SistemPemesanan() {
        penerbanganList = new ArrayList<>();
        tiketList = new ArrayList<>();
    }

    public void tambahPenerbangan(Penerbangan penerbangan) {
        penerbanganList.add(penerbangan);
    }

    public ArrayList<Penerbangan> getPenerbanganList() {
        return penerbanganList;
    }

    public ArrayList<Tiket> getTiketList() {
        return tiketList;
    }

    public void pesanTiket(int indeksPenerbangan, String namaPenumpang, String tipeKelas, LocalDate tanggalKeberangkatan, String bookingID) {
        if (indeksPenerbangan >= 0 && indeksPenerbangan < penerbanganList.size()) {
            Penerbangan penerbangan = penerbanganList.get(indeksPenerbangan);
            int harga = tipeKelas.equalsIgnoreCase("Ekonomi") ? penerbangan.hargaEkonomi() : penerbangan.hargaBisnis();
            Tiket tiket = new Tiket(penerbangan, namaPenumpang, tipeKelas, harga, tanggalKeberangkatan,bookingID);
            tiketList.add(tiket);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Konfirmasi Pemesanan");
            alert.setHeaderText(null);
            alert.setContentText("Tiket berhasil dipesan untuk " + namaPenumpang + " pada penerbangan " + penerbangan.namaPenerbangan() + ".");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Kesalahan Pemesanan");
            alert.setHeaderText(null);
            alert.setContentText("Pilihan penerbangan tidak valid.");
            alert.showAndWait();
        }
    }
}
