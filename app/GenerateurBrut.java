package kyc.app;

import kyc.model.Nom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenerateurBrut implements GenerateurCandidat {
    public Map<Nom, List<Nom>> genererCandidats(List<Nom> listeGauche, List<Nom> listeDroite){
        Map<Nom, List<Nom>> resultat = new HashMap<>();
        for (Nom nom : listeGauche){
            resultat.put(nom,listeDroite);
        }
        return resultat;
    }
}
