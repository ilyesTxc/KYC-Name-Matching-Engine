package kyc.collections;

import kyc.collections.CollectionKYC;
import kyc.model.Nom;

import java.util.ArrayList;
import java.util.List;

/**
 * Collection de Nom basée sur ArrayList.
 * Utilisée pour stocker les listes de noms clients et noms de sanctions.
 * ArrayList choisie car l'accès par index et l'ordre d'insertion sont importants.
 */
public class NomCollection implements CollectionKYC<Nom> {

    private final List<Nom> noms = new ArrayList<>();

    public void ajouter(Nom element) {
        noms.add(element);
    }

    public void supprimer(Nom element) {
        noms.remove(element);
    }

    public boolean contient(Nom element) {
        return noms.contains(element);
    }

    public int taille() {
        return noms.size();
    }

    public boolean estVide() {
        return noms.isEmpty();
    }

    public void vider() {
        noms.clear();
    }

    public List<Nom> versListe() {
        return new ArrayList<>(noms);
    }
}
