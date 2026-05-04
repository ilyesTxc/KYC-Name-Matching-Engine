package kyc.livreurs;

import kyc.livreurs.LivreurResultat;
import kyc.model.Resultat;
import java.util.List;

public class AfficherConsole implements LivreurResultat {
    private  List<Resultat> alerts;

    public AfficherConsole(List<Resultat> alerts) {
        this.alerts = alerts;

    }
    public void afficher() {
        for (Resultat r : alerts) {
            System.out.println( "Client: " + r.getNomClient().getNomOriginal() + " | Sanction: " + r.getNomSanction().getNomOriginal() + "| Score: " +r.getScore() + " | Source: " + r.getFichierSource());
        }
    }
    public void livrer() {
        afficher();
    }
}

