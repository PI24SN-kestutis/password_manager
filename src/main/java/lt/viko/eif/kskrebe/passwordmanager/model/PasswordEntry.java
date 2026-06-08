package lt.viko.eif.kskrebe.passwordmanager.model;

/**
 * Slaptažodžio įrašo modelis.
 *
 * Ši klasė saugo vieną slaptažodžių tvarkyklės įrašą:
 * - pavadinimą;
 * - užšifruotą slaptažodį;
 * - URL adresą;
 * - papildomas pastabas.
 *
 * Slaptažodis saugomas tik užšifruotu pavidalu.
 */
public class PasswordEntry {

    /**
     * Įrašo pavadinimas.
     */
    private String title;

    /**
     * Užšifruotas slaptažodis.
     */
    private String encryptedPassword;

    /**
     * Svetainės arba sistemos adresas.
     */
    private String url;

    /**
     * Papildomos vartotojo pastabos.
     */
    private String notes;

    /**
     * Numatytasis konstruktorius.
     */
    public PasswordEntry() {
    }

    /**
     * Sukuria naują slaptažodžio įrašą.
     *
     * @param title įrašo pavadinimas
     * @param encryptedPassword užšifruotas slaptažodis
     * @param url svetainės adresas
     * @param notes papildomos pastabos
     */
    public PasswordEntry(
            String title,
            String encryptedPassword,
            String url,
            String notes) {

        this.title = title;
        this.encryptedPassword = encryptedPassword;
        this.url = url;
        this.notes = notes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}