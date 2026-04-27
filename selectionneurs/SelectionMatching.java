package kyc.selectionneurs;

import kyc.model.Nom;
import kyc.model.CoupleValeur;
import java.util.List;

public interface SelectionMatching {
    List<Nom> selectionner(List<CoupleValeur> couples);
}
