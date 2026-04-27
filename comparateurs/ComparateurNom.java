package kyc.comparateurs;
import kyc.model.Nom;

public abstract class ComparateurNom {

    /**
     * Méthode abstraite à implémenter par chaque algorithme de comparaison.
     * @param n1 premier nom
     * @param n2 deuxième nom
     * @return score de similarité entre 0.0 et 1.0
     */
    public abstract double comparer(Nom n1, Nom n2);

    /**
     * Méthode concrète commune : vérifie si le score dépasse un seuil donné.
     * @param n1 premier nom
     * @param n2 deuxième nom
     * @param seuil seuil de similarité
     * @return true si la similarité >= seuil
     */
    public boolean estSimilaire(Nom n1, Nom n2, double seuil) {
        return comparer(n1, n2) >= seuil;
    }
}
