package fr.umontpellier.iut.trains;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import fr.umontpellier.iut.trains.cartes.Carte;
import fr.umontpellier.iut.trains.cartes.FabriqueListeDeCartes;
import fr.umontpellier.iut.trains.cartes.ListeDeCartes;
import fr.umontpellier.iut.trains.plateau.Plateau;
import fr.umontpellier.iut.trains.plateau.Tuile;

public class Jeu implements Runnable {
    /**
     * Liste des joueurs
     */
    private ArrayList<Joueur> joueurs;
    /**
     * Joueur dont c'est le tour
     */
    private Joueur joueurCourant;
    /**
     * Dictionnaire des piles de réserve.
     * <p>
     * Associe à un nom de carte la liste des cartes correspondantes disponibles
     * dans la réserve
     */
    private Map<String, ListeDeCartes> reserve;
    /**
     * Liste des cartes écartées par les joueurs au cours de la partie
     */
    private ListeDeCartes cartesEcartees;
    /**
     * Nom de la ville du plateau (pour afficher le plateau dans l'interface
     * graphique)
     */
    private String nomVille;
    /**
     * Tuiles du plateau de jeu (indexées dans l'ordre de lecture)
     */
    private List<Tuile> tuiles;
    /**
     * Nombre de jetons Gare restant (non placés sur les tuiles)
     */
    private int nbJetonsGare;
    /**
     * Scanner pour lire les entrées clavier
     * <p>
     * ATTENTION: cet attribut ne doit pas être utilisé directement. Il faut
     * toujours utiliser la méthode {@code lireLigne} (ou les méthodes qui
     * l'appellent telles que {@code Joueur.choisir}) pour lire les instructions des
     * joueurs afin que l'interface graphique et les tests puissent fonctionner
     * correctement.
     */
    private Scanner scanner;
    /**
     * Messages d'information du jeu (affichés dans l'interface graphique)
     */
    private final List<String> log;
    /**
     * Instruction affichée au joueur courant
     */
    private String instruction;
    /**
     * Liste des boutons à afficher dans l'interface
     */
    private List<Bouton> boutons;

    /**
     * Constructeur de la classe Jeu
     * 
     * @param nomsJoueurs       noms des joueurs de la partie
     * @param cartesPreparation noms des cartes à utiliser pour créer les piles de
     *                          réserve (autres que les piles de cartes communes)
     * @param plateau           choix du plateau ({@code Plateau.OSAKA} ou
     *                          {@code Plateau.TOKYO})
     */
    public Jeu(String[] nomsJoueurs, String[] cartesPreparation, Plateau plateau) {
        // initialisation des entrées/sorties
        scanner = new Scanner(System.in);
        log = new ArrayList<>();

        // préparation du plateau
        this.nomVille = plateau.getNomVille();
        this.tuiles = plateau.makeTuiles();

        this.nbJetonsGare = 30;
        this.cartesEcartees = new ListeDeCartes();

        // construction des piles de réserve
        this.reserve = new HashMap<>();

        // ajouter les cartes communes et les cartes de préparation
        creerCartesCommunes();
        for (String nomCarte : cartesPreparation) {
            reserve.put(nomCarte, FabriqueListeDeCartes.creerListeDeCartes(nomCarte, 10));
        }

        // création des joueurs
        this.joueurs = new ArrayList<>();
        ArrayList<CouleurJoueur> couleurs = new ArrayList<>(List.of(CouleurJoueur.values()));
        Collections.shuffle(couleurs);
        for (String nomJoueur : nomsJoueurs) {
            this.joueurs.add(new Joueur(this, nomJoueur, couleurs.remove(0)));
        }
        this.joueurCourant = joueurs.get(0);
    }

    public Joueur getJoueurCourant() {
        return joueurCourant;
    }

    public List<Tuile> getTuiles() {
        return tuiles;
    }

    public Tuile getTuile(int index) {
        return tuiles.get(index);
    }

    public Map<String, ListeDeCartes> getReserve() {
        return reserve;
    }

    /**
     * Renvoie un ensemble de tous les noms des cartes en jeu.
     * 
     * Cette liste contient les noms des cartes qui étaient disponibles dans les
     * piles la réserve et "Train omnibus" que les joueurs ont en main en début de
     * partie mais ne correspond pas à une pile de la réserve.
     */
    public Set<String> getListeNomsCartes() {
        Set<String> noms = new HashSet<>(reserve.keySet());
        noms.add("Train omnibus");
        return noms;
    }

    /**
     * Construit les piles de réserve pour les cartes communes
     */
    private void creerCartesCommunes() {
        reserve.put("Train express", FabriqueListeDeCartes.creerListeDeCartes("Train express", 20));
        reserve.put("Train direct", FabriqueListeDeCartes.creerListeDeCartes("Train direct", 10));
        reserve.put("Pose de rails", FabriqueListeDeCartes.creerListeDeCartes("Pose de rails", 20));
        reserve.put("Gare", FabriqueListeDeCartes.creerListeDeCartes("Gare", 20));
        reserve.put("Appartement", FabriqueListeDeCartes.creerListeDeCartes("Appartement", 10));
        reserve.put("Immeuble", FabriqueListeDeCartes.creerListeDeCartes("Immeuble", 10));
        reserve.put("Gratte-ciel", FabriqueListeDeCartes.creerListeDeCartes("Gratte-ciel", 10));
        reserve.put("Ferraille", FabriqueListeDeCartes.creerListeDeCartes("Ferraille", 70));
    }

    /**
     * Renvoie une carte prise dans la réserve
     * 
     * @param nomCarte nom de la carte à prendre
     * @return la carte retirée de la réserve ou `null` si aucune disponible (ou si
     *         le nom de carte n'existe pas dans la réserve)
     */
    public Carte prendreDansLaReserve(String nomCarte) {
        if (!reserve.containsKey(nomCarte)) {
            return null;
        }

        ListeDeCartes pile = reserve.get(nomCarte);
        if (pile.isEmpty()) {
            return null;
        }
        return pile.remove(0);
    }

    /**
     * Modifie l'attribut {@code joueurCourant} pour passer au joueur suivant dans
     * l'ordre du tableau {@code joueurs} (le tableau est considéré circulairement)
     */
    public void passeAuJoueurSuivant() {
        int i = joueurs.indexOf(joueurCourant);
        i = (i + 1) % joueurs.size();
        joueurCourant = joueurs.get(i);
    }

    /**
     * Démarre la partie et exécute les tours des joueurs jusqu'à ce que la partie
     * soit terminée
     */
    public void run() {
        // initialisation (chaque joueur choisit une position de départ)
        // À FAIRE: compléter la partie initialisation

        // tours des joueurs jusqu'à une condition de fin
        while (!estFini()) {
            joueurCourant.jouerTour();
            passeAuJoueurSuivant();
        }

        // fin de la partie
        log("<div class=\"tour\">Fin de la partie</div>");
        for (Joueur j : joueurs) {
            log(String.format("%s : %d points", j.toLog(), j.getScoreTotal()));
        }
        prompt("Fin de la partie.", null, true);
    }

    /**
     * @return {@code true} si la partie est finie, {@code false} sinon
     */
    public boolean estFini() {
        if (nbJetonsGare==0){return true;}
        int nbvide = 0;

        for (int i=0; i<reserve.size(); i++) {
            if (reserve.get(i).isEmpty()) {
                nbvide += 1;
            }
            if (nbvide>=4) {
                return true;
            }
        }
        /*
        for (Map pile : reserve) {
            if (pile.isEmpty()) {
                nbvide += 1;
            }
            if (nbvide==4){return true;}
        }*/
        // Dernière vérification ; Dépend du nombre de jetons rails posé par les joueurs
        if(false){}
        // À FAIRE : réécrire cette méthode
        return false;
        }
    }

    /**
     * Ajoute un message au log du jeu
     */
    public void log(String message) {
        log.add(message);
    }

    /**
     * Envoie l'état de la partie pour affichage aux joueurs avant de faire un choix
     *
     * @param instruction l'instruction qui est donnée au joueur
     * @param boutons     boutons pour les choix qui ne sont pas disponibles
     *                    autrement (ou {@code null})
     * @param peutPasser  indique si le joueur peut passer sans faire de choix
     */
    public void prompt(String instruction, List<Bouton> boutons, boolean peutPasser) {
        if (boutons == null) {
            boutons = new ArrayList<>();
        }

        this.instruction = instruction;
        this.boutons = boutons;

        System.out.println();
        System.out.println(this);
        if (boutons.isEmpty()) {
            System.out.printf(">>> %s: %s <<<\n", joueurCourant.getNom(), instruction);
        } else {
            StringJoiner joiner = new StringJoiner(" / ");
            for (Bouton bouton : boutons) {
                joiner.add(bouton.toString());
            }
            System.out.printf(">>> %s: %s [%s] <<<\n", joueurCourant.getNom(), instruction, joiner);
        }
    }

    /**
     * Lit une ligne de l'entrée standard
     * 
     * ATTENTION: Pour que l'interface graphique et les tests fonctionnent
     * correctement il faut toujours utiliser cette méthode pour lire l'entrée
     * clavier et non pas appeler directement les méthodes de {@code scanner} (c'est
     * en particulier ce que fait la méthode {@code Player.choisir})
     *
     * @return le prochain input de l'utilisateur
     */
    public String lireLigne() {
        return scanner.nextLine();
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner("\n");

        StringJoiner reserveJoiner = new StringJoiner(", ");
        for (String nomCarte : reserve.keySet()) {
            reserveJoiner.add(String.format("%s (%d)", nomCarte, reserve.get(nomCarte).size()));
        }
        joiner.add("=== Réserve ===\n" + reserveJoiner + "\n");
        joiner.add(joueurCourant.toString() + "\n");
        return joiner.toString();
    }

    /**
     * @return une représentation du jeu sous la forme d'un dictionnaire de
     *         valeurs sérialisables (qui sera converti en JSON pour l'envoyer à
     *         l'interface
     *         graphique)
     */
    public Map<String, Object> dataMap() {
        // liste des données des piles de réserve
        List<Map<String, Object>> listeReserve = new ArrayList<>();
        List<String> nomsCartesEnReserve = reserve.keySet().stream().sorted().collect(Collectors.toList());
        // piles de cartes communes en premier
        for (String nomCarte : FabriqueListeDeCartes.getNomsCartesCommunes()) {
            if (nomsCartesEnReserve.contains(nomCarte)) {
                listeReserve.add(Map.of("carte", nomCarte, "nombre", reserve.get(nomCarte).size()));
                nomsCartesEnReserve.remove(nomCarte);
            }
        }
        // autres piles de réserve après, par ordre alphabétique
        for (String nomCarte : nomsCartesEnReserve) {
            listeReserve.add(Map.of("carte", nomCarte, "nombre", reserve.get(nomCarte).size()));
        }

        return Map.ofEntries(
                Map.entry("joueurs", joueurs.stream().map(Joueur::dataMap).toList()),
                Map.entry("joueurCourant", joueurs.indexOf(joueurCourant)),
                Map.entry("instruction", instruction),
                Map.entry("boutons", boutons),
                Map.entry("ville", nomVille),
                Map.entry("tuiles", tuiles.stream().map(Tuile::dataMap).toList()),
                Map.entry("log", log),
                Map.entry("reserve", listeReserve));
    }
}
