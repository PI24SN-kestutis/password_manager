package lt.viko.eif.kskrebe.passwordmanager.service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

/**
 * Atsakinga už AES rakto sukūrimą, išsaugojimą ir nuskaitymą iš failo.
 *
 * Raktas saugomas Base64 formatu tekstiniame faile, kad jį būtų galima
 * pakartotinai naudoti paleidus programą iš naujo.
 */
public class KeyService {

    /**
     * AES rakto failo kelias.
     */
    private static final Path KEY_FILE = Path.of("data", "aes.key");

    /**
     * AES rakto algoritmas.
     */
    private static final String ALGORITHM = "AES";

    /**
     * Įkelia esamą AES raktą arba sukuria naują, jeigu rakto failo dar nėra.
     *
     * @return AES slaptasis raktas
     * @throws Exception jei nepavyksta sukurti katalogo, nuskaityti arba išsaugoti rakto
     */
    public SecretKey loadOrCreateKey() throws Exception {

        Files.createDirectories(KEY_FILE.getParent());

        if (Files.exists(KEY_FILE)) {
            return loadKey();
        }

        return createAndSaveKey();
    }

    /**
     * Nuskaito AES raktą iš failo.
     *
     * @return AES slaptasis raktas
     * @throws Exception jei nepavyksta nuskaityti arba dekoduoti rakto
     */
    private SecretKey loadKey() throws Exception {

        String base64Key = Files.readString(KEY_FILE);

        byte[] decodedKey = Base64.getDecoder()
                .decode(base64Key);

        return new SecretKeySpec(decodedKey, ALGORITHM);
    }

    /**
     * Sukuria naują AES raktą ir išsaugo jį faile.
     *
     * @return naujai sukurtas AES slaptasis raktas
     * @throws Exception jei nepavyksta sugeneruoti arba išsaugoti rakto
     */
    private SecretKey createAndSaveKey() throws Exception {

        javax.crypto.KeyGenerator generator =
                javax.crypto.KeyGenerator.getInstance(ALGORITHM);

        generator.init(256);

        SecretKey key = generator.generateKey();

        String base64Key = Base64.getEncoder()
                .encodeToString(key.getEncoded());

        Files.writeString(KEY_FILE, base64Key);

        return key;
    }
}