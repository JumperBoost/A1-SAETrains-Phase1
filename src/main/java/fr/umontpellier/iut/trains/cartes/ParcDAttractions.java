package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public class ParcDAttractions extends Carte {
    public ParcDAttractions() {
        super("Parc d'attractions", 1, 4, Type.ACTION, "Recevez X de valeurs, correspondant à la valeur d'une de vos cartes Train en jeu. ");
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);
        joueur.setCarteAction(this);
        for(Carte carte : joueur.getCartesEnJeu())
            if(carte.getFirstType() == Type.TRAIN)
                joueur.ajouterChoixPossibleAction(carte.getNom());
        if(joueur.getNbChoixPossiblesAction() > 0)
            joueur.setPeutPasser(false);
    }

    @Override
    public void jouer(Joueur joueur, String choix) {
        Carte carte = joueur.getCartesEnJeu().getCarte(choix);
        if(carte != null)
            joueur.setArgent(joueur.getArgent() + carte.getValeur());
        joueur.setCarteAction(null);
        joueur.setPeutPasser(true);
    }
}
