package fr.umontpellier.iut.trains.cartes;

import java.util.List;

public class TrainPostal extends Carte {
    public TrainPostal() {
        super("Train postal", 1, 4, List.of(Type.TRAIN, Type.ACTION), Type.TRAIN.getCouleur(), "Défaussez autant de cartes que vous voulez de votre main. Recevez 1 point de valeur par carte défaussée.");
    }
}
