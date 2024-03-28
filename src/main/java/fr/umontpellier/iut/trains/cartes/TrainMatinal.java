package fr.umontpellier.iut.trains.cartes;

import java.util.List;

public class TrainMatinal extends Carte {
    public TrainMatinal() {
        super("Train matinal", 2, 5, List.of(Type.TRAIN, Type.ACTION), Type.TRAIN.getCouleur(), "Vous pouvez placer sur votre deck les cartes que vous achetez durant ce tour.");
    }
}
