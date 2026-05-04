package kyc.app;

import  kyc.model.Nom;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class ListManager {
    private final List<Nom> listeClients = new ArrayList<>();
    private final List<Nom> listeSanctions = new ArrayList<>();
    private int compteur = 0;

    public void ajouterClient(Nom nom) {
        if (nom != null) listeClients.add(nom);
    }

    public void loadCSV(String path) {
        if (path == null || path.isBlank()) {
            System.out.println(" Chemin CSV invalide.");
            return;
        }
        int added = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            int lineNumber = 0;
            while ((line = br.readLine()) != null) {
                lineNumber++;
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#") || line.equalsIgnoreCase("type,nom")) {
                    continue;
                }
                String[] parts = line.split(",", 2);
                if (parts.length < 2) {
                    System.out.printf("Ligne %d ignorée : %s%n", lineNumber, line);
                    continue;
                }
                String type = parts[0].trim().toLowerCase();
                String nomStr = parts[1].trim();
                if (nomStr.isEmpty()) continue;
                Nom nom = new Nom(++compteur, nomStr);
                switch (type) {
                    case "client" -> listeClients.add(nom);
                    case "sanction" -> listeSanctions.add(nom);
                    default -> System.out.printf("Ligne %d : type inconnu '%s'%n", lineNumber, type);
                }
                added++;
            }
            System.out.printf("%d nom(s) chargé(s) depuis : %s%n", added, path);
        } catch (IOException i) {
            System.out.println("Erreur lecture CSV : " + i.getMessage());
        }
    }

    public List<Nom> getListeClients() {
        return listeClients;
    }

    public List<Nom> getListeSanctions() {
        return listeSanctions;
    }

    public boolean estVide() {
        return listeClients.isEmpty() && listeSanctions.isEmpty();
    }

    public void afficherStats() {
        System.out.printf("Client : %d | Sanctions : %d%n", listeClients.size(), listeSanctions.size());
    }
}



