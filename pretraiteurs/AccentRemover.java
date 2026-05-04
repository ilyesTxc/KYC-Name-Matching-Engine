package kyc.pretraiteurs;

import java.text.Normalizer;
import java.util.List;
import java.util.ArrayList;


public class AccentRemover implements PreTraiteurNom {

    public List<String> preTraiter(List<String> noms) {
       List<String> result = new ArrayList<>();
       for (String nom : noms) {
           String changed = Normalizer.normalize(nom, java.text.Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}", "");
           result.add(changed);
       }
       return result;
    }
}
