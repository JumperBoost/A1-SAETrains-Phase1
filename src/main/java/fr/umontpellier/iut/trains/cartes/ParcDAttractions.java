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
        joueur.setPeutPasser(false);
        for(Carte carte : joueur.getCartesEnJeu())
            if(carte.getFirstType() == Type.TRAIN)
                joueur.ajouterChoixPossibleAction(carte.getNom());
    }

    @Override
    public void jouer(Joueur joueur, String choix) {
        Carte carte = joueur.getCartesEnJeu().getCarte(choix);
        joueur.setArgent(joueur.getArgent() + carte.getValeur());
        joueur.setCarteAction(null);
        joueur.setPeutPasser(true);
    }

    // PRÉ-REQUIS : Au moins une carte Train en jeu
    @Override
    public boolean peutJouer(Joueur joueur) {
        return super.peutJouer(joueur) && joueur.getCartesEnJeu().stream().anyMatch(carte -> carte.getFirstType() == Type.TRAIN);
    }
}
