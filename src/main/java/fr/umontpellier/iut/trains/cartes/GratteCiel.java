package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public class GratteCiel extends Carte {
    public GratteCiel() {
        super("Gratte-Ciel", 0, 8, Type.VICTOIRE, "Lorsque vous achetez cette carte: vouz obtenez 1 carte féraille.", 4);
    }

    @Override
    public void jouer(Joueur joueur){
        /**
         * Il faut ajouter une fonction dans joueur pour prendre une carte dans la réserve qui se trouve
         * dans jeu.java, puis appeler cette fonction de GratteCiel pour prendre une ferraille.
         */
    }
}