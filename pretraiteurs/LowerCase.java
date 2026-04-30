package kyc.pretraiteurs;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class LowerCase implements PreTraiteurNom {
    public List<String> preTraiter(List<String> noms){
        if(noms== null){
            return new ArrayList<>();
        }
        List<String> results= new ArrayList<>();
        for(String nom: noms){
            if(nom==null){
                continue;
            }
            nom= nom.toLowerCase();
            results.add(nom);
        }
        return results;


    }
}
