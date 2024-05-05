package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public class Ferraille extends Carte {
    public Ferraille() {
        super("Ferraille", 0, 0, Type.FERRAILLE, "Vous ne pouvez acheter cette carte.");
    }

    @Override
    public void jouer(Joueur joueur) {
        /*
         * Déposer toutes les cartes FERRAILLE présentes dans la main du joueur dans la réserve de FERRAILLE
         * si aucune carte n'a été joué précédemment (début du tour)
         */
        if(joueur.getCartesEnJeu().isEmpty()) {
            Carte c;
            joueur.getJeu().getReserve().get("Ferraille").add(this);
            // Déposer toutes les autres cartes FERRAILLE de la main du joueur dans la réserve de FERRAILLE
            while ((c = joueur.getMain().retirer("Ferraille")) != null)
                joueur.getJeu().getReserve().get("Ferraille").add(c);
            joueur.setFinTour(true);
        }
    }
}
