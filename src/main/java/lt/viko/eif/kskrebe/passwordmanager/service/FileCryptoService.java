package lt.viko.eif.kskrebe.passwordmanager.service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Atsakinga už viso slaptažodžių failo šifravimą ir dešifravimą.
 */
public class FileCryptoService {

    /**
     * Neužšifruotas CSV failas.
     */
    private static final Path CSV_FILE =
            Path.of("data", "passwords.csv");

    /**
     * Užšifruotas failas.
     */
    private static final Path ENCRYPTED_FILE =
            Path.of("data", "passwords.enc");

    /**
     * AES raktas.
     */
    private final SecretKey secretKey;

    /**
     * Sukuria FileCryptoService objektą.
     *
     * @throws Exception jei nepavyksta įkelti AES rakto
     */
    public FileCryptoService() throws Exception {

        KeyService keyService = new KeyService();
        this.secretKey = keyService.loadOrCreateKey();
    }

    /**
     * Užšifruoja CSV failą ir pašalina originalų failą.
     *
     * @throws Exception jei nepavyksta atlikti šifravimo
     */
    public void encryptFile() throws Exception {

        if (!Files.exists(CSV_FILE)) {
            return;
        }

        byte[] fileBytes =
                Files.readAllBytes(CSV_FILE);

        Cipher cipher =
                Cipher.getInstance("AES");

        cipher.init(
                Cipher.ENCRYPT_MODE,
                secretKey);

        byte[] encrypted =
                cipher.doFinal(fileBytes);

        Files.write(
                ENCRYPTED_FILE,
                encrypted
        );

        Files.delete(CSV_FILE);
    }

    /**
     * Iššifruoja failą paleidžiant programą.
     *
     * @throws Exception jei nepavyksta atlikti dešifravimo
     */
    public void decryptFile() throws Exception {

        if (!Files.exists(ENCRYPTED_FILE)) {
            return;
        }

        byte[] encryptedBytes =
                Files.readAllBytes(ENCRYPTED_FILE);

        Cipher cipher =
                Cipher.getInstance("AES");

        cipher.init(
                Cipher.DECRYPT_MODE,
                secretKey);

        byte[] decrypted =
                cipher.doFinal(encryptedBytes);

        Files.write(
                CSV_FILE,
                decrypted
        );

        Files.delete(ENCRYPTED_FILE);
    }
}