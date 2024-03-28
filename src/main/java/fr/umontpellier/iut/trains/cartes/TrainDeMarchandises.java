package fr.umontpellier.iut.trains.cartes;

import java.util.List;

public class TrainDeMarchandises extends Carte {
    public TrainDeMarchandises() {
    super("Train de marchandises", 1, 4, List.of(Type.TRAIN, Type.ACTION), Type.TRAIN.getCouleur(), "Remettez sur la pile Ferraille autant de cartes Ferraille que vous voulez de votre main. Recevez un point de Valeur par carte Ferraille remise.");
    }
}
