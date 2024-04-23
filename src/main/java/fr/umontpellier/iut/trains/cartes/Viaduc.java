package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public class Viaduc extends CarteRail {
    public Viaduc() {
        super("Viaduc", 0, 5, Type.RAIL, "Cette carte vous permet de poser un rail, mais vous obtenez une carte ferraille. Aucun surcout pour poser des rails sur des villes");
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);
        joueur.setSurcoutVille(false);
    }
}
