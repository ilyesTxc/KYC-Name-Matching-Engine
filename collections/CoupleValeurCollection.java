package kyc.collections;

import kyc.collections.CollectionKYC;
import kyc.model.CoupleValeur;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Collection de CoupleValeur basée sur HashSet.
 * Utilisée pour stocker les candidats générés avec leur score.
 * Set choisi pour garantir l'unicité des couples (pas de doublons
 * lors de la génération des candidats).
 */
public class CoupleValeurCollection implements CollectionKYC<CoupleValeur> {

    private final Set<CoupleValeur> couples = new HashSet<>();

    @Override
    public void ajouter(CoupleValeur element) {
        couples.add(element);
    }

    @Override
    public void supprimer(CoupleValeur element) {
        couples.remove(element);
    }

    @Override
    public boolean contient(CoupleValeur element) {
        return couples.contains(element);
    }

    @Override
    public int taille() {
        return couples.size();
    }

    @Override
    public boolean estVide() {
        return couples.isEmpty();
    }

    @Override
    public void vider() {
        couples.clear();
    }

    @Override
    public List<CoupleValeur> versListe() {
        return new ArrayList<>(couples);
    }
}
