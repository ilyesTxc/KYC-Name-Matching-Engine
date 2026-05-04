package kyc.comparateurs;
import kyc.model.Nom;
import kyc.pretraiteurs.PreTraiteurNom;
import java.util.List;

public class Exact extends ComparateurNom implements ComparateurChaine {
    public PreTraiteurNom preTraiteur;
    public Exact (PreTraiteurNom preTraiteur){
        this.preTraiteur = preTraiteur;
    }

    public double comparer(Nom n1, Nom n2){
        List<String> tokens1 = n1.getNomPretraite();
        if (tokens1 == null ){
           tokens1 = preTraiteur.preTraiter(List.of(n1.getNomOriginal().split("\\s+")));
       }

        List<String> tokens2 = n2.getNomPretraite();
        if (tokens2 == null ){
            tokens2 = preTraiteur.preTraiter(List.of(n2.getNomOriginal().split("\\s+")));
        }

        String s1 = String.join(" ",tokens1);
        String s2 = String.join(" ",tokens2);

        return comparerChaine(s1,s2);
    }
    public double comparerChaine(String s1, String s2){
        return s1.equalsIgnoreCase(s2) ? 1.0 : 0.0;
    }
}