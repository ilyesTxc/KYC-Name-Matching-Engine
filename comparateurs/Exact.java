package kyc.comparateurs;
import kyc.model.Nom;
import java.util.List;

public class Exact extends ComparateurNom implements ComparateurChaine {
    public double comparer(Nom n1, Nom n2){
        List<String> tokens1;
        if (n1.getNomPretraite() != null){
            tokens1 = getNomPretraite();
        } else {
            tokens1 = tokeniser(n1.getNomOriginal());
        }

        List<String> tokens2;
        if (n2.getNomPretraite() != null){
            tokens2= getNomPretraite();
        } else {
            tokens2 = tokeniser(n2.getNomOriginal());
        }

        return comparerChaine(joinTokens(tokens1), joinTokens(tokens2));
    }
}