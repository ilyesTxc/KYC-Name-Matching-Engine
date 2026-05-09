package kyc.model;
import java.util.List;

public class Nom {
    private int id;
    private String nomOriginal;
    private List<String> nomPretraite;
    private String fichierSource;

    public Nom(int id, String nomOriginal) {
        this.id = id;
        this.nomOriginal = nomOriginal;
    }

    public int getId() { return id; }
    public String getNomOriginal() { return nomOriginal; }
    public List<String> getNomPretraite() { return nomPretraite; }
    public void setNomPretraite(List<String> nomPretraite) { this.nomPretraite = nomPretraite; }
    public String getFichierSource() {return fichierSource; }
    public void setFichierSource(String f) { this.fichierSource = f; }
    public String toString() {
        return "Nom{id=" + id +", nomOriginal='" + nomOriginal+"'}";
    }
}
