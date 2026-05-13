# Moteur de Correspondance de Noms KYC

Un projet Java implémentant un moteur de recherche et de correspondance de noms applicable dans tout contexte nécessitant la vérification d'identité à partir de listes de référence. Bien que la recherche de noms soit une problématique générale — utile dans les bases de données, les systèmes d'archives, ou la gestion de doublons — ce projet se concentre sur le cas d'usage KYC *(Know Your Customer)* et AML *(Anti-Money Laundering)*, où cette fonctionnalité est critique.

L'objectif est de vérifier si un nom donné figure dans un ou plusieurs fichiers de sanctions ou listes de surveillance, fournis sous forme de fichiers CSV. La taille potentiellement importante de ces listes rend la performance et la pertinence des résultats essentielles.

---

## Contexte

Dans le secteur bancaire et financier, les établissements doivent vérifier leurs clients avant d'ouvrir des comptes ou de fournir des services. Ce processus permet de détecter les personnes susceptibles d'apparaître sur des listes de sanctions, des listes de personnes politiquement exposées (PPE), ou d'autres listes de conformité.

Une simple comparaison exacte ne suffit pas, car les noms peuvent être écrits différemment. Par exemple, des accents, des variantes orthographiques, des espaces manquants ou des erreurs de frappe peuvent faire paraître deux noms similaires comme différents. Ce projet utilise donc un pipeline de correspondance modulaire capable de prendre en charge des méthodes de comparaison exactes et approximatives.

---

## Objectif du projet

Ce projet vise à construire un moteur de correspondance de noms flexible capable de :

- Comparer des noms de clients avec des listes de contrôle
- Calculer des scores de similarité
- Sélectionner les correspondances les plus pertinentes
- Restituer les résultats dans un format lisible

Le système est conçu autour de deux cas d'usage principaux. Le premier est la **vérification en temps réel**, où un agent contrôle rapidement un nom de client. Le second est la **vérification par lot**, où une base de données complète de clients est comparée aux listes de contrôle sélectionnées.

---

## Architecture

Le projet suit une structure en pipeline :

```
Chargement des données
→ Prétraitement
→ Génération de candidats
→ Comparaison des noms
→ Sélection des correspondances
→ Restitution des résultats
```

Chaque étape est isolée dans son propre composant, ce qui facilite l'extension et la maintenance du projet.

---

## Composants principaux

```
KYC-Name-Matching-Engine/
│
├── app/                          # Couche applicative principale
│   ├── Main.java                 # Point d'entrée du programme
│   ├── Menu.java                 # Interface utilisateur en ligne de commande
│   ├── Configuration.java        # Paramètres globaux du moteur
│   ├── MoteurDeRecherche.java    # Orchestre l'ensemble du pipeline
│   ├── ListManager.java          # Gestion du chargement des listes CSV
│   ├── GenerateurCandidat.java   # Présélection des noms avant comparaison
│   ├── GenerateurBrut.java       # Génération de candidats sans filtrage
│   ├── GenerateurArbre.java      # Génération via index en arbre
│   ├── GenerateurPrefixHash.java # Génération via index de préfixes hashés
│   ├── IndexPhonetique.java      # Index basé sur la clé phonétique
│   ├── IndexPrefixArbre.java     # Index par préfixe (structure arbre)
│   ├── IndexPrefixHash.java      # Index par préfixe (structure hash)
│   └── ClePhonetique.java        # Calcul de la clé phonétique d'un nom
│
├── comparateurs/                 # Algorithmes de comparaison (score entre 0.0 et 1.0)
│   ├── ComparateurNom.java       # Interface commune des comparateurs
│   ├── ComparateurChaine.java    # Comparaison générique de chaînes
│   ├── Exact.java                # Correspondance stricte caractère par caractère
│   ├── JaroWinkler.java          # Similarité adaptée aux noms propres
│   └── Levenshtein.java          # Distance d'édition entre deux chaînes
│
├── pretraiteurs/                 # Normalisation des noms avant comparaison
│   ├── PreTraiteurNom.java       # Interface commune des prétraiteurs
│   ├── Normalizer.java           # Chaîne de prétraitements combinés
│   ├── AccentRemover.java        # Suppression des accents
│   ├── LowerCase.java            # Conversion en minuscules
│   ├── SupprimerPonct.java       # Suppression de la ponctuation
│   └── NGram.java                # Découpage en N-grammes
│
├── selectionneurs/               # Stratégies de sélection des résultats (Pattern Strategy)
│   ├── SelectionMatching.java    # Interface commune des stratégies
│   ├── ParSeuil.java             # Garde les résultats au-dessus d'un seuil
│   ├── ParNPremier.java          # Garde les N meilleures correspondances
│   └── ParNPourcentage.java      # Garde un pourcentage des meilleurs résultats
│
├── livreurs/                     # Restitution des résultats
│   ├── LivreurResultat.java      # Interface commune des livreurs
│   ├── AfficherConsole.java      # Affichage dans le terminal
│   └── ExportCSV.java            # Export vers un fichier CSV
│
├── model/                        # Objets de données métier
│   ├── Nom.java                  # Représente un nom de personne
│   ├── CoupleValeur.java         # Nom + score de similarité associé
│   └── Resultat.java             # Correspondance finale (client ↔ nom sanctionné)
│
└── csvfiles/                     # Listes de surveillance (données de test)
    ├── peps_names_1k.csv
    ├── peps_names_2k.csv
    ├── peps_names_8k.csv
    ├── peps_names_16k.csv
    ├── peps_names_32k.csv
    └── peps_names_512k.csv
```

---

## Format CSV

Le format CSV attendu est simple :

```csv
id,name
NK-001,John Doe
NK-002,Ahmed Ben Ali
```

---

## Exemple de fonctionnement

Au lancement, l'utilisateur interagit via un menu en ligne de commande :

```
Moteur De Recherche De Noms
0. Charger un fichier CSV des sanctions
1. Ajouter liste de clients
2. Ajouter une liste de sanctions
3. Lancer le pipeline
4. Afficher le rapport
5. Gérer la configuration
6. Voir les fichiers CSV chargés
7. Réinitialiser les listes
8. Quitter
```

Un flux d'utilisation typique :

**1. Charger les données** — option `0` pour charger un fichier CSV de sanctions, option `1` pour charger une liste de clients.

**2. Configurer le moteur** — option `5` permet de choisir :
- le **comparateur** (Exact, Levenshtein, JaroWinkler)
- la **stratégie de sélection** (ParSeuil, ParNPremier, ParNPourcentage)
- les **prétraitements** à appliquer (LowerCase, AccentRemover, NGram, SupprimerPonct...)
- le **générateur de candidats** (Phonétique, Arbre, Préfixe Hash, Brut)
- le **livreur** (Console ou CSV)

**3. Lancer le pipeline** — option `3` exécute la recherche et affiche les statistiques :

```
********** STATISTIQUES *********
Temps de prétraitement  : 12 ms
Total clients vérifiés  : 150
Clients avec matching   : 43
Client sans matching    : 107
Score moyen             : 87.4%
Temps total de pipeline : 340 ms
*********************************
```

**4. Consulter le rapport** — option `4` affiche le détail des alertes et propose un export CSV.

---

## Objectifs de conception

Ce projet s'attache à créer une architecture orientée objet propre. L'objectif principal est de séparer clairement les responsabilités afin que les algorithmes, les stratégies de sélection, la gestion des données et la restitution des résultats puissent évoluer indépendamment.

Le système est également conçu dans un souci de performance, en particulier pour les grands fichiers où une comparaison naïve serait trop lente.