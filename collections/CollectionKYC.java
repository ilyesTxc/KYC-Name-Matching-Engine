package kyc.collections;

import java.util.List;

public interface CollectionKYC<T> {

    void ajouter(T element);

    void supprimer(T element);

    boolean contient(T element);

    int taille();

    boolean estVide();

    void vider();

    List<T> versListe();
}
