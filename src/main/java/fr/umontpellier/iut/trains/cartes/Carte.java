package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

import java.util.List;

public abstract class Carte {
    private final String nom;
    private final int valeur;
    private final int cout;
    private final List<Type> typesCarte;
    private final String couleur;
    private final String effet;
    private int pv;

    /**
     * Constructeur simple
     * <p>
     * Important : La classe Carte est actuellement très incomplète. Vous devrez
     * ajouter des attributs et des méthodes et donc probablement définir au moins
     * un autre constructeur plus complet. Les sous-classes de Cartes qui vous sont
     * fournies font appel à ce constructeur simple mais au fur et à mesure que vous
     * les compléterez, elles devront utiliser les autres constructeurs de Carte. Si
     * vous n'utilisez plus ce constructeur, vous pouvez le supprimer.
     * 
     * @param nom Nom de la carte
     * @param valeur Valeur de la carte
     * @param cout Coût de la carte
     * @param typesCarte Liste des types de la carte
     * @param couleur Couleur de la carte
     * @param effet Effet de la carte
     */
    public Carte(String nom, int valeur, int cout, List<Type> typesCarte, String couleur, String effet) {
        this.nom = nom;
        this.valeur = valeur;
        this.cout = cout;
        this.typesCarte = typesCarte;
        this.couleur = couleur;
        this.effet = effet;
        this.pv = 0;
    }

    public Carte(String nom, int valeur, int cout, List<Type> typesCarte, String couleur) {
        this(nom, valeur, cout, typesCarte, couleur, "");
    }

    public Carte(String nom, int valeur, int cout, Type typeCarte) {
        this(nom, valeur, cout, List.of(typeCarte), typeCarte.getCouleur());
    }

    public Carte(String nom, int valeur, int cout, Type typeCarte, String effet) {
        this(nom, valeur, cout, List.of(typeCarte), typeCarte.getCouleur(), effet);
    }

    public Carte(String nom, int valeur, int cout, List<Type> typeCarte, String couleur, String effet, int pv) {
        this(nom, valeur, cout, typeCarte, couleur, effet);
        this.pv = pv;
    }

    public Carte(String nom, int valeur, int cout, Type typeCarte, String effet, int pv) {
        this(nom, valeur, cout, typeCarte, effet);
        this.pv = pv;
    }

    public String getNom() {
        return nom;
    }

    public int getValeur() {
        return valeur;
    }

    public int getCout() {
        return cout;
    }

    public Type getFirstType() {
        return typesCarte.get(0);
    }

    public List<Type> getTypesCarte(){
        return typesCarte;
    }

    public int getPv(){
        return pv;
    }

    /**
     * Cette fonction est exécutée lorsqu'un joueur joue la carte pendant son tour.
     * Toutes les cartes ont une méthode jouer, la carte est utilisée par défaut et l'argent est attribuée au joueur.
     * 
     * @param joueur le joueur qui joue la carte
     */
    public void jouer(Joueur joueur) {
        joueur.utiliserCarte(this);
        joueur.setArgent(joueur.getArgent() + valeur);
    }

    /**
     * Cette fonction est exécutée lorsqu'un joueur joue l'effet de la carte Action pendant son tour.
     * Toutes les cartes ont une méthode jouer, mais elle ne fait rien par défaut.
     *
     * @param joueur le joueur qui joue la carte
     * @param choix le choix du joueur utilisé pour l'action
     */
    public void jouer(Joueur joueur, String choix) {
        if(joueur.getSourceClone() != null && !joueur.getSourceClone().getNom().equals(choix))
            joueur.setSourceClone(null);    // On réinitialise la source clone seulement si le choix est différent de la source clone
    }

    /**
     * Cette fonction est exécutée lorsqu'un joueur achète la carte pendant son tour.
     * Toutes les cartes ont une méthode acheter, et l'argent est retirée au joueur par défaut.
     * @param joueur le joueur qui achète la carte
     */
    public void acheter(Joueur joueur) {
        joueur.setArgent(joueur.getArgent() - cout);
    }

    /**
     * Cette fonction permet de vérifier si le joueur peut jouer la carte courante
     *
     * @param joueur Le joueur concerné
     */
    public boolean peutJouer(Joueur joueur) {
        return !(typesCarte.contains(Type.VICTOIRE) || (typesCarte.contains(Type.FERRAILLE) && !joueur.getCartesEnJeu().isEmpty()));
    }

    /**
     * Cette fonction permet de vérifier si le joueur peut acheter la carte courante
     */
    public boolean peutAcheter(Joueur joueur) {
        return cout != 0 && joueur.getArgent() >= cout;
    }

    @Override
    public String toString() {
        return nom;
    }
}
