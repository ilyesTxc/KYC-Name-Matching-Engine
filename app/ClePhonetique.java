package kyc.app;

import kyc.app.GenerateurCandidat;

import kyc.model.Nom;

import java.util.*;


public class ClePhonetique implements GenerateurCandidat{
    private final IndexPhonetique index;

    ClePhonetique(IndexPhonetique index){
        this.index =index;
    }

    public Map<Nom, List<Nom>> genererCandidats(List<Nom> clients, List<Nom> listSanctionnes) {
        Map<Nom, List<Nom>> result = new LinkedHashMap<>();
        for (Nom client : clients) {
            Set<Nom> candidats = new LinkedHashSet<>();
            for (String code : IndexPhonetique.encoderTokens(client)) {
                candidats.addAll(index.getBucket(code));
            }
            result.put(client, new ArrayList<>(candidats));
        }
        return result;
    }
}