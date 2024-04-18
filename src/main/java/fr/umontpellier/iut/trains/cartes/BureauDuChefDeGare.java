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
            if(carte.getTypesCarte().contains(Type.ACTION))
                joueur.ajouterChoixPossibleAction(carte.getNom());
    }

    @Override
    public void jouer(Joueur joueur, String nomCarte) {
        Carte carte = joueur.getMain().getCarte(nomCarte);
        carte.jouer(joueur);
        joueur.setCarteAction(null);
        joueur.setPeutPasser(true);
    }

    // PRÉ-REQUIS : Au moins une carte ACTION dans la main du joueur
    @Override
    public boolean peutJouer(Joueur joueur) {
        return super.peutJouer(joueur) && joueur.getMain().count(Type.ACTION) > 0;
    }
}
