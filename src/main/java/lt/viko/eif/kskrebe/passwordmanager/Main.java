package lt.viko.eif.kskrebe.passwordmanager;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import lt.viko.eif.kskrebe.passwordmanager.model.PasswordEntry;
import lt.viko.eif.kskrebe.passwordmanager.service.FileCryptoService;
import lt.viko.eif.kskrebe.passwordmanager.service.PasswordFileService;
import lt.viko.eif.kskrebe.passwordmanager.service.PasswordService;

import java.util.List;

/**
 * Pagrindinė JavaFX programos klasė.
 */
public class Main extends Application {

    private PasswordService passwordService;
    private PasswordFileService fileService;
    private final ObservableList<PasswordEntry> entries =
            FXCollections.observableArrayList();

    /**
     * Paleidžia programą.
     *
     * @param stage pagrindinis programos langas
     */
    @Override
    public void start(Stage stage) {

        try {
            FileCryptoService fileCryptoService = new FileCryptoService();
            fileCryptoService.decryptFile();

            passwordService = new PasswordService();
            fileService = new PasswordFileService();

            loadEntries();
        } catch (Exception e) {
            showError("Inicializavimo klaida", e.getMessage());
        }

        TableView<PasswordEntry> table = createTable();

        TextField titleField = new TextField();
        titleField.setPromptText("Pavadinimas");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Slaptažodis");

        TextField urlField = new TextField();
        urlField.setPromptText("URL arba programa");

        TextField notesField = new TextField();
        notesField.setPromptText("Pastabos");

        Button addButton = new Button("Pridėti");
        Button updateButton = new Button("Atnaujinti");
        Button deleteButton = new Button("Ištrinti");
        Button revealButton = new Button("Rodyti slaptažodį");
        Button copyButton = new Button("Kopijuoti");
        Button searchButton = new Button("Ieškoti");

        addButton.setOnAction(event -> {
            try {
                passwordService.addEntry(
                        titleField.getText(),
                        passwordField.getText(),
                        urlField.getText(),
                        notesField.getText()
                );
                loadEntries();
                clearFields(titleField, passwordField, urlField, notesField);
            } catch (Exception e) {
                showError("Pridėjimo klaida", e.getMessage());
            }
        });

        updateButton.setOnAction(event -> {
            try {
                passwordService.updateEntry(
                        titleField.getText(),
                        passwordField.getText(),
                        urlField.getText(),
                        notesField.getText()
                );
                loadEntries();
                clearFields(titleField, passwordField, urlField, notesField);
            } catch (Exception e) {
                showError("Atnaujinimo klaida", e.getMessage());
            }
        });

        deleteButton.setOnAction(event -> {
            try {
                passwordService.deleteEntry(titleField.getText());
                loadEntries();
                clearFields(titleField, passwordField, urlField, notesField);
            } catch (Exception e) {
                showError("Ištrynimo klaida", e.getMessage());
            }
        });

        searchButton.setOnAction(event -> {
            try {
                passwordService.findByTitle(titleField.getText())
                        .ifPresentOrElse(entry -> {
                            entries.setAll(entry);
                            urlField.setText(entry.getUrl());
                            notesField.setText(entry.getNotes());
                            passwordField.clear();
                        }, () -> showError("Paieška", "Įrašas nerastas."));
            } catch (Exception e) {
                showError("Paieškos klaida", e.getMessage());
            }
        });

        revealButton.setOnAction(event -> {
            PasswordEntry selected = table.getSelectionModel().getSelectedItem();

            if (selected == null) {
                showError("Peržiūra", "Pasirinkite įrašą lentelėje.");
                return;
            }

            try {
                String plainPassword =
                        passwordService.revealPassword(selected);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Slaptažodis");
                alert.setHeaderText(selected.getTitle());
                alert.setContentText(plainPassword);
                alert.showAndWait();
            } catch (Exception e) {
                showError("Dešifravimo klaida", e.getMessage());
            }
        });

        copyButton.setOnAction(event -> {
            PasswordEntry selected = table.getSelectionModel().getSelectedItem();

            if (selected == null) {
                showError("Kopijavimas", "Pasirinkite įrašą lentelėje.");
                return;
            }

            try {
                String plainPassword =
                        passwordService.revealPassword(selected);

                ClipboardContent content = new ClipboardContent();
                content.putString(plainPassword);

                Clipboard.getSystemClipboard()
                        .setContent(content);

                showInfo("Kopijavimas", "Slaptažodis nukopijuotas.");
            } catch (Exception e) {
                showError("Kopijavimo klaida", e.getMessage());
            }
        });

        table.setOnMouseClicked(event -> {
            PasswordEntry selected = table.getSelectionModel().getSelectedItem();

            if (selected != null) {
                titleField.setText(selected.getTitle());
                urlField.setText(selected.getUrl());
                notesField.setText(selected.getNotes());
                passwordField.clear();
            }
        });

        Button resetButton = new Button("Rodyti visus");
        resetButton.setOnAction(event -> {
            try {
                loadEntries();
            } catch (Exception e) {
                showError("Duomenų įkėlimo klaida", e.getMessage());
            }
        });

        HBox buttons = new HBox(
                10,
                addButton,
                updateButton,
                deleteButton,
                searchButton,
                resetButton,
                revealButton,
                copyButton
        );

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);

        form.add(new Label("Pavadinimas:"), 0, 0);
        form.add(titleField, 1, 0);

        form.add(new Label("Slaptažodis:"), 0, 1);
        form.add(passwordField, 1, 1);

        form.add(new Label("URL / programa:"), 0, 2);
        form.add(urlField, 1, 2);

        form.add(new Label("Pastabos:"), 0, 3);
        form.add(notesField, 1, 3);

        VBox root = new VBox(15, form, buttons, table);
        root.setPadding(new Insets(15));

        stage.setOnCloseRequest(event -> {
            try {
                FileCryptoService fileCryptoService =
                        new FileCryptoService();

                fileCryptoService.encryptFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        stage.setTitle("Slaptažodžių tvarkyklė");
        stage.setScene(new Scene(root, 1000, 600));
        stage.show();
    }

    /**
     * Sukuria slaptažodžių lentelę.
     *
     * @return TableView komponentas
     */
    private TableView<PasswordEntry> createTable() {

        TableView<PasswordEntry> table = new TableView<>();
        table.setItems(entries);

        TableColumn<PasswordEntry, String> titleColumn =
                new TableColumn<>("Pavadinimas");
        titleColumn.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        data.getValue().getTitle()));

        TableColumn<PasswordEntry, String> passwordColumn =
                new TableColumn<>("Slaptažodis");
        passwordColumn.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty("********"));

        TableColumn<PasswordEntry, String> urlColumn =
                new TableColumn<>("URL / programa");
        urlColumn.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        data.getValue().getUrl()));

        TableColumn<PasswordEntry, String> notesColumn =
                new TableColumn<>("Pastabos");
        notesColumn.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        data.getValue().getNotes()));

        table.getColumns().add(titleColumn);
        table.getColumns().add(passwordColumn);
        table.getColumns().add(urlColumn);
        table.getColumns().add(notesColumn);

        table.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        return table;
    }

    /**
     * Įkelia slaptažodžių įrašus iš CSV failo į lentelę.
     *
     * @throws Exception jei nepavyksta nuskaityti failo
     */
    private void loadEntries() throws Exception {

        List<PasswordEntry> loadedEntries =
                fileService.readAll();

        entries.setAll(loadedEntries);
    }

    /**
     * Išvalo įvedimo laukus.
     *
     * @param fields teksto laukai
     */
    private void clearFields(TextInputControl... fields) {

        for (TextInputControl field : fields) {
            field.clear();
        }
    }

    /**
     * Parodo klaidos pranešimą.
     *
     * @param title lango pavadinimas
     * @param message klaidos tekstas
     */
    private void showError(String title, String message) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Parodo informacinį pranešimą.
     *
     * @param title lango pavadinimas
     * @param message pranešimo tekstas
     */
    private void showInfo(String title, String message) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Programos paleidimo metodas.
     *
     * @param args komandų eilutės argumentai
     */
    public static void main(String[] args) {
        launch(args);
    }
}