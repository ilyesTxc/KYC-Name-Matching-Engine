package kyc.app;
import kyc.selectionneurs.SelectionMatching;
import kyc.selectionneurs.ParSeuil;
import kyc.pretraiteurs.PreTraiteurNom;
import kyc.pretraiteurs.Normalizer;
import kyc.comparateurs.ComparateurNom;
import kyc.comparateurs.JaroWinkler;
import kyc.model.Nom;
import java.util.List;


public class Configuration {
    public enum GenerateurType {PHONETIQUE, ARBRE, PREFIX_HASH, BRUT}

    private SelectionMatching strategie;
    private PreTraiteurNom preTraiteur;
    private GenerateurCandidat generateur;
    private ComparateurNom comparateur;
    private GenerateurType generateurType;

    public Configuration() {
        this.comparateur = new ComparateurNom() {
            private final JaroWinkler jw = new JaroWinkler();

            public double comparer(Nom n1, Nom n2) {
                String s1 = String.join(" ", n1.getNomPretraite() != null ? n1.getNomPretraite() : List.of(n1.getNomOriginal().split("\\s+")));
                String s2 = String.join(" ", n2.getNomPretraite() != null ? n2.getNomPretraite() : List.of(n2.getNomOriginal().split("\\s+")));
                return jw.comparerChaine(s1, s2);
            }
        };
        this.strategie = new ParSeuil(0.8);
        this.preTraiteur = new Normalizer();
        this.generateurType = GenerateurType.PHONETIQUE;
    }

    public ComparateurNom getComparateur() {
        return comparateur;
    }
    public void setComparateur(ComparateurNom comparateur) {
        this.comparateur = comparateur;
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

    public String afficherConfig() {
        return String.format("Config active -> Comparateur : %s | Stratégie : %s | Prétraiteur : %s | Générateur : %s", comparateur.getClass().getSimpleName(), strategie.toString(), preTraiteur.getClass().getSimpleName(), generateurType);
    }
}
