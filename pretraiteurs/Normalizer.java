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

    public static void main(String[] args) {
        PreTraiteurNom normalizer = new Normalizer();

        List<String> noms = Arrays.asList(
                "  Ahmed    Ben   Ali  ",
                "  ILYÈS  ",
                "Sâmi     Salah",
                "   Mohamed\tAli   "
        );

        List<String> resultats = normalizer.preTraiter(noms);

        System.out.println(resultats);
    }

}
