package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public class Depot extends Carte {
    private int nbCartes;

    public Depot() {
        super("Dépôt", 1, 3, Type.ACTION, "Piochez 2 cartes. Défaussez 2 cartes de votre main.");
        nbCartes = 2;
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);
        joueur.getMain().addAll(joueur.piocher(2));
        joueur.setCarteAction(this);
        joueur.setPeutPasser(false);
        for(Carte carte : joueur.getMain())
            joueur.ajouterChoixPossibleAction(carte.getNom());
    }

    @Override
    public void jouer(Joueur joueur, String nomCarte) {
        Carte carte = joueur.getMain().retirer(nomCarte);
        joueur.getDefausse().add(carte);
        joueur.retirerChoixPossibleAction(nomCarte);
        if(--nbCartes == 0) {
            joueur.setCarteAction(null);
            joueur.setPeutPasser(true);
        }
    }

    // PRÉ-REQUIS : Au moins 2 cartes quelconques dans la main et au moins 2 cartes dans la pioche et/ou la défausse du joueur
    @Override
    public boolean peutJouer(Joueur joueur) {
        return super.peutJouer(joueur) && joueur.getMain().size() >= 2
                && joueur.getPioche().size() + joueur.getDefausse().size() >= 2;
    }
}
