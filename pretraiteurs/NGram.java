package kyc.pretraiteurs;

import kyc.comparateurs.ComparateurChaine;
import kyc.pretraiteurs.PreTraiteurNom;

import java.util.ArrayList;
import java.util.List;

public class NGram implements ComparateurChaine, PreTraiteurNom {

    private int n;

    public NGram() {
        this.n = 2; // bigrammes par défaut
    }

    public NGram(int n) {
        this.n = n;
    }

    // ---- ComparateurChaine ----

    @Override
    public double comparerChaine(String l1, String l2) {
        // TODO : implémenter la similarité par N-grammes
        return 0;
    }

    // ---- PreTraiteurNom ----

    /**
     * Décompose chaque nom de la liste en N-grammes et retourne
     * la liste des tokens obtenus.
     */
    @Override
    public List<String> preTraiter(List<String> noms) {
        List<String> tokens = new ArrayList<>();
        for (String nom : noms) {
            tokens.addAll(extraireNGrams(nom));
        }
        return tokens;
    }

    /**
     * Extrait les N-grammes d'une chaîne donnée.
     */
    private List<String> extraireNGrams(String s) {
        List<String> ngrams = new ArrayList<>();
        if (s == null || s.length() < n) return ngrams;
        for (int i = 0; i <= s.length() - n; i++) {
            ngrams.add(s.substring(i, i + n));
        }
        return ngrams;
    }
}
