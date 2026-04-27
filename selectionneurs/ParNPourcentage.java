package kyc.selectionneurs;

import kyc.selectionneurs.SelectionMatching;
import kyc.model.Nom;
import kyc.model.CoupleValeur;
import java.util.List;

public class ParNPourcentage implements SelectionMatching {
    private double n2;

    public ParNPourcentage(double n2) {
        this.n2 = n2;
    }

    @Override
    public List<Nom> selectionner(List<CoupleValeur> couples) {
        // TODO : retourner les N% meilleurs noms par score
        return null;
    }
}
