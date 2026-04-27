package kyc.app;

import java.util.List;
import java.util.ArrayList;

public class ListManager {
    private List<Controle> listes = new ArrayList<>();

    public void ajouterListe(String path) {
        // TODO : charger une liste depuis path et l'ajouter
    }

    public void loadCSV(String path) {
        // TODO : parser le fichier CSV et peupler listes
    }

    public List<Controle> getActiveListe() {
        // TODO : retourner la liste active
        return listes;
    }
}
