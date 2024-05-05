package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Bouton;
import fr.umontpellier.iut.trains.Joueur;

public class CentreDeControle extends Carte {
    public CentreDeControle() {
        super("Centre de contrôle", 0, 3, Type.ACTION, "Piochez 1 carte puis nommez une carte. Dévoilez la première carte de votre deck. Si c'est la carte nommée, ajoutez-la à votre main. Sinon, remettez-la sur votre deck.");
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);
        joueur.setCarteAction(this);
        if(joueur.getPeutPiocher()) {
            joueur.setPeutPasser(false);
            joueur.getMain().add(joueur.piocher());
            for (Carte carte : joueur.getCartes())
                joueur.ajouterBoutonPossibleAction(new Bouton(carte.getNom(), carte.getNom()));
            for (String nomCarte : joueur.getJeu().getListeNomsCartes())
                joueur.ajouterBoutonPossibleAction(new Bouton(nomCarte, nomCarte));
            for (Carte carte : joueur.getJeu().getCartesEcartees())
                joueur.ajouterBoutonPossibleAction(new Bouton(carte.getNom(), carte.getNom()));
        }
    }

    @Override
    public void jouer(Joueur joueur, String choix) {
        if(joueur.getPeutPiocher() && joueur.recuperer().getNom().equals(choix))
            joueur.getMain().add(joueur.piocher());
        joueur.setCarteAction(null);
        joueur.setPeutPasser(true);
    }
}
