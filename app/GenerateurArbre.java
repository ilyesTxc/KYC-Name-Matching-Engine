package kyc.app;

import kyc.model.Nom;

import java.util.*;

public class GenerateurArbre implements GenerateurCandidat {
    private static final int  toleranceDefault = 2;
    private final IndexPrefixArbre index;
    private final int tolerance;

    GenerateurArbre(IndexPrefixArbre index,int tolerance){
        this.index= index;
        if(tolerance < 0){
            this.tolerance = 1;
        }else {
            this.tolerance = tolerance;
        }
    }

    // use default tolerance of 2
    GenerateurArbre(IndexPrefixArbre index){
        this.index= index;
        this.tolerance = toleranceDefault;
    }

    public Map<Nom, List<Nom>> genererCandidats(List<Nom> clients, List<Nom> listSanctionnes) {
        Map<Nom, List<Nom>> result = new LinkedHashMap<>();
        for (Nom client : clients) {
            List<String> tokens = client.getNomPretraite();
            if (tokens == null || tokens.isEmpty()) {
                result.put(client, Collections.emptyList());
                continue;
            }
            int longueur = index.longeur(tokens);
            result.put(client, new ArrayList<>(index.RechercherParLongeur(longueur, tolerance)));
        }
        return result;
    }


}
