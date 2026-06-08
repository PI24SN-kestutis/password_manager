package lt.viko.eif.kskrebe.passwordmanager;

import javafx.application.Application;
import javafx.stage.Stage;
import lt.viko.eif.kskrebe.passwordmanager.service.CryptoService;

public class Main extends Application {

    @Override
    public void start(Stage stage) {



        try {

            CryptoService crypto =
                    new CryptoService();

            String encrypted =
                    crypto.encrypt("Labas123");

            String decrypted =
                    crypto.decrypt(encrypted);

            System.out.println("Encrypted:");
            System.out.println(encrypted);

            System.out.println();

            System.out.println("Decrypted:");
            System.out.println(decrypted);

        } catch (Exception e) {
            e.printStackTrace();
        }

        stage.setTitle("Slaptažodžių tvarkyklė");
        stage.setWidth(900);
        stage.setHeight(600);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}