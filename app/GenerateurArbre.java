package kyc.app;

import kyc.model.Nom;

import java.util.*;

public class GenerateurArbre implements GenerateurCandidat {
    private static final int  toleranceDefault = 2;
    private final Nom nomClient;
    private final IndexPrefixArbre index;
    private final int tolerance;

    GenerateurArbre(Nom nomClient, IndexPrefixArbre index,int tolerance){
        this.nomClient =nomClient;
        this.index= index;
        if(tolerance < 0){
            this.tolerance = 1;
        }else {
            this.tolerance = tolerance;
        }
    }

    // use default tolerance of 2
    GenerateurArbre(Nom nomClient, IndexPrefixArbre index){
        this.nomClient =nomClient;
        this.index= index;
        this.tolerance = toleranceDefault;
    }

    public List<Nom> genererCandidats(Nom nomClient, List<Nom> listSanctionnes){
        List<String> tokens = nomClient.getNomPretraite();

        if(tokens == null || tokens.isEmpty()){
            return Collections.emptyList();
        }

        int longeurClient = index.longeur(tokens);

        Set<Nom> candidats = index.RechercherParLongeur(longeurClient,tolerance);

        return new ArrayList<>(candidats);
    }


}
