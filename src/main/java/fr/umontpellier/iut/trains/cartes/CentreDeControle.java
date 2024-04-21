package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public class CentreDeControle extends Carte {
    public CentreDeControle() {
        super("Centre de contrôle", 0, 3, Type.ACTION, "Piochez 1 carte puis nommez une carte. Dévoilez la première carte de votre deck. Si c'est la carte nommée, ajoutez-la à votre main. Sinon, remettez-la sur votre deck.");
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);
        joueur.setCarteAction(this);
        joueur.setPeutPasser(false);
        joueur.getMain().add(joueur.piocher());
        for(Carte carte : joueur.getCartesComplet())
            joueur.ajouterChoixPossibleAction(carte.getNom());
        for(String nomCarte : joueur.getJeu().getListeNomsCartes())
            joueur.ajouterChoixPossibleAction(nomCarte);
    }

    @Override
    public void jouer(Joueur joueur, String choix) {
        if(joueur.getPioche().get(0).getNom().equals(choix))
            joueur.getMain().add(joueur.piocher());
        joueur.setCarteAction(null);
        joueur.setPeutPasser(true);
    }

    // PRÉ-REQUIS : Au moins 2 cartes quelconques dans la pioche du joueur
    @Override
    public boolean peutJouer(Joueur joueur) {
        return super.peutJouer(joueur) && joueur.getPioche().size() >= 2;
    }
}
