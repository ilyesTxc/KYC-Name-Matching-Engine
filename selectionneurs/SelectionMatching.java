package kyc.selectionneurs;

import kyc.model.Nom;
import kyc.model.CoupleValeur;
import kyc.model.Resultat;
import java.util.List;
import java.util.ArrayList;

public interface SelectionMatching {
    List<Resultat> selectionner(List<CoupleValeur> couples);
}
