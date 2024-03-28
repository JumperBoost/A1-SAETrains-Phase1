package fr.umontpellier.iut.trains.cartes;

public class Depot extends Carte {
    public Depot() {
        super("Dépôt", 1, 3, Type.ACTION, "Piochez 2 cartes. Défaussez 2 cartes de votre main.");
    }
}
