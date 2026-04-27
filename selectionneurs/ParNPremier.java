package kyc.selectionneurs;

import kyc.selectionneurs.SelectionMatching;
import kyc.model.Nom;
import kyc.model.CoupleValeur;
import java.util.List;

public class ParNPremier implements SelectionMatching {
    private int n1;

    public ParNPremier(int n1) {
        this.n1 = n1;
    }

    public List<Nom> selectionner(List<CoupleValeur> couples) {
        // TODO : retourner les N premiers noms par score décroissant
        return null;
    }
}
