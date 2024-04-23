package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public class GratteCiel extends Carte {
    public GratteCiel() {
        super("Gratte-ciel", 0, 8, Type.VICTOIRE, "Lorsque vous achetez cette carte: obtenez 1 carte férraille.", 4);
    }

    @Override
    public void acheter(Joueur joueur){
        super.acheter(joueur);
        joueur.getCartesRecues().add(joueur.getJeu().prendreDansLaReserve("Ferraille"));
    }
}