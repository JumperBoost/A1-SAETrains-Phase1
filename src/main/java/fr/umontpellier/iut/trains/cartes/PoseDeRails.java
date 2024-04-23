package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public class PoseDeRails extends CarteRail {
    public PoseDeRails() {
        super("Pose de rails", 0, 3, Type.RAIL, "Gagnez un point de rail.\nRécupérez 1 Férraille.");
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);
    }
}
