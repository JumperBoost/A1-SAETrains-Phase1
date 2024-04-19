package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public class Depotoir extends Carte {
    public Depotoir() {
        super("Dépotoir", 1, 5, Type.ACTION, "Vous ne recevez aucune carte férraille durant ce tour.");
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);
        joueur.setRecevoirFerraille(false);
    }
}
