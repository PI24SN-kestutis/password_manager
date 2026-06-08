package lt.viko.eif.kskrebe.passwordmanager.service;

import lt.viko.eif.kskrebe.passwordmanager.model.PasswordEntry;

import java.util.List;
import java.util.Optional;

/**
 * Atsakinga už slaptažodžių įrašų valdymą.
 */
public class PasswordService {

    private final PasswordFileService fileService;
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
     * @throws Exception jei įrašas jau egzistuoja arba nepavyksta išsaugoti duomenų
     */
    public void addEntry(
            String title,
            String password,
            String url,
            String notes) throws Exception {

        List<PasswordEntry> entries = fileService.readAll();

        if (findByTitle(title).isPresent()) {
            throw new IllegalArgumentException("Įrašas tokiu pavadinimu jau egzistuoja.");
        }

        String encryptedPassword = cryptoService.encrypt(password);

        entries.add(new PasswordEntry(
                title,
                encryptedPassword,
                url,
                notes
        ));

        fileService.saveAll(entries);
    }

    /**
     * Ieško slaptažodžio įrašo pagal pavadinimą.
     *
     * Paieška atliekama ignoruojant didžiąsias ir mažąsias raides.
     *
     * @param title ieškomo įrašo pavadinimas
     * @return rastas slaptažodžio įrašas arba tuščias Optional
     * @throws Exception jei nepavyksta nuskaityti duomenų failo
     */
    public Optional<PasswordEntry> findByTitle(String title) throws Exception {

        return fileService.readAll()
                .stream()
                .filter(entry -> entry.getTitle().equalsIgnoreCase(title))
                .findFirst();
    }

    /**
     * Iššifruoja pasirinkto įrašo slaptažodį.
     *
     * Šis metodas naudojamas tik tada, kai vartotojas aiškiai pareikalauja
     * parodyti slaptažodį.
     *
     * @param entry slaptažodžio įrašas
     * @return iššifruotas slaptažodis
     * @throws Exception jei nepavyksta iššifruoti slaptažodžio
     */
    public String revealPassword(PasswordEntry entry) throws Exception {
        return cryptoService.decrypt(entry.getEncryptedPassword());
    }

    /**
     * Atnaujina esamą slaptažodžio įrašą pagal pavadinimą.
     *
     * Naujas slaptažodis prieš išsaugojimą iš naujo užšifruojamas
     * AES algoritmu.
     *
     * @param title esamo įrašo pavadinimas
     * @param newPassword naujas slaptažodis
     * @param newUrl naujas URL adresas
     * @param newNotes naujos pastabos
     * @throws Exception jei įrašas nerandamas arba nepavyksta išsaugoti duomenų
     */
    public void updateEntry(
            String title,
            String newPassword,
            String newUrl,
            String newNotes) throws Exception {

        List<PasswordEntry> entries = fileService.readAll();

        boolean updated = false;

        for (PasswordEntry entry : entries) {
            if (entry.getTitle().equalsIgnoreCase(title)) {

                String encryptedPassword =
                        cryptoService.encrypt(newPassword);

                entry.setEncryptedPassword(encryptedPassword);
                entry.setUrl(newUrl);
                entry.setNotes(newNotes);

                updated = true;
                break;
            }
        }

        if (!updated) {
            throw new IllegalArgumentException("Įrašas nerastas.");
        }

        fileService.saveAll(entries);
    }

    /**
     * Ištrina slaptažodžio įrašą pagal pavadinimą.
     *
     * Ištrintas įrašas pašalinamas iš CSV duomenų failo.
     *
     * @param title trinamo įrašo pavadinimas
     * @throws Exception jei įrašas nerandamas arba nepavyksta išsaugoti duomenų
     */
    public void deleteEntry(String title) throws Exception {

        List<PasswordEntry> entries = fileService.readAll();

        boolean removed =
                entries.removeIf(entry ->
                        entry.getTitle().equalsIgnoreCase(title));

        if (!removed) {
            throw new IllegalArgumentException("Įrašas nerastas.");
        }

        fileService.saveAll(entries);
    }
}