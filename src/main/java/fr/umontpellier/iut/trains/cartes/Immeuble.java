package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public class Immeuble extends Carte {
    public Immeuble() {
        super("Immeuble", 0, 5, Type.VICTOIRE, "Vous recevez une carte férraille.", 2);
    }

    @Override
    public void acheter(Joueur joueur) {
        super.acheter(joueur);
        if(joueur.getRecevoirFerraille() && joueur.getJeu().estExistantDansLaReserve("Ferraille"))
            joueur.getCartesRecues().add(joueur.getJeu().prendreDansLaReserve("Ferraille"));
    }
}
