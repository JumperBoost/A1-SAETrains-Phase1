package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public class BureauDuChefDeGare extends Carte {
    public BureauDuChefDeGare() {
        super("Bureau du chef de gare", 0, 4, Type.ACTION, "Choisissez une carte Action que vous avez en main. Cette carte copie l'effet de la carte choisie.");
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);
        joueur.setCarteAction(this);
        joueur.setPeutPasser(false);
        for(Carte carte : joueur.getMain())
            if(carte.getFirstType() == Type.ACTION)
                joueur.ajouterChoixPossibleAction(carte.getNom());
    }

    @Override
    public void jouer(Joueur joueur, String choix) {
        Carte carte = joueur.getMain().getCarte(choix);
        carte.jouer(joueur);
        // Remise de la carte choisie dans la main du joueur pour pouvoir la jouer à nouveau plus tard
        joueur.getCartesEnJeu().remove(carte);
        joueur.getMain().add(carte);
        joueur.setCarteAction(null);
        joueur.setPeutPasser(true);
    }

    // PRÉ-REQUIS : Au moins une carte ACTION dans la main du joueur
    @Override
    public boolean peutJouer(Joueur joueur) {
        return super.peutJouer(joueur) && joueur.getMain().count(Type.ACTION) > 0;
    }
}
