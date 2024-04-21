package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

import java.util.List;

public class TrainPostal extends Carte {
    public TrainPostal() {
        super("Train postal", 1, 4, List.of(Type.TRAIN, Type.ACTION), Type.TRAIN.getCouleur(), "Défaussez autant de cartes que vous voulez de votre main. Recevez 1 point de valeur par carte défaussée.");
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);
        joueur.setCarteAction(this);
        for(Carte carte : joueur.getMain())
            joueur.ajouterChoixPossibleAction(carte.getNom());
    }

    @Override
    public void jouer(Joueur joueur, String choix) {
        if(choix.isEmpty()) {
            joueur.setCarteAction(null);
            return;
        }

        Carte carte = joueur.getMain().retirer(choix);
        joueur.getDefausse().add(carte);
        joueur.setArgent(joueur.getArgent() + 1);
    }
}
