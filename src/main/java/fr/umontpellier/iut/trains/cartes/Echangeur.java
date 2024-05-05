package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Bouton;
import fr.umontpellier.iut.trains.Joueur;

public class Echangeur extends Carte {
    public Echangeur() {
        super("Échangeur", 1, 3, Type.ACTION, "Remettez une carte Train de votre zone de jeu sur le dessus de votre deck.");
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);
        joueur.setCarteAction(this);
        for (Carte carte : joueur.getCartesEnJeu())
            if (carte.getFirstType() == Type.TRAIN){
                joueur.ajouterChoixPossibleAction(carte.getNom());
                joueur.ajouterBoutonPossibleAction(new Bouton(carte.getNom(), carte.getNom()));
            }
    }

    @Override
    public void jouer(Joueur joueur, String choix) {
        if(!choix.isEmpty())
            joueur.getPioche().add(0, joueur.getCartesEnJeu().retirer(choix));
        joueur.setCarteAction(null);
        joueur.setPeutPasser(true);
    }
}
