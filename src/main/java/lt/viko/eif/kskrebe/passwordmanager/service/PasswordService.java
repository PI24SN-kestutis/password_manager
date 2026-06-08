package lt.viko.eif.kskrebe.passwordmanager.service;

import lt.viko.eif.kskrebe.passwordmanager.model.PasswordEntry;

import java.util.List;

/**
 * Atsakinga už slaptažodžių įrašų valdymą.
 */
public class PasswordService {

    /**
     * Slaptažodžių failo servisas.
     */
    private final PasswordFileService fileService;

    /**
     * Šifravimo servisas.
     */
    private final CryptoService cryptoService;

    /**
     * Sukuria PasswordService objektą.
     *
     * @throws Exception jei nepavyksta inicializuoti servisų
     */
    public PasswordService() throws Exception {
        this.fileService = new PasswordFileService();
        this.cryptoService = new CryptoService();
    }

    /**
     * Prideda naują slaptažodžio įrašą.
     *
     * Prieš išsaugojant slaptažodis užšifruojamas AES algoritmu.
     *
     * @param title pavadinimas
     * @param password slaptažodis
     * @param url URL adresas
     * @param notes pastabos
     * @throws Exception jei nepavyksta išsaugoti duomenų
     */
    public void addEntry(
            String title,
            String password,
            String url,
            String notes) throws Exception {

        List<PasswordEntry> entries =
                fileService.readAll();

        String encryptedPassword =
                cryptoService.encrypt(password);

        PasswordEntry entry =
                new PasswordEntry(
                        title,
                        encryptedPassword,
                        url,
                        notes
                );

        entries.add(entry);

        fileService.saveAll(entries);
    }
}