package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public abstract class CarteRail extends Carte {
    public CarteRail(String nom, int valeur, int cout, Type typeCarte, String effet) {
        super(nom, valeur, cout, typeCarte, effet);
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);
        joueur.incrementerPointsRails();
        if(joueur.getRecevoirFerraille() && joueur.getJeu().estExistantDansLaReserve("Ferraille"))
            joueur.getCartesRecues().add(joueur.getJeu().prendreDansLaReserve("Ferraille"));
        joueur.setArgent(joueur.getArgent() + joueur.getBonusFerronnerie());
    }
}
