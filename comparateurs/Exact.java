package kyc.comparateurs;

import kyc.comparateurs.ComparateurNom;
import kyc.model.Nom;

public class Exact extends ComparateurNom {
    @Override
    public double comparer(Nom n1, Nom n2) {
        // TODO : retourner 1.0 si noms identiques, 0.0 sinon
        return 0;
    }
}
