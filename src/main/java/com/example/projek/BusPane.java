package com.example.projek;

import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.MediaPrintableArea;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.print.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

public class BusPane extends StackPane {
    private final ComboBox<String> busComboBox;
    private final TextField namaField;
    private final ComboBox<String> kelasComboBox;
    private final ListView<String> tiketListView;
    private final Label totalHargaLabel;
    private final SistemPemesananBus sistemPemesananBus;
    private final DatePicker datePicker;
    private double selectionPaneWidth;
    private double selectionPaneHeight;

    public BusPane() {
        sistemPemesananBus = new SistemPemesananBus();
        initTransportasiData();

        Image backgroundImage = new Image(Objects.requireNonNull(getClass().getResource("/images/bus_background.jpg")).toExternalForm());
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

        Label transportasiLabel = new Label("Pilih Transportasi:");
        transportasiLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #F0FFFF; -fx-font-weight: bold;");
        busComboBox = new ComboBox<>();
        for (Bus bus : sistemPemesananBus.getBusList()) {
            busComboBox.getItems().add(bus.toString());
        }
        busComboBox.getSelectionModel().selectFirst();
        busComboBox.setStyle("-fx-background-color: #ffffff; -fx-border-color: #ccc; -fx-padding: 5;");

        Label namaLabel = new Label("Nama Penumpang:");
        namaLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #F0FFFF; -fx-font-weight: bold;");
        namaField = new TextField();
        namaField.setStyle("-fx-background-color: #ffffff; -fx-border-color: #ccc; -fx-padding: 5; -fx-font-weight: bold;");

        Label kelasLabel = new Label("Pilih Kelas:");
        kelasLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #F0FFFF; -fx-font-weight: bold;");
        kelasComboBox = new ComboBox<>();
        kelasComboBox.getItems().addAll("Ekonomi", "Bisnis");
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

        Label listLabel = new Label("Tiket yang dipesan:");
        listLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #F0FFFF; -fx-font-weight: bold;");

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

        gridPane.add(transportasiLabel, 0, 0);
        gridPane.add(busComboBox, 1, 0);
        gridPane.add(namaLabel, 0, 1);
        gridPane.add(namaField, 1, 1);
        gridPane.add(kelasLabel, 0, 2);
        gridPane.add(kelasComboBox, 1, 2);
        gridPane.add(tanggalLabel, 0, 4);
        gridPane.add(datePicker, 1, 4);
        gridPane.add(pesanButton, 1, 5);
        gridPane.add(cetakButton, 1, 6);
        gridPane.add(listLabel, 0, 7);
        gridPane.add(tiketListView, 1, 7);
        gridPane.add(totalHargaLabel, 1, 8);
        gridPane.add(backButton, 1, 9);

        getChildren().addAll(backgroundImageView, gridPane);
        setPrefSize(PREF_WIDTH, PREF_HEIGHT);
    }
    private String generateBookingID() {
        return String.format("%08d", (int) (Math.random() * 100000000));
    }

    private void initTransportasiData() {
        sistemPemesananBus.tambahBus(new Bus("BUS MALAM JAYA", "Jakarta", "Surabaya", "20:00", "06:00", 250000, 350000));
        sistemPemesananBus.tambahBus(new Bus("BUS SINAR JAYA", "Jakarta", "Bandung", "08:00", "12:00", 100000, 150000));
        sistemPemesananBus.tambahBus(new Bus("BUS MURNI JAYA", "Surabaya", "Yogyakarta", "09:00", "17:00", 150000, 250000));
        selectionPaneWidth = 512;
        selectionPaneHeight = 512;
    }

    private void pesanTiket() {
        int indeksBus = busComboBox.getSelectionModel().getSelectedIndex();
        String namaPenumpang = namaField.getText();
        String kelas = kelasComboBox.getSelectionModel().getSelectedItem();
        LocalDate tanggalKeberangkatan = datePicker.getValue();

        if (namaPenumpang.isEmpty() || tanggalKeberangkatan == null) {
            showErrorMessage("Nama penumpang dan tanggal keberangkatan harus diisi.");
            return;
        }
        String bookingID = generateBookingID();
        sistemPemesananBus.pesanTiket(indeksBus, namaPenumpang, kelas, tanggalKeberangkatan, bookingID);
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
        LocalDate selectedDate = datePicker.getValue();

        if (selectedTiket != null && selectedDate != null) {

            PrinterJob printerJob = PrinterJob.getPrinterJob();
            printerJob.setPrintable((graphics, pageFormat, pageIndex) -> {

                if (pageIndex > 0) {
                    return Printable.NO_SUCH_PAGE;
                }

                Graphics2D g2d = (Graphics2D) graphics;
                g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

                try {
                    Image backgroundImage = new Image(Objects.requireNonNull(getClass().getResource("/images/tiket_bus_background.jpg")).toExternalForm());
                    BufferedImage bufferedImage = SwingFXUtils.fromFXImage(backgroundImage, null);
                    g2d.drawImage(bufferedImage, 0, 0, (int) pageFormat.getWidth(), (int) pageFormat.getHeight(), null);

                    g2d.setFont(new Font("Arial", Font.BOLD, 16));
                    g2d.setColor(Color.BLACK);

                    TiketBus selectedTicket = sistemPemesananBus.getTiketBusList().stream()
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
                                selectedTicket.bus().namaBus(),
                                selectedDate.getDayOfWeek()+","+selectedDate.toString(),
                                "",
                                "Booking ID : %s".formatted(selectedTicket.bookingID()),

                        };

                        for (String line : headerInfo) {
                            g2d.drawString(line, leftMargin, y);
                            y += lineHeight;
                        }

                        y += lineHeight;

                        String[] ticketInfo = {
                                "Bus: " + selectedTicket.bus().namaBus(),
                                "Asal: " + selectedTicket.bus().asal(),
                                "Tujuan: " + selectedTicket.bus().tujuan(),
                                "Berangkat: " + selectedTicket.bus().waktuBerangkat(),
                                "Tiba: " + selectedTicket.bus().waktuTiba(),
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
        alert.setTitle("Error");
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

record TiketBus(Bus bus, String namaPenumpang, String tipeKelas, int harga, LocalDate tanggalKeberangkatan, String bookingID) {

    @Override
    public String toString() {
        return "Bus: " + bus.namaBus() + ", Nama: " + namaPenumpang + "| Kelas: " + tipeKelas + "| Tanggal Keberangkatan: "+tanggalKeberangkatan+
                "| Booking ID: "+bookingID+
                ", Harga: Rp" + harga;
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

    public void pesanTiket(int indeksBus, String namaPenumpang, String tipeKelas, LocalDate tanggalKeberangkatan, String bookingID) {
        if (indeksBus >= 0 && indeksBus < busList.size()) {
            Bus bus = busList.get(indeksBus);
            int harga = tipeKelas.equalsIgnoreCase("Ekonomi") ? bus.hargaEkonomi() : bus.hargaBisnis();
            TiketBus tiketBus = new TiketBus(bus, namaPenumpang, tipeKelas, harga, tanggalKeberangkatan, bookingID);
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
