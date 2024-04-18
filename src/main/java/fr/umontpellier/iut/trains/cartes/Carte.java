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
        // On applique les actions par défaut seulement si aucune carte action n'est en cours d'utilisation
        if(joueur.getCarteAction() == null) {
            joueur.utiliserCarte(this);
            joueur.setArgent(joueur.getArgent() + valeur);
        }
    }

    /**
     * Cette fonction est exécutée lorsqu'un joueur joue l'effet de la carte Action pendant son tour.
     * Toutes les cartes ont une méthode jouer, mais elle ne fait rien par défaut.
     *
     * @param joueur le joueur qui joue la carte
     * @param nomCarte le nom de la carte utilisée pour l'action
     */
    public void jouer(Joueur joueur, String nomCarte) {

    }

    /**
     * Cette fonction permet de vérifier si le joueur peut jouer la carte courante
     *
     * @param joueur Le joueur concerné
     */
    public boolean peutJouer(Joueur joueur) {
        return !(typesCarte.contains(Type.VICTOIRE) || typesCarte.contains(Type.FERAILLE));
    }

    /**
     * Cette fonction permet de vérifier si le joueur peut acheter la carte courante
     */
    public boolean peutAcheter(Joueur joueur) {
        return joueur.getArgent() >= cout;
    }

    @Override
    public String toString() {
        return nom;
    }
}
