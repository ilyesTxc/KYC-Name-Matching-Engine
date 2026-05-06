package kyc.pretraiteurs;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

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

    public static class NGram implements PreTraiteurNom{
        private int n;
        public NGram(){
            this.n = 2;
        }
        public NGram(int n){
            this.n = n;
        }
        public List<String> preTraiter(List<String> noms){
            List<String> result = new ArrayList<>();
            for (String nom : noms) {
                result.addAll(extraireNGrams(nom));
            }
            return result;

        }
        private List<String> extraireNGrams(String s){
            if (s == null || s.length() < n) return Collections.emptyList();
            List<String> ngrams = new ArrayList<>();
            for ( int i = 0; i <= s.length() - n; i++) {
                ngrams.add(s.substring(i, i + n));
            }
            return ngrams;
        }
    }
}
