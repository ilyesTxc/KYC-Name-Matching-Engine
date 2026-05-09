package kyc.livreurs;

import kyc.model.Resultat;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ExportCSV implements LivreurResultat {
    private static final String DEFAULT_PATH= "resultats_kyc.csv";
    private final List<Resultat> alertes;

    public ExportCSV(List<Resultat> alertes) {
        this.alertes = alertes;
    }

    private String echapper(String valeur){
        if( valeur == null) return "";
        return "\""+valeur.replace("\"", "\"\"")+"\"";
    }

    public void exporter(String path) {
        if (alertes == null || alertes.isEmpty()){
            System.out.println("Aucune alerte à exporter");
        }
        try (BufferedWriter writer = new BufferedWriter( new FileWriter(path))){
            writer.write("nomClient,nomSanction,score,fichierSource");
            writer.newLine();
            for (Resultat alerte : alertes){
                String ligne= echapper(alerte.getNomClient().getNomOriginal())
                        + "," + echapper(alerte.getNomSanction().getNomOriginal())
                        + "," + alerte.getScore()
                        + "," + echapper(alerte.getFichierSource());
                writer.write(ligne);
                writer.newLine();
            }
            System.out.println("Export CSV terminé: " + path);
        }
        catch (IOException e){
            System.err.println("Erreur d'écriture CSV : " +e.getMessage());
        }
    }

    public void livrer() {
        exporter(DEFAULT_PATH);
    }
}
