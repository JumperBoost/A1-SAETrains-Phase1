package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public class GratteCiel extends Carte {
    public GratteCiel() {
        super("Gratte-Ciel", 0, 8, Type.VICTOIRE, "Lorsque vous achetez cette carte: obtenez 1 carte férraille.", 4);
    }

    @Override
    public void jouer(Joueur joueur){
        super.jouer(joueur);
        joueur.getCartesRecues().add(joueur.getJeu().prendreDansLaReserve("Ferraille"));
    }
}