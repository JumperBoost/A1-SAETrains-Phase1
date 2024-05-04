package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public class HorairesEstivaux extends Carte {
    public HorairesEstivaux() {
        super("Horaires estivaux", 0, 3, Type.ACTION, "Vous pouvez écarter cette carte. Dans ce cas, recevez 3 points Valeur.");
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);
        joueur.setCarteAction(this);
        joueur.setPeutPasser(false);
        joueur.ajouterChoixPossibleAction("oui");
        joueur.ajouterChoixPossibleAction("non");
    }

    @Override
    public void jouer(Joueur joueur, String choix) {
        if (choix.equals("oui")) {
            joueur.setArgent(joueur.getArgent() + 3);
            Carte carte = joueur.getSourceClone() == null ? this : joueur.getSourceClone();
            joueur.getMain().remove(carte);
            joueur.getCartesEnJeu().remove(carte);
            joueur.getJeu().getCartesEcartees().add(carte);
        }
        joueur.setCarteAction(null);
        joueur.setPeutPasser(true);
        super.jouer(joueur, choix);
    }
}
