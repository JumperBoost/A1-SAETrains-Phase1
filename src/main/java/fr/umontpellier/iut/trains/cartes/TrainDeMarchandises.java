package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

import java.util.List;

public class TrainDeMarchandises extends Carte {
    public TrainDeMarchandises() {
        super("Train de marchandises", 1, 4, List.of(Type.TRAIN, Type.ACTION), Type.TRAIN.getCouleur(), "Remettez sur la pile Ferraille autant de cartes Ferraille que vous voulez de votre main. Recevez un point de Valeur par carte Ferraille remise.");
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);
        joueur.setCarteAction(this);
        joueur.ajouterChoixPossibleAction("Ferraille");
    }

    @Override
    public void jouer(Joueur joueur, String choix) {
        if(choix.equals("Ferraille")) {
            Carte carte = joueur.getMain().retirer("Ferraille");
            if(carte != null) {
                joueur.getJeu().getReserve().get("Ferraille").add(carte);
                joueur.setArgent(joueur.getArgent() + 1);
            }
        } else joueur.setCarteAction(null);
    }
}
