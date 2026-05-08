package kyc.app;

import kyc.model.Nom;

import java.util.*;

public class GenerateurPrefixHash implements GenerateurCandidat{
    private final IndexPrefixHash indexPrefixHash;

    GenerateurPrefixHash(IndexPrefixHash index){
        this.indexPrefixHash = index;
    }

    public Map<Nom, List<Nom>> genererCandidats(List<Nom> Clients, List<Nom> listeSanctions){
        Map<Nom, List<Nom>> resultats = new LinkedHashMap<>();

        if(Clients == null || Clients.isEmpty()){
            return resultats;
        }

        for(Nom c : Clients){
            Set<Nom> candidats = indexPrefixHash.rechercher(c);
            resultats.put(c, new ArrayList<>(candidats));
        }

        return resultats;

    }





}
