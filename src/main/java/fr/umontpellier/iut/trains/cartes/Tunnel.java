package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public class Tunnel extends CarteRail {
    public Tunnel() {
        super("Tunnel", 0, 5, Type.RAIL, "Vous gagnez un point de pose de rail, recevez ensuite une carte férraille. Enfin, vous gagnez l'effet d'avoir aucun surcout pour poser des rails sur des montagnes.");
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);
        joueur.setSurcoutMontagne(false);
    }
}
