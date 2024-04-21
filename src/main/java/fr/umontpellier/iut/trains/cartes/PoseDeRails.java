package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public class PoseDeRails extends Carte {
    public PoseDeRails() {
        super("Pose de rails", 0, 3, Type.RAIL, "Gagnez un point de rail.\nRécupérez 1 Férraille.");
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);
        joueur.incrementerPointsRails(1);
        joueur.getCartesRecues().add(joueur.getJeu().prendreDansLaReserve("Ferraille"));
    }
}
