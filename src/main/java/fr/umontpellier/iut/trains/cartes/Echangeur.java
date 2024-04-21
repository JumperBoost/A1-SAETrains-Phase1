package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public class Echangeur extends Carte {
    public Echangeur() {
        super("Échangeur", 1, 3, Type.ACTION, "Remettez une carte Train de votre zone de jeu sur le dessus de votre deck.");
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
        joueur.getPioche().add(0, joueur.getCartesEnJeu().retirer(choix));
        joueur.setCarteAction(null);
        joueur.setPeutPasser(true);
    }

    // PRÉ-REQUIS : Au moins une carte dans les cartes en jeu du joueur
    @Override
    public boolean peutJouer(Joueur joueur) {
        return super.peutJouer(joueur) && !joueur.getCartesEnJeu().isEmpty();
    }
}
