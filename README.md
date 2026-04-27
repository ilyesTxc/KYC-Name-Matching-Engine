KYC Name Matching Engine :

A Java project that simulates a client screening system used in KYC and AML compliance.

The goal of the application is to help a bank verify whether a client name appears in one or more sanctions or watchlist files. These lists are provided as CSV files and may contain a large number of records, which makes performance and result relevance important.

Context

In banking and financial services, institutions must verify clients before opening accounts or providing services. This process helps detect people who may appear on sanctions lists, politically exposed person lists, or other compliance watchlists.

A simple exact comparison is not enough, because names can be written differently. For example, accents, spelling variations, missing spaces, or typing errors can make two similar names appear different. This project therefore uses a modular matching pipeline that can support exact and fuzzy comparison methods.

Project Objective

This project aims to build a flexible name matching engine that can compare client names against control lists, calculate similarity scores, select the most relevant matches, and deliver the results in a readable format.

The system is designed around two main use cases. The first is real-time verification, where an agent checks one client name quickly. The second is batch verification, where a full client database is compared against selected control lists.

Architecture

The project follows a pipeline structure.

Data loading
→ Preprocessing
→ Candidate generation
→ Name comparison
→ Match selection
→ Result delivery

Each step is separated into its own component, which makes the project easier to extend and maintain.

Main Components

The kyc.model package contains the main data objects used by the system. Nom represents a person name, CoupleValeur represents a name associated with a similarity score, and Resultat represents a final match between a client and a sanctioned name.

The kyc.collections package provides custom collection classes used to store names, candidates, and results. These classes wrap Java collections such as lists, sets, and queues depending on the role of the data.

The kyc.comparateurs package contains the comparison logic. It is designed to support several algorithms such as exact matching, Levenshtein distance, Jaro-Winkler similarity, and N-Gram comparison. These algorithms return a similarity score between 0.0 and 1.0.

The candidate generator reduces the number of comparisons by selecting only relevant names before running the comparison algorithms. This is important because comparing every client against every sanctioned name can become expensive with large CSV files.

The kyc.selectionneurs package decides which results should be kept. A selection strategy can keep results above a threshold, keep only the top matches, or keep a percentage of the best results.

The kyc.livreurs package is responsible for delivering the final results. Results can be displayed in the console or exported to a CSV file. This keeps the output logic separate from the matching logic.

The MoteurDeRecherche class acts as the central engine. It coordinates the complete pipeline from loading data to delivering the final results.

CSV Format

The expected CSV format is simple.

id,name
NK-001,John Doe
NK-002,Ahmed Ben Ali
Example Workflow

A user enters a client name such as Ahmed Ben Ali.

The system loads the selected watchlists, preprocesses the names, generates possible candidates, compares the client name with those candidates, keeps the most relevant matches, and finally displays or exports the result.

A possible output would be:

Search: Ahmed Ben Ali

Match found:
Client: Ahmed Ben Ali
Matched name: Ahmed Benali
Source: sanctions_onu.csv
Confidence: 93%
Design Goals

This project focuses on creating a clean object-oriented architecture. The main design goal is to separate responsibilities clearly so that algorithms, selection strategies, data handling, and result delivery can evolve independently.

The system is also designed with performance in mind, especially for large files where naive comparison would be too slow.
