package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public class PontEnAcier extends CarteRail {
    public PontEnAcier() {
        super("Pont en acier", 0, 4, Type.RAIL, "Gagnez un point de rail.\nRécupérez une Férraille.\nAucun surcout pour poser des rails sur des rivières.");
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);
        joueur.setSurcoutRiviere(false);
    }
}
