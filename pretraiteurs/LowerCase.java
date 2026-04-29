package kyc.pretraiteurs;

import java.util.List;
import java.util.stream.Collectors;

public class LowerCase implements PreTraiteurNom {
    public List<String> preTraiter(List<String> noms){
        return noms.stream()
                .map(nom-> nom.toLowerCase())
                .collect(Collectors.toList());
    }
}
