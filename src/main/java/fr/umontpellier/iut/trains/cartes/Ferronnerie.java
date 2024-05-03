package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public class Ferronnerie extends Carte {
    public Ferronnerie() {
        super("Ferronnerie", 1, 4, Type.ACTION, "Durant ce tour, vous recevez 2 points de valeur à chaque fois que vous jouez une carte Pose de rails.");
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);
        joueur.incrementerBonusFerronnerie(2);
    }
}
