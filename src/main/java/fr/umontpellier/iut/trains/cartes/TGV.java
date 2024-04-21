package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

import java.util.List;

public class TGV extends Carte {
    public TGV() {
        super("TGV", 1, 2, List.of(Type.TRAIN, Type.ACTION), Type.TRAIN.getCouleur(), "Recevez un point de Valeur si vous avez un Train omnibus en jeu.");
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);
        if (joueur.getCartesEnJeu().stream().anyMatch(carte -> carte.getNom().equals("Train omnibus")))
            joueur.setArgent(joueur.getArgent() + 1);
    }
}
