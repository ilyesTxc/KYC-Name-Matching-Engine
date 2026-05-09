package kyc.app;

import kyc.model.Nom;

import java.util.*;

public class GenerateurPrefixHash implements GenerateurCandidat{
    private final IndexPrefixHash indexPrefixHash;

    GenerateurPrefixHash(IndexPrefixHash index){
        this.indexPrefixHash = index;
    }

    public Map<Nom, List<Nom>> genererCandidats(List<Nom> listeGauche, List<Nom> listeDroite){
        Map<Nom, List<Nom>> resultats = new LinkedHashMap<>();

        if(listeGauche == null || listeGauche.isEmpty()){
            return resultats;
        }

        for(Nom c : listeGauche){
            Set<Nom> candidats = indexPrefixHash.rechercher(c);
            resultats.put(c, new ArrayList<>(candidats));
        }

        return resultats;

    }





}
