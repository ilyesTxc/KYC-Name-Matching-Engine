package kyc.selectionneurs;

import kyc.selectionneurs.SelectionMatching;
import kyc.model.Nom;
import kyc.model.CoupleValeur;
import kyc.model.Resultat;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ParSeuil implements SelectionMatching{
    private double seuil;

    public ParSeuil(double seuil) {
        if(seuil < 0.0){
            this.seuil = 0.0;
        }else if(seuil > 1.0){
            this.seuil = 1.0;
        }else{
            this.seuil = seuil;
        }
    }
    public ParSeuil(){
        this.seuil = 0.7;
    }

    public List<Resultat> selectionner(Nom nomClient, List<CoupleValeur> couples) {
        List<Resultat> resultats = new ArrayList<>();

        if(couples == null || couples.isEmpty()){
            return resultats;
        }

        List<CoupleValeur> coupleTri = new ArrayList<>(couples);

        coupleTri.sort(Comparator.comparingDouble(CoupleValeur::getScore).reversed());

        for(CoupleValeur c : coupleTri){
            if(c.getScore() >= seuil){
                resultats.add(new Resultat(nomClient, c.getNom(), c.getScore(),""));
            }else{
                break;
            }
        }

        return resultats;

    }
}
