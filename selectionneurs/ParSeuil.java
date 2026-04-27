package kyc.selectionneurs;

import kyc.selectionneurs.SelectionMatching;
import kyc.model.Nom;
import kyc.model.CoupleValeur;
import java.util.List;

public class ParSeuil implements SelectionMatching {
    private double seuil;

    public ParSeuil(double seuil) {
        this.seuil = seuil;
    }

    @Override
    public List<Nom> selectionner(List<CoupleValeur> couples) {
        // TODO : retourner les noms dont le score >= seuil
        return null;
    }
}
