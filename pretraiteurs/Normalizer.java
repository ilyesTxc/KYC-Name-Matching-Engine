package kyc.pretraiteurs;

import kyc.pretraiteurs.PreTraiteurNom;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class Normalizer implements PreTraiteurNom {
    public List<String> preTraiter(List<String> noms) {
        List<String> results = new ArrayList<>();

        for(String nom : noms){
                nom = nom.trim();
                nom = nom.replaceAll("\\s+"," ");
                results.add(nom);
        }

        return results;
    }
}
