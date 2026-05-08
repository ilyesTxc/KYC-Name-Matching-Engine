package kyc.app;

import kyc.comparateurs.ComparateurNom;
import kyc.comparateurs.JaroWinkler;
import kyc.comparateurs.Levenshtein;
import kyc.comparateurs.Exact;
import kyc.livreurs.AfficherConsole;
import kyc.livreurs.ExportCSV;
import kyc.livreurs.LivreurResultat;
import kyc.model.Nom;
import kyc.model.Resultat;
import kyc.pretraiteurs.AccentRemover;
import kyc.pretraiteurs.LowerCase;
import kyc.pretraiteurs.Normalizer;
import kyc.selectionneurs.ParNPourcentage;
import kyc.selectionneurs.ParNPremier;
import kyc.selectionneurs.ParSeuil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Menu {
    private Configuration config;
    private ListManager listManager;
    private Scanner scanner;
    private int idCounter = 1;


    public Menu(Configuration config, ListManager listManager) {
        this.config = config;
        this.listManager = listManager;
        this.scanner = new Scanner(System.in);
    }

    public void afficherMenuPrincipal() {
        boolean continuer = true;
        while (continuer) {
            System.out.println("Moteur De Recherche De Noms");
            System.out.println("0. Charger un fichier CSV des sanctions ");
            System.out.println("1. Ajouter liste de client");
            System.out.println("2. Ajouter une liste de sanction");
            System.out.println("3. Lancer le pipeline");
            System.out.println("4. Afficher le rapport");
            System.out.println("5. Gérer la configuration");
            System.out.println("6. Voir les fichiers CSV chargés");
            System.out.println("7. Quitter");
            System.out.println("Votre choix : ");
            String choix = scanner.nextLine().trim();

            switch (choix) {
                case "0":
                    chargerCSV();
                    break;
                case "1":
                    chargerListeClient();
                    break;
                case "2":
                    ajouter(demanderNom(), listManager.getListeSanctions());
                    break;
                case "3":
                    lancerPipeline();
                    break;
                case "4":
                    rapport();
                    break;
                case "5":
                    genererConfig();
                    break;
                case "6":
                    afficherFichierCSV();
                    break;
                case "7":
                    quitter();
                    continuer = false;
                    break;
                default:
                    System.out.println("Choix invalide, veuillez réessayer!");
            }
        }
    }

    private Nom demanderNom() {
        System.out.println("Entrez le nom :");
        String nomOriginal = scanner.nextLine().trim();
        return new Nom(idCounter++, nomOriginal);
    }

    public void ajouter(Nom nom, List<Nom> liste) {
        if (nom == null || nom.getNomOriginal() == null || nom.getNomOriginal().isBlank()) {
            System.out.println("Nom invlaide, ajout annulé!");
            return;
        }
        liste.add(nom);
        System.out.println("Nom ajouté : " + nom.getNomOriginal());
    }

    public void miseAJour(List<Nom> liste) {
        for (Nom nom : liste) {
            nom.setNomPretraite(null);
        }
        System.out.println("Liste mise à jour :" + liste.size() + " nom(s) réinitialisé(s).");
    }

    private Map<Nom, List<Resultat>> derniersResultats;

    private void lancerPipeline() {
        MoteurDeRecherche moteur = new MoteurDeRecherche(config, listManager, null);
        derniersResultats = moteur.lancerPipeline();
        if (derniersResultats != null && !derniersResultats.isEmpty()) {
            new AfficherConsole(derniersResultats).livrer();
        }
    }

    public void rapport() {
        if (derniersResultats == null || derniersResultats.isEmpty()) {
            System.out.println("Aucun résultat disponible. Lancez le pipeline d'abord.");
            return;
        }
        int total = 0;
        System.out.println("Rapport des alertes :");
        for (Map.Entry<Nom, List<Resultat>> entry : derniersResultats.entrySet()) {
            String client = entry.getKey().getNomOriginal();
            List<Resultat> alertes = entry.getValue();
            if (alertes.isEmpty()) {
                System.out.printf("  %s → aucune correspondance%n", client);
            } else {
                for (Resultat r : alertes) {
                    System.out.printf("  Client: %-20s | Sanction: %-20s | Score: %.2f%n",
                            client, r.getNomSanction().getNomOriginal(), r.getScore());
                    total++;
                }
            }
        }

        System.out.println("Total : " + total + " alerte(s) !");

        System.out.println("\nExporter en CSV ? (o/n) :");
        if (scanner.nextLine().trim().equalsIgnoreCase("o")) {

            List<Resultat> flat = new ArrayList<>();
            for (List<Resultat> alertes : derniersResultats.values()) {
                flat.addAll(alertes);
            }
            new ExportCSV(flat).livrer();
            System.out.println("Export effectué !");
        }
    }

    public void genererConfig() {
        System.out.println("Configuration ");
        System.out.println("1. Comparateur : Exact");
        System.out.println("2. Comparateur : Levenshtein");
        System.out.println("3. Comparateur : JaroWinkler");
        System.out.println("Choisir le comparateur : ");

        String choix = scanner.nextLine().trim();
        switch (choix) {
            case "1":
                config.setComparateur(new Exact(new Normalizer()));
                System.out.println("Comparateur : Exact");
                break;
            case "2":
                config.setComparateur(new ComparateurNom() {
                    private final Levenshtein lev = new Levenshtein();
                    public double comparer(Nom n1, Nom n2) {
                        String s1 = String.join(" ", n1.getNomPretraite() != null ? n1.getNomPretraite() : List.of(n1.getNomOriginal().split("\\s+")));
                        String s2 = String.join(" ", n2.getNomPretraite() != null ? n2.getNomPretraite() : List.of(n2.getNomOriginal().split("\\s+")));
                        return lev.comparerChaine(s1, s2);
                    }
                });
                System.out.println("Comparateur : Levenshtein : ");
                break;
            case "3":
                config.setComparateur(new ComparateurNom() {
                    private final JaroWinkler jw = new JaroWinkler();

                    public double comparer(Nom n1, Nom n2) {
                        String s1 = String.join(" ", n1.getNomPretraite() != null ? n1.getNomPretraite() : List.of(n1.getNomOriginal().split("\\s+")));
                        String s2 = String.join(" ", n2.getNomPretraite() != null ? n2.getNomPretraite() : List.of(n2.getNomOriginal().split("\\s+")));
                        return jw.comparerChaine(s1, s2);
                    }
                });
                System.out.println("Comparateur : JaroWinkler");
                break;
            default:
                System.out.println("Coix invalide, comparateur inchangé !");
        }

        System.out.println("\n1. Sélection : ParSeuil");
        System.out.println("2. Sélection : Par N Premiers");
        System.out.println("3. Sélection : ParNPourcentage");
        System.out.println("Choisir la stratégie : ");
        choix = scanner.nextLine().trim();
        switch (choix) {
            case "1":
                System.out.println("Entrez le seuil :");
                double seuil = Double.parseDouble(scanner.nextLine().trim());
                config.setStrategie(new ParSeuil(seuil));
                System.out.println("Stratégie : ParSeuil(" + seuil +")");
                break;
            case "2":
                System.out.println("Entrez N:");
                int n = Integer.parseInt(scanner.nextLine().trim());
                config.setStrategie(new ParNPremier(n));
                System.out.println("Stratégie : ParNPremier(" + n +")");
                break;
            case "3":
                System.out.println("Entrez le pourcentage (ex: 20.0) :");
                double p = Double.parseDouble(scanner.nextLine().trim());
                config.setStrategie(new ParNPourcentage(p));
                System.out.println("Stratégie : ParNPourcentage(" + p + "%)");
                break;

            default:
                System.out.println("Choix invalide, stratégie inchnagée ");
        }

        System.out.println("\n1. Prétraitement : Normalizer");
        System.out.println("2. Prétraiement : LowerCase");
        System.out.println("3. Prétraitement : AccentRemover");
        System.out.println("Choisir le prétraitement");
        choix = scanner.nextLine().trim();
        switch (choix) {
            case "1":
                config.setPreTraiteur(new Normalizer());
                System.out.println("Prétraiement : Normalizer");
                break;
            case "2":
                config.setPreTraiteur(new LowerCase());
                System.out.println("Prétraiement : LowerCse");
                break;
            case "3":
                config.setPreTraiteur(new AccentRemover());
                System.out.println("Prétraiement : AccentRemover");
                break;
            default:
                System.out.println("Choix invalide, prétraitement inchangé ");
        }
        System.out.println("1. Générateur : Clé Phonétique");
        System.out.println("2. Générateur : Arbre Préfixe");
        System.out.println("3. Générateur : Prefixe Hash");
        System.out.println("Choisir le générateur :");
        choix = scanner.nextLine().trim();

        switch (choix) {
            case "1":
                config.setGenerateurType(Configuration.GenerateurType.PHONETIQUE);
                System.out.println("Générateur : Clé Phonétique");
                break;
            case "2":
                config.setGenerateurType(Configuration.GenerateurType.ARBRE);
                System.out.println("Générateur : Arbre Préfixe");
                break;
            case "3":
                config.setGenerateurType(Configuration.GenerateurType.PREFIX_HASH);
                System.out.println("Générateur : Préfixe Hash");
                break;
            default:
                System.out.println("Choix invalide, générateur inchangé");
        }
    }

    private void chargerListeClient() {
        System.out.println("Entrez le chemin du fichier CSV des clients:");
        String path = scanner.nextLine().trim();
        listManager.loadCSVAsClients(path);
        listManager.afficherStats();
    }

    private void chargerCSV() {
        System.out.println("Entrez le chemin du fichier CSV :");
        String path = scanner.nextLine().trim();
        listManager.loadCSV(path);
        listManager.afficherStats();
    }

    private void afficherFichierCSV(){
        List<String> fichiers = listManager.getFichiersCharges();
        if(fichiers == null ){
            System.out.println("Aucun fichier est chargé.");
        }

        System.out.println("Fichier CSV chargés :");

        for(int i =0; i<fichiers.size();i++){
            System.out.printf("  %d. %s%n", i + 1, fichiers.get(i));
        }

        System.out.printf("Total : %d fichier(s) | Clients : %d | Sanctions : %d%n", fichiers.size(),
                listManager.getListeClients().size(),
                listManager.getListeSanctions().size());
    }


    public void quitter() {
        System.out.println("Au revoir ");
        System.exit(0);
    }
}