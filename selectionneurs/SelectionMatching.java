package kyc.selectionneurs;

import kyc.model.Nom;
import kyc.model.CoupleValeur;
import kyc.model.Resultat;

import java.util.List;

public interface SelectionMatching {
    List<Resultat> selectionner(Nom nomClient, List<CoupleValeur> couples);
}
