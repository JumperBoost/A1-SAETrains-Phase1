package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public class Cooperation extends CarteRail {
    public Cooperation() {
        super("Coopération", 0, 5, Type.RAIL, "Cette carte vous donne 1 point de pose de rail, mais vous donne une carte férraille. Cette carte vous octroie l'effet \"Aucun surcout pour poser des rails là où vos adversaires ont des rails. Vous ne recevez pas de cartes férrailles\".");
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);
        joueur.setSurcoutAdversaires(false);
    }
}
