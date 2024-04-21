package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public class Appartement extends Carte {
    public Appartement() {
        super("Appartement", 0, 3, Type.VICTOIRE, "Lorsque vous achetez cette carte : Recevez une carte férraille.", 1);
    }

    @Override
    public void jouer(Joueur joueur) {
        joueur.donnePvBonus(super.getPv());
        //Pas terminé, doit faire piocher une carte feraille.
    }
}
