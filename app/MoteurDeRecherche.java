package kyc.app;

import kyc.livreurs.LivreurResultat;
import kyc.model.CoupleValeur;
import kyc.model.Nom;
import kyc.model.Resultat;
import kyc.pretraiteurs.PreTraiteurNom;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MoteurDeRecherche {
    private Configuration config;
    private ListManager listManager;
    private LivreurResultat livreur;

    private long tempsPretraitementMS;
    private long tempsPipelineTotalMs;

    public MoteurDeRecherche(Configuration config, ListManager listManager, LivreurResultat livreur) {
        this.config = config;
        this.listManager = listManager;
        this.livreur = livreur;
    }

    public long getTempsPretraitementMS() {return tempsPretraitementMS; }
    public long getTempsPipelineTotalMs() { return tempsPipelineTotalMs; }


    public Map<Nom, List<Resultat>> lancerPipeline(List<Nom> listeClients, List<Nom> listeSanctions) {
        long debutTotal = System.nanoTime();

        if (listeClients.isEmpty()) {
            System.out.println("Aucun nom client à traiter.");
            return new LinkedHashMap<>();
        }
        if (listeSanctions.isEmpty()) {
            System.out.println("La liste de sanctions est vide !");
            return new LinkedHashMap<>();
        }

        long debutPretraitement = System.nanoTime();

        pretraiter(listeSanctions);
        pretraiter(listeClients);

        tempsPretraitementMS = (System.nanoTime()- debutPretraitement)/1_000_000;

        GenerateurCandidat generateur = switch (config.getGenerateurType()) {
            case PHONETIQUE -> new ClePhonetique(new IndexPhonetique(listeSanctions));
            case ARBRE  -> new GenerateurArbre(new IndexPrefixArbre(listeSanctions));
            case PREFIX_HASH -> new GenerateurPrefixHash(new IndexPrefixHash(listeSanctions));
            case BRUT -> new GenerateurBrut();
        };

        Map<Nom, List<Nom>> candidatsParClient = generateur.genererCandidats(listeClients, listeSanctions);

        Map<Nom, List<Resultat>> resultatsParClient = new LinkedHashMap<>();
        for (Nom nomClient : listeClients) {
            resultatsParClient.put(nomClient, new ArrayList<>());
        }

        for (Map.Entry<Nom, List<Nom>> entry : candidatsParClient.entrySet()) {
            Nom nomClient = entry.getKey();
            List<Nom> candidats = entry.getValue();
            if (candidats.isEmpty()) continue;
            traiterClient(nomClient, candidats, resultatsParClient.get(nomClient));
        }

        tempsPipelineTotalMs = (System.nanoTime()-debutTotal)/1_000_000;

        int totalAlertes = 0;
        for (List<Resultat> alertes : resultatsParClient.values()) {
            totalAlertes += alertes.size();
        }

        if (totalAlertes == 0) {
            System.out.println("Pipeline terminé: aucune alerte détectée !");
        } else {
            System.out.printf("%d alerte(s) détectée(s) !%n", totalAlertes);
        }

        return resultatsParClient;
    }



    private void traiterClient(Nom nomClient, List<Nom> candidats, List<Resultat> toutesAlertes) {
        List<CoupleValeur> couples = comparer(nomClient, candidats);
        if (config.getStrategie() != null) {
            List<Resultat> alertes = config.getStrategie().selectionner(nomClient, couples);
            if (alertes != null) toutesAlertes.addAll(alertes);
        }
    }

    private void pretraiter(List<Nom> noms) {
        for (Nom nom : noms) {
            if (nom.getNomPretraite() != null) continue;
            List<String> tokens = List.of(nom.getNomOriginal().split("\\s+"));
            for (PreTraiteurNom p : config.getPreTraiteurs()) {
                tokens = p.preTraiter(tokens);
            }
            nom.setNomPretraite(tokens);
        }
    }

    private List<CoupleValeur> comparer(Nom nomClient, List<Nom> candidats) {
        List<CoupleValeur> couples = new ArrayList<>();
        for (Nom candidat : candidats) {
            double score = config.getComparateur() != null
                    ? config.getComparateur().comparer(nomClient, candidat)
                    : 0.0;
            couples.add(new CoupleValeur(candidat, score));
        }
        return couples;
    }

}
