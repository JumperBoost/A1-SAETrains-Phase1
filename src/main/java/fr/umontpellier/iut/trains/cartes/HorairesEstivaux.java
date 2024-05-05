package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Bouton;
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
        joueur.ajouterBoutonPossibleAction(new Bouton("Oui !", "oui"));
        joueur.ajouterBoutonPossibleAction(new Bouton("Non !", "non"));
    }

    @Override
    public void jouer(Joueur joueur, String choix) {
        if (choix.equals("oui")) {
            joueur.setArgent(joueur.getArgent() + 3);
            joueur.getCartesEnJeu().remove(this);
            joueur.getJeu().getCartesEcartees().add(this);
        }
        joueur.setCarteAction(null);
        joueur.setPeutPasser(true);
    }
}
