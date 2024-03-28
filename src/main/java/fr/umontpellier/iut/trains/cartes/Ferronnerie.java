package fr.umontpellier.iut.trains.cartes;

public class Ferronnerie extends Carte {
    public Ferronnerie() {
        super("Ferronnerie", 1, 4, Type.ACTION, "Durant ce tour, vous recevez 2 points de valeur à chaque fois que jouez une carte Pose de rails.");
    }
}
