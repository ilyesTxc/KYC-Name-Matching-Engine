package kyc.app;

import kyc.app.GenerateurCandidat;

import kyc.model.Nom;

import java.util.*;
import kyc.app.IndexPhonetique;


public class ClePhonetique implements GenerateurCandidat{
    private final IndexPhonetique index;

    ClePhonetique(IndexPhonetique index){
        this.index =index;
    }

    public Map<Nom, List<Nom>> genererCandidats(List<Nom> ListeGauche, List<Nom> listeDroite) {
        Map<Nom, List<Nom>> result = new LinkedHashMap<>();
        for (Nom client : ListeGauche) {
            Set<Nom> candidats = new LinkedHashSet<>();
            for (String code : IndexPhonetique.encoderTokens(client)) {
                candidats.addAll(index.getBucket(code));
            }
            result.put(client, new ArrayList<>(candidats));
        }
        return result;
    }
}
