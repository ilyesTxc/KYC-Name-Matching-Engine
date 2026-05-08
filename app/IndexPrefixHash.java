package kyc.app;

import kyc.model.Nom;
import java.util.*;


public class IndexPrefixHash {

    private final Map<String, Set<Nom>> index;
    private final static int TAILLE_PREFIX = 3;
    private final int taillePrefixe;

    public IndexPrefixHash(List<Nom> sanctions) {
        this(sanctions, TAILLE_PREFIX);
    }

    public IndexPrefixHash(List<Nom> sanctions, int taillePrefixe){
        this.index = new HashMap<>();
        this.taillePrefixe = Math.max(1, taillePrefixe);
        construireIndex(sanctions);
    }


    private Set<String> genererCles(Nom nom){
        Set<String> cles = new LinkedHashSet<>();

        if(nom == null || nom.getNomPretraite() == null){
            return cles;
        }

        for(String token : nom.getNomPretraite()){
            String tokenNettoye = nettoyerToken(token);

            if(tokenNettoye.isEmpty()){
                continue;
            }

            int fin = Math.min(taillePrefixe, tokenNettoye.length());
            String prefixe = tokenNettoye.substring(0,fin);

            cles.add(prefixe);
        }

        return cles;
    }

    private String nettoyerToken(String token){
        if(token == null){
            return "";
        }

        return token.toLowerCase().replaceAll("[^\\p{L}]","");
    }

    private void construireIndex(List<Nom> sanctions) {
        if (sanctions == null) {
            return;
        }

        for (Nom sanction : sanctions) {
            Set<String> cles = genererCles(sanction);

            for (String cle : cles) {
                index.computeIfAbsent(cle, k -> new LinkedHashSet<>()).add(sanction);
            }
        }
    }


    public Set<Nom> rechercher(Nom client){
        Set<Nom> resultats = new LinkedHashSet<>();

        Set<String> clesClient = genererCles(client);

        for(String cle : clesClient){
            Set<Nom> candidats = index.get(cle);

            if(candidats != null){
                resultats.addAll(candidats);
            }
        }
        return resultats;
    }



}
