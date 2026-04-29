package kyc.pretraiteurs;

import java.text.Normalizer;
import java.util.List;
import java.util.stream.Collectors;


public class AccentRemover implements PreTraiteurNom {

    public List<String> preTraiter(List<String> noms) {
        return noms.stream().map(nom -> Normalizer.normalize(nom, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}", "")).collect(Collectors.toList());

    }
}
