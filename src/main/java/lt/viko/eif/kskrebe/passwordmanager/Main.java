package lt.viko.eif.kskrebe.passwordmanager;

import javafx.application.Application;
import javafx.stage.Stage;
import lt.viko.eif.kskrebe.passwordmanager.service.PasswordFileService;

/**
 * Pagrindinė JavaFX programos paleidimo klasė.
 */
public class Main extends Application {

    /**
     * Paleidžia JavaFX langą ir inicializuoja slaptažodžių duomenų failą.
     *
     * @param stage pagrindinis programos langas
     */
    @Override
    public void start(Stage stage) {

        try {
            PasswordFileService fileService = new PasswordFileService();
            fileService.createFileIfNotExists();

            System.out.println("Slaptažodžių CSV failas paruoštas.");
        } catch (Exception e) {
            e.printStackTrace();
        }

        stage.setTitle("Slaptažodžių tvarkyklė");
        stage.setWidth(900);
        stage.setHeight(600);
        stage.show();
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