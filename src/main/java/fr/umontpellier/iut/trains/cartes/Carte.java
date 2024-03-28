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

    public String getNom() {
        return nom;
    }    

    /**
     * Cette fonction est exécutée lorsqu'un joueur joue la carte pendant son tour.
     * Toutes les cartes ont une méthode jouer, mais elle ne fait rien par défaut.
     * 
     * @param joueur le joueur qui joue la carte
     */
    public void jouer(Joueur joueur) {
    }

    @Override
    public String toString() {
        return nom;
    }
}
