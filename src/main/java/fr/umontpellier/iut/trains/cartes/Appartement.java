package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public class Appartement extends Carte {
    public Appartement() {
        super("Appartement", 0, 3, Type.VICTOIRE, "Lorsque vous achetez cette carte : Recevez une carte férraille.", 1);
    }

    @Override
    public void acheter(Joueur joueur) {
        super.acheter(joueur);
        joueur.getCartesRecues().add(joueur.getJeu().prendreDansLaReserve("Ferraille"));
    }
}
