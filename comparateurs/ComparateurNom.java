package kyc.comparateurs;
import kyc.model.Nom;
public abstract class ComparateurNom {
    public abstract double comparer(Nom n1, Nom n2);
    public boolean estSimilaire(Nom n1, Nom n2, double seuil) {
        return comparer(n1, n2) >= seuil;
    }
}
