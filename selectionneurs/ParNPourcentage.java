package kyc.selectionneurs;

import kyc.model.Nom;
import kyc.model.CoupleValeur;
import kyc.model.Resultat;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

public class ParNPourcentage implements SelectionMatching {
    private double n;
    public ParNPourcentage(double n) {
        this.n = n;
    }
    public List<Resultat> selectionner(Nom nomClient, List<CoupleValeur> couples) {
        List<Resultat> result = new ArrayList<>();
        if (couples == null) return result;
        int limit = (int) Math.round(couples.size() * n / 100.0);
        couples.sort(Comparator.comparingDouble((CoupleValeur couple)  -> couple.getScore()).reversed());
        for (int i = 0; i < limit; i++) {
            CoupleValeur couple = couples.get(i);
            result.add(new Resultat(nomClient, couple.getNom(), couple.getScore(), ""));
        }
        return result;
    }

}



