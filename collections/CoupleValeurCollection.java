package kyc.collections;

import kyc.collections.CollectionKYC;
import kyc.model.CoupleValeur;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CoupleValeurCollection implements CollectionKYC<CoupleValeur> {

    private final Set<CoupleValeur> couples = new HashSet<>();

    public void ajouter(CoupleValeur element) {
        couples.add(element);
    }

    public void supprimer(CoupleValeur element) {
        couples.remove(element);
    }

    public boolean contient(CoupleValeur element) {
        return couples.contains(element);
    }

    public int taille() {
        return couples.size();
    }

    public boolean estVide() {
        return couples.isEmpty();
    }

    public void vider() {
        couples.clear();
    }

    public List<CoupleValeur> versListe() {
        return new ArrayList<>(couples);
    }
}
