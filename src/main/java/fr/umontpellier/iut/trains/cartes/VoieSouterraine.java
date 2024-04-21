package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public class VoieSouterraine extends Carte {
    public VoieSouterraine() {
        super("Voie souterraine", 0, 7, Type.RAIL, "Cette carte vous permet de poser un rail, mais vous obtenez une carte ferraille. Aucun surcout pour poser des rails.");
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);
        joueur.incrementerPointsRails(1);
        joueur.getCartesRecues().add(joueur.getJeu().prendreDansLaReserve("Ferraille"));
    }
}
