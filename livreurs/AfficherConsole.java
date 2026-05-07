package kyc.livreurs;

import kyc.model.Nom;
import kyc.model.Resultat;
import java.util.List;
import java.util.Map;

public class AfficherConsole implements LivreurResultat {
    private Map<Nom, List<Resultat>> resultats;

    public AfficherConsole(Map<Nom, List<Resultat>> resultats) {
        this.resultats = resultats;
    }

    public void afficher() {
        for (Map.Entry<Nom, List<Resultat>> entry : resultats.entrySet()) {
            String client = entry.getKey().getNomOriginal();
            List<Resultat> alertes = entry.getValue();
            if (alertes.isEmpty()) {
                System.out.printf("Client: %s, aucune correspondance%n", client);
            } else {
                for (Resultat r : alertes) {
                    System.out.printf("Client: %-20s | Sanction: %-20s | Score: %.2f%n",
                            client, r.getNomSanction().getNomOriginal(), r.getScore());
                }
            }
        }
    }

    public void livrer() {
        afficher();
    }
}