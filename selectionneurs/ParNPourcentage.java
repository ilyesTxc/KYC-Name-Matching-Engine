package kyc.selectionneurs;

import kyc.model.Nom;
import kyc.model.CoupleValeur;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

public class ParNPourcentage implements SelectionMatching {
    private double n;
    public ParNPourcentage(double n) {
        this.n = n;
    }
    public List<Nom> selectionner(List<CoupleValeur> couples) {
        List<Nom> result = new ArrayList<>();
        int limit = (int) Math.round(couples.size() * n / 100.0);
        couples.sort(Comparator.comparingDouble((CoupleValeur couple)  -> couple.getScore()).reversed());
        for (int i = 0; i < limit; i++) {
            result.add(couples.get(i).getNom());
        }
        return result;
    }

}



