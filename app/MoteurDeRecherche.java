package kyc.app;

import kyc.livreurs.LivreurResultat;
import kyc.model.Resultat;
import java.util.List;

public class MoteurDeRecherche {

    private Configuration config;
    private ListManager listManager;
    private LivreurResultat livreur;

    public MoteurDeRecherche(Configuration config,
                              ListManager listManager,
                              LivreurResultat livreur) {
        this.config = config;
        this.listManager = listManager;
        this.livreur = livreur;
    }

    public List<Resultat> lancerPipeline() {
        // TODO : orchestrer le pipeline complet
        // 1. Récupérer les listes via listManager
        // 2. Prétraiter les noms via config.getPreTraiteur()
        // 3. Générer les candidats via config.getGenerateur()
        // 4. Comparer les noms via config.getComparateur()
        // 5. Sélectionner les résultats via config.getStrategie()
        // 6. Livrer les alertes via livreur
        return null;
    }
}
