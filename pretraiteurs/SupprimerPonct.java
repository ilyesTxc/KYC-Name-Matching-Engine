package kyc.pretraiteurs;
import java.util.List;
import java.util.stream.Collectors;

public class SupprimerPonct implements PreTraiteurNom{
    public List<String> preTraiter(List<String> noms){
        List<String> resultat= noms.stream()
                .map(n-> n.replaceAll("[^\\p{L}\\p{N}\\s]",""))
                .collect(Collectors.toList());
        return resultat;
    }
}
