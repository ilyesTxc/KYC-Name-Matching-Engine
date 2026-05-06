package kyc.model;

public class CoupleValeur {
    private Nom nom;
    private double score;

    public CoupleValeur(Nom nom, double score) {
        this.nom = nom;
        this.score = score;
    }

    public Nom getNom() 
    { 
        return nom;
    }
    
    public double getScore() 
    { 
        return score; 
    }
}
