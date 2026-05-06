package kyc.app;
import kyc.model.Nom;

import java.util.*;
import java.util.TreeMap;

public class IndexPrefixArbre{
    private final TreeMap<Integer, Set<Nom>> index;
    private final int tailleListeSanctions;


    public IndexPrefixArbre(List<Nom> sanctions){
        this.index = new TreeMap<>();

        if (sanctions == null) {
            this.tailleListeSanctions = 0;
            return;
        }

        for(Nom nom : sanctions){
            List<String> tokens = nom.getNomPretraite();
            if(tokens == null || tokens.isEmpty()){
                continue;
            }

            int length = longeur(tokens);
            index.computeIfAbsent(length,k->new LinkedHashSet<>()).add(nom);
        }

        this.tailleListeSanctions = sanctions.size();
    }

    public int longeur(List<String> tokens){
        if(tokens == null || tokens.isEmpty()){
            return 0;
        }
        
        int total = 0;
        for(String t : tokens){
            total+= t.length();
        }
        return total;
    }

    public int getTailleListeSanctions() {
        return tailleListeSanctions;
    }

    public Set<Nom> RechercherParLongeur(int targetLength, int tolerance){

        Set<Nom> results =new LinkedHashSet<>();

        int from = Math.max(1, targetLength - tolerance);
        int to = targetLength + tolerance;

        NavigableMap<Integer, Set<Nom>> range = index.subMap(from, true, to, true);

        for(Set<Nom> bucket : range.values()){
            results.addAll(bucket);
        }

        return results;
    }

}
