package lt.viko.eif.kskrebe.passwordmanager.service;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;

/**
 * Atsakinga už tekstinių duomenų šifravimą ir dešifravimą
 * naudojant AES algoritmą.
 *
 * Užšifruoti duomenys papildomai koduojami Base64 formatu,
 * kad juos būtų galima saugoti tekstiniuose failuose.
 */
public class CryptoService {

    /**
     * Naudojamas šifravimo algoritmas.
     */
    private static final String ALGORITHM = "AES";

    /**
     * Simetrinis AES raktas.
     */
    private final SecretKey secretKey;

    /**
     * Sukuria naują CryptoService objektą.
     *
     * Konstruktoriaus metu sugeneruojamas naujas
     * 256 bitų AES raktas.
     *
     * @throws Exception jei nepavyksta inicializuoti
     *                   AES rakto generatoriaus
     */
    public CryptoService() throws Exception {

        KeyGenerator generator =
                KeyGenerator.getInstance(ALGORITHM);

        generator.init(256);

        this.secretKey = generator.generateKey();
    }

    /**
     * Užšifruoja perduotą tekstą naudojant AES algoritmą.
     *
     * Gauti baitai konvertuojami į Base64 formatą,
     * kad rezultatą būtų galima saugoti tekstiniame faile.
     *
     * @param text tekstas, kurį reikia užšifruoti
     * @return Base64 formatu užšifruotas tekstas
     * @throws Exception jei šifravimo metu įvyksta klaida
     */
    public String encrypt(String text) throws Exception {

        Cipher cipher =
                Cipher.getInstance(ALGORITHM);

        cipher.init(
                Cipher.ENCRYPT_MODE,
                secretKey);

        byte[] encrypted =
                cipher.doFinal(text.getBytes());

        return Base64.getEncoder()
                .encodeToString(encrypted);
    }

    /**
     * Iššifruoja anksčiau AES algoritmu užšifruotą tekstą.
     *
     * Pirmiausia Base64 tekstas paverčiamas baitais,
     * po to atliekamas AES dešifravimas.
     *
     * @param encryptedText Base64 formatu užšifruotas tekstas
     * @return pradinis tekstas
     * @throws Exception jei dešifravimo metu įvyksta klaida
     */
    public String decrypt(String encryptedText)
            throws Exception {

        Cipher cipher =
                Cipher.getInstance(ALGORITHM);

        cipher.init(
                Cipher.DECRYPT_MODE,
                secretKey);

        byte[] decoded =
                Base64.getDecoder()
                        .decode(encryptedText);

        return new String(
                cipher.doFinal(decoded));
    }
}