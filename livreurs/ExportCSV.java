package kyc.livreurs;

import kyc.livreurs.LivreurResultat;
import kyc.model.Resultat;
import java.util.List;

public class ExportCSV implements LivreurResultat {
    private List<Resultat> alertes;

    public ExportCSV(List<Resultat> alertes) {
        this.alertes = alertes;
    }

    public void exporter(String path) {
        // TODO : écrire alertes dans un fichier CSV au chemin path
    }

    @Override
    public void livrer() {
        // TODO : appeler exporter() avec le chemin par défaut
    }
}
