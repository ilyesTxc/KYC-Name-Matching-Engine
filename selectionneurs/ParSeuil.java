package kyc.selectionneurs;

import kyc.model.Nom;
import kyc.model.CoupleValeur;
import kyc.model.Resultat;
import java.util.List;
import java.util.ArrayList;

public class ParSeuil implements SelectionMatching {
    private double seuil;

    public ParSeuil(double seuil) {
        this.seuil = seuil;
    }

    public List<Resultat> selectionner(Nom nomClient, List<CoupleValeur> couples) {
        if (couples == null){
            return new ArrayList<>();
        }
        List<Resultat> resultats= new ArrayList<>();
        for (CoupleValeur couple: couples){
            if ( couple != null && couple.getScore() >= seuil) {
                Resultat resultat = new Resultat(nomClient, couple.getNom(), couple.getScore(), "source inconnue");
                resultats.add(resultat);
            }
        }
        return resultats;
    }
}
