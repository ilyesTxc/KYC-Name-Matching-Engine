package kyc.livreurs;

import kyc.livreurs.LivreurResultat;
import kyc.model.Resultat;
import java.util.List;

public class AfficherConsole implements LivreurResultat {
    private List<Resultat> alertes;

    public AfficherConsole(List<Resultat> alertes) {
        this.alertes = alertes;
    }

    public void afficher() {
        // TODO : afficher chaque Resultat dans la console
    }

    @Override
    public void livrer() {
        afficher();
    }
}
