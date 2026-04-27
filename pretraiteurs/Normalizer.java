package kyc.pretraiteurs;

import kyc.pretraiteurs.PreTraiteurNom;
import java.util.List;

public class Normalizer implements PreTraiteurNom {
    @Override
    public List<String> preTraiter(List<String> noms) {
        // TODO : normaliser chaque nom (trim, espaces multiples, etc.)
        return null;
    }
}
