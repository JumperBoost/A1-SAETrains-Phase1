package fr.umontpellier.iut.trains.cartes;

public class Echangeur extends Carte {
    public Echangeur() {
        super("Échangeur", 1, 3, Type.ACTION, "Remettez une carte Train de votre zone de jeu sur le dessus de votre deck.");
    }
}
