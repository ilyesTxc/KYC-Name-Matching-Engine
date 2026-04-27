package kyc.model;

public class Nom {
    private int id;
    private String nomOriginal;
    private String nomPretraite;

    public Nom(int id, String nomOriginal) {
        this.id = id;
        this.nomOriginal = nomOriginal;
        this.nomPretraite = nomOriginal;
    }

    public int getId() { return id; }
    public String getNomOriginal() { return nomOriginal; }
    public String getNomPretraite() { return nomPretraite; }
    public void setNomPretraite(String nomPretraite) { this.nomPretraite = nomPretraite; }
}
