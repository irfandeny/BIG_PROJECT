package com.example.projek;

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
import java.awt.image.BufferedImage;
import java.awt.print.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.DatePicker;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.MediaPrintableArea;


public class KeretaPane extends StackPane {
    private final DatePicker datePicker;
    private final ComboBox<String> keretaComboBox;
    private final TextField namaField;
    private final ComboBox<String> kelasComboBox;
    private ComboBox<String> comboSeat;
    private final ListView<String> tiketListView;
    private final Label totalHargaLabel;
    private final SistemPemesananKereta sistemPemesananKereta;

    private double selectionPaneWidth;
    private double selectionPaneHeight;

    public KeretaPane() {
        sistemPemesananKereta = new SistemPemesananKereta();
        initKeretaData();

        Image backgroundImage = new Image(Objects.requireNonNull(getClass().getResource("/images/train_background.jpg")).toExternalForm());
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

        Label seat = new Label("Pilih Kursi");
        seat.setStyle("-fx-font-size: 14px; -fx-text-fill: #333; -fx-font-weight: bold;");
        comboSeat = new ComboBox<>();
        comboSeat.getItems().addAll("A1", "A2", "A3", "B1", "B2", "B3", "C1", "C2", "C3");
        comboSeat.getSelectionModel().selectFirst();
        comboSeat.setStyle("-fx-background-color: #ffffff; -fx-border-color: #ccc; -fx-padding: 5; -fx-font-weight: bold;");

        Label kelasLabel = new Label("Pilih Kelas:");
        kelasLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #333; -fx-font-weight: bold;");
        kelasComboBox = new ComboBox<>();
        kelasComboBox.getItems().addAll("Ekonomi", "Bisnis", "Eksekutif");
        kelasComboBox.getSelectionModel().selectFirst();
        kelasComboBox.setStyle("-fx-background-color: #ffffff; -fx-border-color: #ccc; -fx-padding: 5; -fx-font-weight: bold;");

        Label tanggalLabel = new Label("Tanggal Keberangkatan:");
        tanggalLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #333; -fx-font-weight: bold;");

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

        Label listLabel = new Label("Tiket yang \nDipesan:");
        listLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #8B0000; -fx-font-weight: bold;");

        tiketListView = new ListView<>();
        tiketListView.setStyle("-fx-background-color: #ffffff; -fx-border-color: #ccc;");
        tiketListView.setOpacity(0.9);

        totalHargaLabel = new Label("Total Harga: Rp0");
        totalHargaLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #B22222;");
        
        Label kembalianLabel = new Label();
        kembalianLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #B22222;");

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #DC143C; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 10;");
        backButton.setOnAction(e -> {
            SelectionPane selectionPane = new SelectionPane();
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
        gridPane.add(seat, 0,3);
        gridPane.add(comboSeat, 1,3);
        gridPane.add(tanggalLabel, 0, 4);
        gridPane.add(datePicker, 1, 4);
        gridPane.add(pesanButton, 1, 5);
        gridPane.add(cetakButton, 1, 6);
        gridPane.add(listLabel, 0, 7);
        gridPane.add(tiketListView, 1, 7);
        gridPane.add(totalHargaLabel, 1, 8);
        gridPane.add(backButton, 1, 9);
        gridPane.add(kembalianLabel,1,12);

        getChildren().addAll(backgroundImageView, gridPane);
        setPrefSize(PREF_WIDTH, PREF_HEIGHT);
    }

    private String generateBookingID() {
        return String.format("%08d", (int) (Math.random() * 100000000));
    }

    private void initKeretaData() {
        sistemPemesananKereta.tambahKereta(new Kereta("KERETA API ARGO BROMO", "Jakarta", "Surabaya", "08:00", "16:00", 500000, 1000000));
        sistemPemesananKereta.tambahKereta(new Kereta("KERETA API GAJAYANA", "Jakarta", "Malang", "09:00", "17:00", 550000, 1100000));
        sistemPemesananKereta.tambahKereta(new Kereta("KERETA API TEGAL BAHARI", "Jakarta", "Tegal", "10:00", "14:00", 300000, 600000));
        selectionPaneWidth = 512;
        selectionPaneHeight = 512;
    }

    private void pesanTiket() {
        int indeksKereta = keretaComboBox.getSelectionModel().getSelectedIndex();
        String namaPenumpang = namaField.getText();
        String tipeKelas = kelasComboBox.getSelectionModel().getSelectedItem();
        String kursi = comboSeat.getSelectionModel().getSelectedItem();
        LocalDate tanggalKeberangkatan = datePicker.getValue();

        if (namaPenumpang.isEmpty() || tanggalKeberangkatan == null) {
            showErrorMessage("Nama penumpang dan tanggal keberangkatan harus diisi.");
            return;
        }

        String bookingID = generateBookingID();
        sistemPemesananKereta.pesanTiket(indeksKereta, namaPenumpang, tipeKelas, tanggalKeberangkatan, bookingID, kursi);
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
        LocalDate selectedDate = datePicker.getValue();
        if (selectedTiket != null) {
            double totalHarga = sistemPemesananKereta.hitungTotalHarga();

            TextInputDialog paymentDialog = new TextInputDialog();
            paymentDialog.setTitle("Pembayaran");
            paymentDialog.setHeaderText("Total Harga: Rp" + totalHarga);
            paymentDialog.setContentText("Masukkan jumlah pembayaran:");

            double paymentAmount = Double.parseDouble(paymentDialog.showAndWait().orElse("0"));

            if (paymentAmount < totalHarga) {
                showErrorMessage("Jumlah pembayaran kurang.");
                return;
            }

            double change = paymentAmount - totalHarga;
            showInfoMessage("Pembayaran berhasil. Kembalian: Rp" + change);


            PrinterJob printerJob = PrinterJob.getPrinterJob();
            printerJob.setPrintable((graphics, pageFormat, pageIndex) -> {
                if (pageIndex > 0) {
                    return Printable.NO_SUCH_PAGE;
                }

                Graphics2D g2d = (Graphics2D) graphics;
                g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

                try {
                    Image backgroundImage = new Image(Objects.requireNonNull(getClass().getResource("/images/tiket_train_background.jpg")).toExternalForm());
                    BufferedImage bufferedImage = SwingFXUtils.fromFXImage(backgroundImage, null);
                    g2d.drawImage(bufferedImage, 0, 0, (int) pageFormat.getWidth(), (int) pageFormat.getHeight(), null);

                    g2d.setFont(new Font("Arial", Font.BOLD, 16));
                    g2d.setColor(Color.BLACK);

                    TiketKereta selectedTicket = sistemPemesananKereta.getTiketKeretaList().stream()
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
                                selectedTicket.kereta().namaKereta(),
                                selectedDate.getDayOfWeek()+","+selectedDate.toString(),
                                "",
                                "Booking ID:%s%s".formatted(comboSeat.getSelectionModel().getSelectedItem(), selectedTicket.bookingID()),
                        };

                        for (String line : headerInfo) {
                            g2d.drawString(line, leftMargin, y);
                            y += lineHeight;
                        }

                        y += lineHeight;

                        String[] ticketInfo = {
                                "Kereta: " + selectedTicket.kereta().namaKereta(),
                                "Asal: " + selectedTicket.kereta().asal(),
                                "Tujuan: " + selectedTicket.kereta().tujuan(),
                                "Berangkat: " + selectedTicket.kereta().waktuBerangkat(),
                                "Tiba: " + selectedTicket.kereta().waktuTiba(),
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
                g2d.setFont(new Font("Arial", Font.PLAIN, 10));
                g2d.setColor(Color.black);
                g2d.drawString("© Tiketku", (int) pageFormat.getWidth() - 100, (int) pageFormat.getHeight() - 10);

                return Printable.PAGE_EXISTS;
            });
            PrintRequestAttributeSet attrs = new HashPrintRequestAttributeSet();
            attrs.add(new MediaPrintableArea(0, 0, 72, 72, MediaPrintableArea.INCH));
            printerJob.setJobName("Cetak Tiket");

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
    private void showInfoMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
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
    public double getHargaEkonomi() {
        return hargaEkonomi;
    }

    public double getHargaBisnis() {
        return hargaBisnis;
    }
}

record TiketKereta(Kereta kereta, String namaPenumpang, String tipeKelas, int harga, LocalDate tanggalKeberangkatan, String bookingID, String kursi) {

    @Override
    public String toString() {
        return "Tiket untuk: " + namaPenumpang + " pada " + kereta.namaKereta() +
                " | Tanggal Keberangkatan: " + tanggalKeberangkatan.toString() +
                " | Kelas: " + tipeKelas + " | Harga: Rp" + harga +
                " | Booking ID: " +kursi + bookingID;
    }
    public double getHarga() {
        if (tipeKelas.equals("Ekonomi")) {
            return kereta.getHargaEkonomi();
        } else if (tipeKelas.equals("Bisnis")) {
            return kereta.getHargaBisnis();
        } else {
            return 0;
        }
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
    public double hitungTotalHarga() {
        double totalHarga = 0;
        for (TiketKereta tiket : tiketKeretaList) {
            totalHarga += tiket.getHarga();
        }
        return totalHarga;
    }

    public void pesanTiket(int indeksKereta, String namaPenumpang, String tipeKelas, LocalDate tanggalKeberangkatan, String bookingID, String kursi) {
        if (indeksKereta >= 0 && indeksKereta < keretaList.size()) {
            Kereta kereta = keretaList.get(indeksKereta);
            int harga = tipeKelas.equalsIgnoreCase("Ekonomi") ? kereta.hargaEkonomi() : kereta.hargaBisnis();
            TiketKereta tiketKereta = new TiketKereta(kereta, namaPenumpang, tipeKelas, harga, tanggalKeberangkatan, bookingID, kursi);
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