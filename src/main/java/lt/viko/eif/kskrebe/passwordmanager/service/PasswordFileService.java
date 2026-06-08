package lt.viko.eif.kskrebe.passwordmanager.service;

import lt.viko.eif.kskrebe.passwordmanager.model.PasswordEntry;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Atsakinga už slaptažodžių įrašų saugojimą ir nuskaitymą iš CSV failo.
 *
 * CSV faile saugomi šie duomenys:
 * pavadinimas, užšifruotas slaptažodis, URL adresas ir pastabos.
 */
public class PasswordFileService {

    /**
     * Slaptažodžių CSV failo kelias.
     */
    private static final Path PASSWORD_FILE = Path.of("data", "passwords.csv");

    /**
     * CSV stulpelių antraštė.
     */
    private static final String HEADER = "title,encryptedPassword,url,notes";

    /**
     * Sukuria duomenų katalogą ir slaptažodžių CSV failą,
     * jeigu jie dar neegzistuoja.
     *
     * @throws Exception jei nepavyksta sukurti katalogo arba failo
     */
    public void createFileIfNotExists() throws Exception {

        Files.createDirectories(PASSWORD_FILE.getParent());

        if (!Files.exists(PASSWORD_FILE)) {
            Files.writeString(PASSWORD_FILE, HEADER + System.lineSeparator());
        }
    }

    /**
     * Nuskaito visus slaptažodžių įrašus iš CSV failo.
     *
     * @return slaptažodžių įrašų sąrašas
     * @throws Exception jei nepavyksta nuskaityti failo
     */
    public List<PasswordEntry> readAll() throws Exception {

        createFileIfNotExists();

        List<String> lines = Files.readAllLines(PASSWORD_FILE);
        List<PasswordEntry> entries = new ArrayList<>();

        for (int i = 1; i < lines.size(); i++) {

            String line = lines.get(i);

            if (line.isBlank()) {
                continue;
            }

            String[] parts = line.split(",", -1);

            if (parts.length == 4) {
                entries.add(new PasswordEntry(
                        parts[0],
                        parts[1],
                        parts[2],
                        parts[3]
                ));
            }
        }

        return entries;
    }

    /**
     * Išsaugo visus slaptažodžių įrašus į CSV failą.
     *
     * Ankstesnis failo turinys yra perrašomas naujais duomenimis.
     *
     * @param entries slaptažodžių įrašų sąrašas
     * @throws Exception jei nepavyksta įrašyti duomenų į failą
     */
    public void saveAll(List<PasswordEntry> entries) throws Exception {

        createFileIfNotExists();

        List<String> lines = new ArrayList<>();
        lines.add(HEADER);

        for (PasswordEntry entry : entries) {
            lines.add(toCsvLine(entry));
        }

        Files.write(PASSWORD_FILE, lines);
    }

    /**
     * Konvertuoja slaptažodžio įrašą į vieną CSV eilutę.
     *
     * @param entry slaptažodžio įrašas
     * @return CSV formato eilutė
     */
    private String toCsvLine(PasswordEntry entry) {
        return entry.getTitle() + ","
                + entry.getEncryptedPassword() + ","
                + entry.getUrl() + ","
                + entry.getNotes();
    }
}