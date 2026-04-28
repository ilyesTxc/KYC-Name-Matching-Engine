package kyc.pretraiteurs;

import java.text.Normalizer;
import java.util.List;
import java.util.stream.Collectors;


public class AccentRemover implements PreTraiteurNom {

    public List<String> preTraiter(List<String> noms) {
        return noms.stream().map(noms → Normalizer.normalize(noms, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}", "")).collect(Collectors.toList());

    }
}
