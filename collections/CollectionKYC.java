package kyc.collections;

import java.util.List;

/**
 * Interface générique commune pour toutes les collections du projet KYC.
 * Chaque implémentation choisit librement la structure sous-jacente
 * (List, Set, Queue, Map...) selon ses besoins métier.
 *
 * @param <T> le type d'entité stockée (Nom, Resultat, CoupleValeur...)
 */
public interface CollectionKYC<T> {

    /** Ajoute un élément à la collection. */
    void ajouter(T element);

    /** Supprime un élément de la collection. */
    void supprimer(T element);

    /** Vérifie si la collection contient l'élément donné. */
    boolean contient(T element);

    /** Retourne le nombre d'éléments dans la collection. */
    int taille();

    /** Vérifie si la collection est vide. */
    boolean estVide();

    /** Vide complètement la collection. */
    void vider();

    /** Retourne tous les éléments sous forme de List pour itération. */
    List<T> versListe();
}
