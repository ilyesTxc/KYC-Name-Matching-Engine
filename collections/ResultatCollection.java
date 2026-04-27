package kyc.collections;

import kyc.collections.CollectionKYC;
import kyc.model.Resultat;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Collection de Resultat basée sur LinkedList (utilisée comme Queue).
 * Utilisée pour stocker les résultats de matching à livrer.
 * Queue choisie car les résultats sont traités dans l'ordre FIFO
 * (premier résultat produit = premier livré).
 */
public class ResultatCollection implements CollectionKYC<Resultat> {

    private final Queue<Resultat> resultats = new LinkedList<>();

    @Override
    public void ajouter(Resultat element) {
        resultats.offer(element);
    }

    @Override
    public void supprimer(Resultat element) {
        resultats.remove(element);
    }

    @Override
    public boolean contient(Resultat element) {
        return resultats.contains(element);
    }

    @Override
    public int taille() {
        return resultats.size();
    }

    @Override
    public boolean estVide() {
        return resultats.isEmpty();
    }

    @Override
    public void vider() {
        resultats.clear();
    }

    @Override
    public List<Resultat> versListe() {
        return new ArrayList<>(resultats);
    }

    /**
     * Retire et retourne le prochain résultat à livrer (comportement Queue).
     * @return le premier Resultat en attente, ou null si vide
     */
    public Resultat extraireProchain() {
        return resultats.poll();
    }
}
