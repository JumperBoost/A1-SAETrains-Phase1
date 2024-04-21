package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public class Immeuble extends Carte {
    public Immeuble() {
        super("Immeuble", 0, 5, Type.VICTOIRE, "Vous recevez une carte férraille.", 2);
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);
        joueur.getCartesRecues().add(joueur.getJeu().prendreDansLaReserve("Ferraille"));
    }
}
