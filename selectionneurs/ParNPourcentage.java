package kyc.selectionneurs;

import kyc.selectionneurs.SelectionMatching;
import kyc.model.Nom;
import kyc.model.CoupleValeur;
import kyc.model.Resultat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class ParNPourcentage implements SelectionMatching{
    private final double pourcentage;

    public ParNPourcentage(double pourcentage) {
        if(pourcentage < 0.0 ) {
            this.pourcentage = 0.0;
        }else if(pourcentage > 100.0){
            this.pourcentage = 100.0;
        }else{
            this.pourcentage = pourcentage;
        }
    }

    public List<Resultat> selectionner(Nom nomClient, List<CoupleValeur> couples) {
        List<Resultat> resultats = new ArrayList<>();

        if(couples == null || couples.isEmpty()){
            return Collections.emptyList();
        }

        List<CoupleValeur> couplesTri = new ArrayList<>(couples);

        couplesTri.sort(Comparator.comparingDouble(CoupleValeur::getScore).reversed());

        int limit = (int) Math.ceil((pourcentage/100) * couplesTri.size());
        limit = Math.min(limit,couplesTri.size());

        for(int i = 0 ; i<limit; i++){
            CoupleValeur c = couplesTri.get(i);
            resultats.add(new Resultat(nomClient, c.getNom(), c.getScore(),""));
        }

        return resultats;
    }
}
