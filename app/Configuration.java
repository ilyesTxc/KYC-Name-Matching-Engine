package kyc.app;
import kyc.selectionneurs.SelectionMatching;
import kyc.pretraiteurs.PreTraiteurNom;
import kyc.comparateurs.ComparateurNom;


public class Configuration {
    private double seuil;
    private SelectionMatching strategie;
    private PreTraiteurNom preTraiteur;
    private GenerateurCandidat generateur;
    private ComparateurNom comparateur;
    public enum GenerateurType { PHONETIQUE, ARBRE, PREFIX_HASH }


    public double getSeuil() {
        return seuil;
    }
    public void setSeuil(double seuil) {
        this.seuil = seuil;
    }

    public SelectionMatching getStrategie() {
        return strategie;
    }
    public void setStrategie(SelectionMatching strategie) {
        this.strategie = strategie;
    }

    public PreTraiteurNom getPreTraiteur() {
        return preTraiteur;
    }
    public void setPreTraiteur(PreTraiteurNom preTraiteur) {
        this.preTraiteur = preTraiteur;
    }
    private GenerateurType generateurType = GenerateurType.PHONETIQUE;

    public GenerateurType getGenerateurType() {
        return generateurType;
    }

    public void setGenerateurType(GenerateurType t) {
        this.generateurType = t;
    }

    public GenerateurCandidat getGenerateur() {
        return generateur;
    }
    public void setGenerateur(GenerateurCandidat generateur) {
        this.generateur = generateur;
    }

    public ComparateurNom getComparateur() {
        return comparateur;
    }
    public void setComparateur(ComparateurNom comparateur) {
        this.comparateur = comparateur;
    }
}
