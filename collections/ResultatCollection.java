package kyc.collections;

import kyc.collections.CollectionKYC;
import kyc.model.Resultat;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


public class ResultatCollection implements CollectionKYC<Resultat> {

    private final Queue<Resultat> resultats = new LinkedList<>();

    public void ajouter(Resultat element) {
        resultats.offer(element);
    }

    public void supprimer(Resultat element) {
        resultats.remove(element);
    }

    public boolean contient(Resultat element) {
        return resultats.contains(element);
    }

    public int taille() {
        return resultats.size();
    }

    public boolean estVide() {
        return resultats.isEmpty();
    }

    public void vider() {
        resultats.clear();
    }

    public List<Resultat> versListe() {
        return new ArrayList<>(resultats);
    }


    public Resultat extraireProchain() {
        return resultats.poll();
    }
}
