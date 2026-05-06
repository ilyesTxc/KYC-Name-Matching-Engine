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
import kyc.selectionneurs.ParNPremier;
import kyc.selectionneurs.ParSeuil;

import java.util.List;
import java.util.Scanner;

public class Menu {
    private Configuration config;
    private ListManager listManager;
    private List<Resultat> derniersResultats;
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
            System.out.println("0. Charger un fichier CSV");
            System.out.println("1. Ajouter un client");
            System.out.println("2. Ajouter une sanction");
            System.out.println("3. Lancer le pipeline");
            System.out.println("4. Afficher le rapport");
            System.out.println("5. Gérer la configuration");
            System.out.println("6. Quitter");
            System.out.println("Votre chois : ");

            String choix = scanner.nextLine().trim();

            switch (choix) {
                case "0":
                    chargerCSV();
                    break;
                case "1":
                    ajouter(demanderNom(), listManager.getListeClients());
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

    private void lancerPipeline() {
        MoteurDeRecherche moteur = new MoteurDeRecherche(config, listManager, null);
        derniersResultats = moteur.lancerPipeline();
        if (derniersResultats != null && !derniersResultats.isEmpty()) {
            new AfficherConsole(derniersResultats).livrer();
        }
    }

    public void rapport() {
        if (derniersResultats == null || derniersResultats.isEmpty()) {
            System.out.println("Aucun résultat disponible. Lancez le pipeline d'abord. ");
            return;
        }
        System.out.println("Rapport des alertes : ");
        for (Resultat r : derniersResultats) {
            System.out.printf("Client: %-20s | Sanctions: %-20s | Score: %.2f%n", r.getNomClient().getNomOriginal(), r.getNomSanction().getNomOriginal(), r.getScore());
        }
        System.out.println("Toltal : " + derniersResultats.size() + "alerte(s) !");

        System.out.println("\nExporter en CSV ? (o/n) :");
        if (scanner.nextLine().trim().equalsIgnoreCase("o")) {
            ExportCSV export = new ExportCSV(derniersResultats);
            export.livrer();
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
    }

    private void chargerCSV() {
        System.out.println("Entrez le chemin du fichier CSV :");
        String path = scanner.nextLine().trim();
        listManager.loadCSV(path);
        listManager.afficherStats();
    }

    public void quitter() {
        System.out.println("Au revoir ");
        System.exit(0);
    }
}