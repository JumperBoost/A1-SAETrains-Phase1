package fr.umontpellier.iut.trains.cartes;

public class Gare extends Carte {
    public Gare() {
        super("Gare", 0, 3, Type.GARE, "Cette carte vous permet d'ajouter une station sur une des villes du plateau, mais rejette 1 carte féraille.");
    }
}
