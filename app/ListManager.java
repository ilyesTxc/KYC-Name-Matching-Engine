package kyc.app;

import  kyc.model.Nom;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class ListManager {
    private final List<Nom> listeGauche = new ArrayList<>();
    private final List<Nom> listeDroite = new ArrayList<>();
    private final List<String> fichiersCharges = new ArrayList<>();
    private int compteur = 0;

    public void ajouterClient(Nom nom) {
        if (nom != null) listeGauche.add(nom);
    }

    public void loadCSV(String path) {
        if (path == null || path.isBlank()) {
            System.out.println("Chemin CSV invalide.");
            return;
        }

        int added = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            int lineNumber = 0;
            while ((line = br.readLine()) != null) {
                lineNumber++;
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;

                String[] parts = line.split(",", 2);
                if (parts.length < 2) {
                    System.out.printf("Ligne %d ignorée : %s%n", lineNumber, line);
                    continue;
                }


                String nomStr = parts[1].trim();
                if (nomStr.isEmpty()) continue;

                Nom n = new Nom(++compteur, nomStr);
                n.setFichierSource(path);
                listeDroite.add(n);
                added++;
            }
            System.out.printf("%d sanctions chargées depuis : %s%n", added, path);
        } catch (IOException i) {
            System.out.println("Erreur lecture CSV : " + i.getMessage());
        }
        fichiersCharges.add(path);
    }

    public void loadCSVAsClients(String path) {
        if (path == null || path.isBlank()) {
            System.out.println("Chemin CSV invalide.");
            return;
        }
        int added = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            int lineNumber = 0;
            while ((line = br.readLine()) != null) {
                lineNumber++;
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;
                String[] parts = line.split(",", 2);
                if (parts.length < 2) continue;
                String nomStr = parts[1].trim();
                if (nomStr.isEmpty()) continue;
                listeGauche.add(new Nom(++compteur, nomStr));
                added++;
            }
            System.out.printf("%d client(s) chargé(s) depuis : %s%n", added, path);
        } catch (IOException i) {
            System.out.println("Erreur lecture CSV : " + i.getMessage());
        }
    }

    public List<Nom> getListeClients() {
        return listeGauche;
    }

    public List<Nom> getListeSanctions() {
        return listeDroite;
    }

    public List<String> getFichiersCharges() {
        return fichiersCharges;
    }

    public boolean estVide() {
        return listeGauche.isEmpty() && listeDroite.isEmpty();
    }

    public void afficherStats() {
        System.out.printf("Client : %d | Sanctions : %d%n", listeGauche.size(), listeDroite.size());
    }
}



