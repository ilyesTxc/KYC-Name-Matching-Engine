package kyc.collections;

import kyc.collections.CollectionKYC;
import kyc.model.Nom;

import java.util.ArrayList;
import java.util.List;


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
