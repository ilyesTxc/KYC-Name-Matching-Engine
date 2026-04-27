package kyc.pretraiteurs;

import kyc.pretraiteurs.PreTraiteurNom;
import java.util.List;

public class AccentRemover implements PreTraiteurNom {
    @Override
    public List<String> preTraiter(List<String> noms) {
        // TODO : supprimer les accents de chaque nom
        return null;
    }
}
