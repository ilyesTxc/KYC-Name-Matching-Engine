package kyc.pretraiteurs;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class SupprimerPonct implements PreTraiteurNom{
    public List<String> preTraiter(List<String> noms){
        if(noms== null){
            return new ArrayList<>();
        }
        List<String> results= new ArrayList<>();
        for(String nom: noms){
            if(nom==null){
                continue;
            }
            nom=nom.replaceAll("[^\\p{L}\\p{N}\\s]","");
            results.add(nom);
        }
        return results;
    }
}
