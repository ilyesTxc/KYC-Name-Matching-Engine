package kyc.app;

import kyc.app.GenerateurCandidat;

import kyc.model.Nom;

import java.util.*;
import kyc.app.IndexPhonetique;

public class ClePhonetique implements GenerateurCandidat{

    private final List<String> clientCodes;
    private final IndexPhonetique index;

    ClePhonetique(Nom nomClient, IndexPhonetique index){
        this.clientCodes =IndexPhonetique.encoderTokens(nomClient);
        this.index =index;
    }

    public List<Nom> genererCandidats(Nom nomClient, List<Nom> listSanctionnes){
        Set<Nom> candidats =new LinkedHashSet<>();

        for (String code : clientCodes) {
            candidats.addAll(index.getBucket(code));
        }

        return new ArrayList<>(candidats);
    }

    public List<String> getClientCodes() {
        return clientCodes;
    }
}
