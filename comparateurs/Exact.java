package kyc.comparateurs;
import kyc.model.Nom;
public class Exact extends ComparateurNom {
    public double comparer(Nom n1, Nom n2){
        if (n1 == null || n2 == null) return 0.0;
        if (n1.getNomPretraite().equalsIgnoreCase(n2.getNomPretraite())) {
            return 1.0;
        } else {
            return 0.0;
        }
    }
}