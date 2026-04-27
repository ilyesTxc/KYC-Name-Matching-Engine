package kyc.model;

public class Resultat {
    private Nom nomClient;
    private Nom nomSanction;
    private double score;
    private String fichierSource;

    public Resultat(Nom nomClient, Nom nomSanction, double score, String fichierSource) {
        this.nomClient = nomClient;
        this.nomSanction = nomSanction;
        this.score = score;
        this.fichierSource = fichierSource;
    }

    public Nom getNomClient() { return nomClient; }
    public Nom getNomSanction() { return nomSanction; }
    public double getScore() { return score; }
    public String getFichierSource() { return fichierSource; }
}
