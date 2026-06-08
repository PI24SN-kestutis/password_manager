package lt.viko.eif.kskrebe.passwordmanager;

import javafx.application.Application;
import javafx.stage.Stage;
import lt.viko.eif.kskrebe.passwordmanager.service.PasswordFileService;
import lt.viko.eif.kskrebe.passwordmanager.service.PasswordService;

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

            PasswordService passwordService = new PasswordService();


            passwordService.findByTitle("github")
                    .ifPresent(entry -> {
                        System.out.println("Rastas įrašas:");
                        System.out.println("Pavadinimas: " + entry.getTitle());
                        System.out.println("URL: " + entry.getUrl());
                        System.out.println("Pastabos: " + entry.getNotes());
                        System.out.println("Slaptažodis: ********");
                    });



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