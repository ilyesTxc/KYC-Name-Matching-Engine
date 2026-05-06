package kyc.app;

import kyc.livreurs.LivreurResultat;
import kyc.model.CoupleValeur;
import kyc.model.Nom;
import kyc.model.Resultat;
import java.util.ArrayList;
import java.util.List;

public class MoteurDeRecherche {
    private Configuration config;
    private ListManager listManager;
    private LivreurResultat livreur;

    public MoteurDeRecherche (Configuration config, ListManager listManager, LivreurResultat livreur){
        this.config = config;
        this.listManager = listManager;
        this.livreur = livreur;
    }
    public List<Resultat> lancerPipeline(){
        List<Nom> listeClients = listManager.getListeClients();
        List<Nom> listeSanctions = listManager.getListeSanctions();

        if (listeClients.isEmpty()) {
            System.out.println("Aucun nom client à traiter .");
            return new ArrayList<>();
        }
        if (listeSanctions.isEmpty()) {
            System.out.println("La liste de sanctions est vide, aucune comparaison possible !");
            return new ArrayList<>();
        }

        // DEBUG
        System.out.println("DEBUG — comparateur: " + config.getComparateur());
        System.out.println("DEBUG — strategie: " + config.getStrategie());
        System.out.println("DEBUG — preTraiteur: " + config.getPreTraiteur());
        System.out.println("DEBUG — clients: " + listeClients.size() + " | sanctions: " + listeSanctions.size());

        pretraiter(listeSanctions);
        pretraiter(listeClients);

        List<Resultat> toutesAlertes = new ArrayList<>();
        for (Nom nomClient : listeClients) {
            List<Nom> candidats = genererCandidats(nomClient, listeSanctions);
            if (candidats.isEmpty()) continue;
            List<CoupleValeur> couples = comparer(nomClient, candidats);

            if (config.getStrategie() != null) {
                List<Resultat> alertes = config.getStrategie().selectionner(nomClient, couples);
                if (alertes != null ) toutesAlertes.addAll(alertes);
            }
        }

        if (toutesAlertes.isEmpty()) {
            System.out.println("Pipeline terminé : aucune alerte détectée !" );
        } else {
            System.out.printf("Moteur %d alertes(s) détectée(s) ! %n", toutesAlertes.size());
        }
        return toutesAlertes;
    }


    private void pretraiter(List<Nom> noms) {
        for (Nom nom : noms) {
            if (nom.getNomPretraite() != null) continue;
            if (config.getPreTraiteur() != null) {
                List<String> tokens = config.getPreTraiteur().preTraiter(List.of(nom.getNomOriginal().split("\\s+")));
                nom.setNomPretraite(tokens);
            } else {
                nom.setNomPretraite(List.of(nom.getNomOriginal().split("\\s+")));
            }
        }
    }

    private List<Nom> genererCandidats(Nom nomClient, List<Nom> listeSanctions) {
        if (config.getGenerateur() != null ) {
            List<Nom> candidats = config.getGenerateur().genererCandidats(nomClient, listeSanctions);
            return candidats != null ? candidats : new ArrayList<>();
        }
        return listeSanctions;
    }

    private List<CoupleValeur> comparer(Nom nomClient, List<Nom> candidats) {
        List<CoupleValeur> couples = new ArrayList<>();
        for (Nom candidat : candidats) {
            double score = config.getComparateur() != null ? config.getComparateur().comparer(nomClient,candidat) : 0.0;
            couples.add(new CoupleValeur(candidat, score));
        }
        return couples;
    }
}