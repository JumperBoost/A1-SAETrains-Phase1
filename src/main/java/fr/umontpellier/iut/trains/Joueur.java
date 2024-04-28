package fr.umontpellier.iut.trains;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import fr.umontpellier.iut.trains.cartes.Carte;
import fr.umontpellier.iut.trains.cartes.FabriqueListeDeCartes;
import fr.umontpellier.iut.trains.cartes.ListeDeCartes;
import fr.umontpellier.iut.trains.plateau.*;

public class Joueur {
    /**
     * Le jeu auquel le joueur est rattaché
     */
    private final Jeu jeu;
    /**
     * Booléen pour savoir si le tour du joueur est terminé
     */
    private boolean finTour;
    /**
     * Nom du joueur (pour les affichages console et UI)
     */
    private final String nom;
    /**
     * Quantité d'argent que le joueur a (remis à zéro entre les tours)
     */
    private int argent;
    /**
     * Nombre de points rails dont le joueur dispose. Ces points sont obtenus en
     * jouant les cartes RAIL (vertes) et remis à zéro entre les tours
     */
    private int pointsRails;
    /**
     * Nombre de points victoire bonus récupérée durant la partie. Ces points sont obtenus en
     * jouant les cartes TRAINS (bleus)
     */
    private int pvBonus;
    /**
     * Nombre de jetons rails disponibles (non placés sur le plateau)
     */
    private int nbJetonsRails;
    /**
     * Liste des cartes en main
     */
    private final ListeDeCartes main;
    /**
     * Liste des cartes dans la pioche (le début de la liste correspond au haut de la pile)
     */
    private final ListeDeCartes pioche;
    /**
     * Liste de cartes dans la défausse
     */
    private final ListeDeCartes defausse;
    /**
     * Liste des cartes en jeu (cartes jouées par le joueur pendant le tour)
     */
    private final ListeDeCartes cartesEnJeu;
    /**
     * Liste des cartes reçues pendant le tour
     */
    private final ListeDeCartes cartesRecues;
    /**
     * Carte action en cours d'utilisation
     * <p>
     * Permet de connaître la carte action en cours d'utilisation
     */
    private Carte carteAction;
    /**
     * Liste des choix possibles d'une action Action
     * <p>
     * Permet de lister et connaître la liste des choix possibles lors de l'utilisation en cours d'une carte Action
     */
    private final List<String> choixPossiblesAction;
    /**
     * Booléen pour autoriser un prompt vide
     * <p>
     * Permet de savoir si on peut utiliser un prompt vide lors de la demande d'un choix
     */
    private boolean peutPasser;
    /**
     * Couleur du joueur (utilisé par l'interface graphique)
     */
    private final CouleurJoueur couleur;


    // Attributs nécessaires pour gérer les effets des cartes

    private boolean surcoutAdversaires;
    private boolean surcoutRiviere;
    private boolean surcoutMontagne;
    private boolean surcoutVille;
    private boolean surcoutRail;
    private boolean recevoirFerraille;
    private boolean bonusFerronnerie;
    private Carte bonusTrainMatinal;

    public Joueur(Jeu jeu, String nom, CouleurJoueur couleur) {
        this.jeu = jeu;
        this.nom = nom;
        this.couleur = couleur;
        argent = 0;
        pointsRails = 0;
        pvBonus = 0;
        nbJetonsRails = 20;
        main = new ListeDeCartes();
        defausse = new ListeDeCartes();
        pioche = new ListeDeCartes();
        cartesEnJeu = new ListeDeCartes();
        cartesRecues = new ListeDeCartes();
        carteAction = null;
        choixPossiblesAction = new ArrayList<>();
        peutPasser = true;

        reinitialiserTour();

        // créer 7 Train omnibus (non disponibles dans la réserve)
        pioche.addAll(FabriqueListeDeCartes.creerListeDeCartes("Train omnibus", 7));
        // prendre 2 Pose de rails de la réserve
        for (int i = 0; i < 2; i++) {
            pioche.add(jeu.prendreDansLaReserve("Pose de rails"));
        }
        // prendre 1 Gare de la réserve
        pioche.add(jeu.prendreDansLaReserve("Gare"));

        // mélanger la pioche
        pioche.melanger();
        // Piocher 5 cartes en main
        // Remarque : on peut aussi appeler piocherEnMain(5) si la méthode est écrite
        for (int i = 0; i < 5; i++) {
            main.add(pioche.remove(0));
        }

    }

    public String getNom() {
        return nom;
    }

    public CouleurJoueur getCouleur() {
        return couleur;
    }

    /**
     * Renvoie le score total du joueur
     * <p>
     * Le score total est la somme des points obtenus par les effets suivants :
     * <ul>
     * <li>points de rails (villes et lieux éloignés sur lesquels le joueur a posé
     * un rail)
     * <li>points des cartes possédées par le joueur (cartes VICTOIRE jaunes)
     * <li>score courant du joueur (points marqués en jouant des cartes pendant la
     * partie p.ex. Train de tourisme)
     * </ul>
     * 
     * @return le score total du joueur
     */
    public int getScoreTotal() {
        int nb = 0;
        // Calcul des points via les stations et les rails
        for(Tuile tuile : jeu.getTuiles()) {
            if(tuile.getType() == TypeTuile.VILLE && tuile.hasRail(this)) {
                nb += (int) Math.pow(2, tuile.getNbGares());
            } else if(tuile.getType() == TypeTuile.ETOILE && tuile.hasRail(this)) {
                nb += ((TuileEtoile) tuile).getValeur();
            }
        }
        // Calcul des points via les cartes victoires
        for (Carte carte : getCartes()) {
            nb += carte.getPv();
        }
        // Ajout des points victoire bonus
        nb += pvBonus;
        return nb;
    }

    /**
     * Donne des points de victoire bonus au joueur
     *
     * @param pv nombre de points de victoire bonus à donner
     */
    public void donnePvBonus(int pv){
        pvBonus += pv;
    }

    /**
     * Transférer la défausse à la pioche si nécessaire
     * <p>
     * Si la pioche est vide, la méthode commence par mélanger
     * toute la défausse dans la pioche.
     */
    private void remplirPioche() {
        if(pioche.isEmpty()) {
            defausse.melanger();
            pioche.addAll(defausse);
            defausse.clear();
        }
    }

    /**
     * Récupère et renvoie la première carte de la pioche.
     * <p>
     * Si la pioche est vide, la méthode commence par mélanger toute la défausse
     * dans la pioche.
     *
     * @return la carte piochée ou {@code null} si aucune carte disponible
     */
    public Carte recuperer() {
        remplirPioche();
        return !pioche.isEmpty() ? pioche.get(0) : null;
    }

    /**
     * Retire et renvoie la première carte de la pioche.
     * <p>
     * Si la pioche est vide, la méthode commence par mélanger toute la défausse
     * dans la pioche.
     *
     * @return la carte piochée ou {@code null} si aucune carte disponible
     */
    public Carte piocher() {
        remplirPioche();
        return !pioche.isEmpty() ? pioche.remove(0) : null;
    }

    /**
     * Retire et renvoie les {@code n} premières cartes de la pioche.
     * <p>
     * Si à un moment il faut encore piocher des cartes et que la pioche est vide,
     * la défausse est mélangée et toutes ses cartes sont déplacées dans la pioche.
     * S'il n'y a plus de cartes à piocher la méthode s'interrompt et les cartes qui
     * ont pu être piochées sont renvoyées.
     * 
     * @param n nombre de cartes à piocher
     * @return une liste des cartes piochées (la liste peut contenir moins de n
     *         éléments si pas assez de cartes disponibles dans la pioche et la
     *         défausse)
     */
    public List<Carte> piocher(int n) {
        List<Carte> cartes = new ArrayList<>();
        Carte carte;
        for(int i = 0; i < n; i++) {
            carte = piocher();
            if(carte == null)
                break;
            cartes.add(carte);
        }
        return cartes;
    }

    /**
     * Retire la carte de la main et l'ajoute dans les cartes en jeu
     *
     * @param carte Une carte quelconque présent dans la main
     */
    public void utiliserCarte(Carte carte) {
        main.remove(carte);
        cartesEnJeu.add(carte);
    }

    /**
     * Définir la carte d'action
     *
     * @param carte Une carte d'action
     */
    public void setCarteAction(Carte carte) {
        viderChoixPossiblesActions();
        this.carteAction = carte;
    }

    public void ajouterChoixPossibleAction(String choixPossible) {
        this.choixPossiblesAction.add(choixPossible);
    }

    public void retirerChoixPossibleAction(String choixPossible) {
        this.choixPossiblesAction.remove(choixPossible);
    }

    public int getNbChoixPossiblesAction() {
        return this.choixPossiblesAction.size();
    }

    public void viderChoixPossiblesActions() {
        this.choixPossiblesAction.clear();
    }

    /**
     * Récupérer l'état de passement
     *
     * @return Un booléen permettant d'autoriser ou non un prompt vide
     */
    public boolean getPeutPasser() {
        return peutPasser;
    }

    /**
     * Définir l'état de passement
     *
     * @param peutPasser Un booléen permettant d'autoriser ou non un prompt vide
     */
    public void setPeutPasser(boolean peutPasser) {
        this.peutPasser = peutPasser;
    }

    /**
     * Joue un tour complet du joueur
     * <p>
     * Le tour du joueur se déroule en plusieurs étapes :
     * <ol>
     * <li>Initialisation
     * <p>
     * Dans ce jeu il n'y a rien de particulier à faire en début de tour à part un
     * éventuel affichage dans le log.
     * 
     * <li>Boucle principale
     * <p>
     * C'est le cœur de la fonction. Tant que le tour du joueur n'est pas terminé,
     * il faut préparer la liste de tous les choix valides que le joueur peut faire
     * (jouer des cartes, poser des rails, acheter des cartes, etc.), puis demander
     * au joueur de choisir une action (en appelant la méthode {@code choisir}).
     * <p>
     * Ensuite, en fonction du choix du joueur il faut exécuter l'action demandée et
     * recommencer la boucle si le tour n'est pas terminé.
     * <p>
     * Le tour se termine lorsque le joueur décide de passer (il choisit {@code ""})
     * ou lorsqu'il exécute une action qui termine automatiquement le tour (par
     * exemple s'il choisit de recycler toutes ses cartes Ferraille en début de
     * tour)
     * 
     * <li>Finalisation
     * <p>
     * Actions à exécuter à la fin du tour : réinitialiser les attributs
     * du joueur qui sont spécifiques au tour (argent, rails, etc.), défausser
     * toutes les
     * cartes, piocher 5 nouvelles cartes en main, etc.
     * </ol>
     */
    public void jouerTour() {
        // Initialisation
        jeu.log("<div class=\"tour\">Tour de " + toLog() + "</div>");
        // À FAIRE: compléter l'initialisation du tour si nécessaire (mais possiblement
        // rien de spécial à faire)

        // Boucle principale
        while (!finTour) {
            List<String> choixPossibles = new ArrayList<>();
            if(carteAction != null)
                choixPossibles.addAll(choixPossiblesAction);
            else {
                // Lister le choix des possibilités
                for (Carte carte : main) {
                    // Ajoute les noms de toutes les cartes possibles à jouer
                    if (carte.peutJouer(this))
                        choixPossibles.add(carte.getNom());
                }
                // Si aucune carte action est en cours, on autorise l'achat de cartes et de tuiles
                for (Map.Entry<String, ListeDeCartes> carte_reserve : jeu.getReserve().entrySet()) {
                    // Ajoute les noms des cartes dans la réserve préfixés de "ACHAT:"
                    if (!carte_reserve.getValue().isEmpty() && carte_reserve.getValue().get(0).peutAcheter(this))
                        choixPossibles.add("ACHAT:" + carte_reserve.getKey());
                }
                for(Tuile tuile : jeu.getTuiles())
                    if(tuile.hasRail(this))
                        for(Tuile voisin : tuile.getVoisines())
                            if(!(voisin.getType() == TypeTuile.MER) && !voisin.hasRail(this))
                                choixPossibles.add("TUILE:" + jeu.getTuiles().indexOf(voisin));

                // Ajoute les choix possibles d'une action Action passive
                if(bonusTrainMatinal != null)
                    choixPossibles.addAll(choixPossiblesAction);
            }

            // Choix de l'action à réaliser
            String choix = choisir(String.format("Tour de %s", this.nom), choixPossibles, null, peutPasser);

            // Exécuter l'action demandée par le joueur
            if(bonusTrainMatinal != null && carteAction == null)
                bonusTrainMatinal.jouer(this, choix);
            else if(carteAction == null) {
                executerChoix(choix);
            } else carteAction.jouer(this, choix);
        }
        // Finalisation
        // À FAIRE: compléter la finalisation du tour
        defausse.addAll(main);
        main.clear();
        defausse.addAll(cartesRecues);
        defausse.addAll(cartesEnJeu);
        cartesRecues.clear();
        cartesEnJeu.clear();
        main.addAll(piocher(5));

        reinitialiserTour();
    }

    private void reinitialiserTour() {
        // Réinitialiser les attributs spécifiques au tour
        finTour = false;
        pointsRails = 0;
        argent = 0;
        // Réinitialisation des attributs spécifiques nécessaires pour gérer les effets des cartes
        surcoutAdversaires = true;
        surcoutRiviere = true;
        surcoutMontagne = true;
        surcoutVille = true;
        surcoutRail = true;
        recevoirFerraille = true;
        bonusFerronnerie = false;
        bonusTrainMatinal = null;
    }

    public void executerChoix(String choix) {
        if (choix.startsWith("ACHAT:")) {
            // Prendre une carte dans la réserve
            String nomCarte = choix.split(":")[1];
            Carte carte = jeu.prendreDansLaReserve(nomCarte);
            if (carte != null) {
                log("Reçoit " + carte); // affichage dans le log
                cartesRecues.add(carte);
                carte.acheter(this);
            }
        } else if (choix.startsWith("TUILE:")) {
            if (pointsRails > 0) {
                // Poser un rail sur la tuile du plateau
                int tuile_index = Integer.parseInt(choix.split("TUILE:")[1]);
                Tuile tuile = jeu.getTuile(tuile_index);

                // Déterminer le coût supplémentaire
                int cout_supp = 0;
                if(surcoutRail) {
                    switch (tuile.getType()) {
                        case TERRAIN:
                            TypeTerrain type = ((TuileTerrain) tuile).getTypeTerrain();
                            if (type == TypeTerrain.FLEUVE) {
                                if (surcoutRiviere)
                                    cout_supp = 1;
                            } else if (type == TypeTerrain.MONTAGNE) {
                                if (surcoutMontagne)
                                    cout_supp = 2;
                            }
                            break;

                        case VILLE:
                            if (surcoutVille)
                                cout_supp = 1 + tuile.getNbGares();
                            break;

                        case ETOILE:
                            cout_supp = ((TuileEtoile) tuile).getValeur();
                            break;
                    }
                    // Ajouter le coût supplémentaire des rails des autres joueurs
                    if (surcoutAdversaires)
                        cout_supp += tuile.getRailsSize();
                }

                // Placer une gare dans une tuile si possible
                if (argent >= cout_supp) {
                    argent -= cout_supp;
                    if (recevoirFerraille && !tuile.estVide() && surcoutAdversaires)
                        main.add(jeu.prendreDansLaReserve("Ferraille"));
                    tuile.ajouterRail(this);
                    pointsRails--;
                    nbJetonsRails--;
                    log("Gare posé.");
                } else log("Vous n'avez pas assez d'argent nécessaire pour poser un rail sur cette tuile.");
            } else log("Vous n'avez pas de point de rail nécessaire pour cette tuile.");
        } else if (choix.isEmpty()) {
            // terminer le tour
            finTour = true;
        } else {
            // jouer une carte de la main
            Carte carte = main.retirer(choix);
            log("Joue " + carte); // affichage dans le log
            carte.jouer(this);  // exécuter l'action de la carte
        }
    }

    /**
     * Attend une entrée de la part du joueur (au clavier ou sur la websocket) et
     * renvoie le choix du joueur.
     * <p>
     * Cette méthode lit les entrées du jeu ({@code Jeu.lireligne()}) jusqu'à ce
     * qu'un choix valide (un élément de {@code choix} ou la valeur d'un élément de
     * {@code boutons} ou éventuellement la chaîne vide si l'utilisateur est
     * autorisé à passer) soit reçu.
     * Lorsqu'un choix valide est obtenu, il est renvoyé par la fonction.
     * <p>
     * Exemple d'utilisation pour demander à un joueur de répondre à une question
     * par "oui" ou "non" :
     * <p>
     * 
     * <pre>{@code
     * List<String> choix = Arrays.asList("oui", "non");
     * String input = choisir("Voulez-vous faire ceci ?", choix, null, false);
     * }</pre>
     * <p>
     * Si par contre on voulait proposer les réponses à l'aide de boutons, on
     * pourrait utiliser :
     * 
     * <pre>{@code
     * List<String> boutons = Arrays.asList(new Bouton("Oui !", "oui"), new Bouton("Non !", "non"));
     * String input = choisir("Voulez-vous faire ceci ?", null, boutons, false);
     * }</pre>
     * 
     * (ici le premier bouton a le label "Oui !" et envoie la String "oui" s'il est
     * cliqué, le second a le label "Non !" et envoie la String "non" lorsqu'il est
     * cliqué)
     *
     * <p>
     * <b>Remarque :</b> Normalement, si le paramètre {@code peutPasser} est
     * {@code false} le choix
     * {@code ""} n'est pas valide. Cependant s'il n'y a aucun choix proposé (les
     * listes {@code choix} et {@code boutons} sont vides ou {@code null}), le choix
     * {@code ""} est accepté pour éviter un blocage.
     * 
     * @param instruction message à afficher à l'écran pour indiquer au joueur la
     *                    nature du choix qui est attendu
     * @param choix       une collection de chaînes de caractères correspondant aux
     *                    choix valides attendus du joueur (ou {@code null})
     * @param boutons     une liste d'objets de type {@code Bouton} définis par deux
     *                    chaînes de caractères (label, valeur) correspondant aux
     *                    choix valides attendus du joueur qui doivent être
     *                    représentés par des boutons sur l'interface graphique (le
     *                    label est affiché sur le bouton, la valeur est ce qui est
     *                    envoyé au jeu quand le bouton est cliqué) ou {@code null}
     * @param peutPasser  booléen indiquant si le joueur a le droit de passer sans
     *                    faire de choix. S'il est autorisé à passer, c'est la
     *                    chaîne de caractères vide ({@code ""}) qui signifie qu'il
     *                    désire passer.
     * @return le choix de l'utilisateur (un élement de {@code choix}, ou la valeur
     *         d'un élément de {@code boutons} ou la chaîne vide)
     */
    public String choisir(
            String instruction,
            Collection<String> choix,
            List<Bouton> boutons,
            boolean peutPasser) {
        if (choix == null)
            choix = new ArrayList<>();
        if (boutons == null)
            boutons = new ArrayList<>();

        HashSet<String> choixDistincts = new HashSet<>(choix);
        choixDistincts.addAll(boutons.stream().map(Bouton::valeur).toList());
        if (peutPasser || choixDistincts.isEmpty()) {
            // si le joueur a le droit de passer ou s'il n'existe aucun choix valide, on
            // ajoute "" à la liste des choix possibles
            choixDistincts.add("");
        }

        String entree;
        // Lit l'entrée de l'utilisateur jusqu'à obtenir un choix valide
        while (true) {
            jeu.prompt(instruction, boutons, peutPasser);
            entree = jeu.lireLigne();
            // si une réponse valide est obtenue, elle est renvoyée
            if (choixDistincts.contains(entree)) {
                return entree;
            }
        }
    }

    /**
     * Ajoute un message dans le log du jeu
     * 
     * @param message message à ajouter dans le log
     */
    public void log(String message) {
        jeu.log(message);
    }

    @Override
    public String toString() {
        // Vous pouvez modifier cette fonction comme bon vous semble pour afficher
        // d'autres informations si nécessaire
        StringJoiner joiner = new StringJoiner("\n");
        joiner.add(String.format("=== %s (%d pts) ===", nom, getScoreTotal()));
        joiner.add(String.format("  Argent: %d  Rails: %d", argent, pointsRails));
        joiner.add("  Cartes en jeu: " + cartesEnJeu);
        joiner.add("  Cartes reçues: " + cartesRecues);
        joiner.add("  Cartes en main: " + main);
        return joiner.toString();
    }

    /**
     * Renvoie la liste de toutes les cartes possédées par le joueur
     * <p>
     * Renvoie une liste contenant les cartes de la pioche, de la main et de la défausse du joueur
     *
     * @return une liste de toutes les cartes possédées par le joueur
     */
    public List<Carte> getCartes() {
        List<Carte> cartes = new ArrayList<>();
        cartes.addAll(pioche);
        cartes.addAll(main);
        cartes.addAll(defausse);
        cartes.addAll(cartesEnJeu);
        cartes.addAll(cartesRecues);
        return cartes;
    }

    public ListeDeCartes getMain() {
        return main;
    }

    public ListeDeCartes getPioche() {
        return pioche;
    }

    public ListeDeCartes getDefausse() {
        return defausse;
    }

    public ListeDeCartes getCartesEnJeu() {
        return cartesEnJeu;
    }

    public ListeDeCartes getCartesRecues() {
        return cartesRecues;
    }

    /**
     * @return une représentation du joueur pour l'affichage dans le log du jeu
     */
    public String toLog() {
        return String.format("<span class=\"joueur %s\">%s</span>", couleur.toString(), nom);
    }

    /**
     * @return une représentation du joueur sous la forme d'un dictionnaire de
     *         valeurs sérialisables (qui sera converti en JSON pour l'envoyer à
     *         l'interface graphique)
     */
    Map<String, Object> dataMap() {
        return Map.ofEntries(
                Map.entry("nom", nom),
                Map.entry("couleur", couleur),
                Map.entry("scoreTotal", getScoreTotal()),
                Map.entry("argent", argent),
                Map.entry("rails", pointsRails),
                Map.entry("nbJetonsRails", nbJetonsRails),
                Map.entry("main", main.dataMap()),
                Map.entry("defausse", defausse.dataMap()),
                Map.entry("cartesEnJeu", cartesEnJeu.dataMap()),
                Map.entry("cartesRecues", cartesRecues.dataMap()),
                Map.entry("pioche", pioche.dataMap()),
                Map.entry("actif", jeu.getJoueurCourant() == this));
    }

    public void setFinTour(boolean finTour) {
        this.finTour = finTour;
    }

    public int getArgent() {
        return argent;
    }

    public void setArgent(int argent) {
        this.argent = argent;
    }

    public int getNbJetonsRails(){
        return nbJetonsRails;
    }

    public Jeu getJeu() {
        return jeu;
    }

    public void incrementerPointsRails(int points) {
        pointsRails += points;
    }

    /*
     * Méthodes pour gérer les effets des cartes
     */

    public void setSurcoutAdversaires(boolean surcoutAdversaires) {
        this.surcoutAdversaires = surcoutAdversaires;
    }

    public void setSurcoutRiviere(boolean surcoutRiviere) {
        this.surcoutRiviere = surcoutRiviere;
    }

    public void setSurcoutMontagne(boolean surcoutMontagne) {
        this.surcoutMontagne = surcoutMontagne;
    }

    public void setSurcoutVille(boolean surcoutVille) {
        this.surcoutVille = surcoutVille;
    }

    public void setSurcoutRail(boolean surcoutRail) {
        this.surcoutRail = surcoutRail;
    }

    public boolean getRecevoirFerraille() {
        return recevoirFerraille;
    }

    public void setRecevoirFerraille(boolean recevoirFerraille) {
        this.recevoirFerraille = recevoirFerraille;
    }

    public boolean getBonusFerronnerie() {
        return bonusFerronnerie;
    }

    public void setBonusFerronnerie(boolean bonusFerronnerie) {
        this.bonusFerronnerie = bonusFerronnerie;
    }

    public void setBonusTrainMatinal(Carte carteTrainMatinal) {
        this.bonusTrainMatinal = carteTrainMatinal;
    }
}
