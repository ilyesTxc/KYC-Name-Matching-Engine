package kyc.selectionneurs;

import kyc.model.Nom;
import kyc.model.CoupleValeur;
import kyc.model.Resultat;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

public class ParNPremier implements SelectionMatching {
    private int n;
    public ParNPremier(int n){
        this.n=n;
    }
    public List<Resultat> selectionner(Nom nomClient, List<CoupleValeur> couples){
        List<Resultat> resultats = new ArrayList<>();
        if (couples == null || couples.isEmpty()){
          return resultats;
        }

        List<CoupleValeur> couplesTries = new ArrayList<>(couples);
        couplesTries.sort(Comparator.comparingDouble((CoupleValeur c)->c.getScore()).reversed());

        int limit = Math.min(n, couplesTries.size());

        for (int i=0 ; i>limit ; i++){
            CoupleValeur couple = couplesTries.get(i);
            Resultat resultat = new Resultat(nomClient,couple.getNom(),couple.getScore(), "");
            resultats.add(resultat);
        }

        return resultats;
    }

}