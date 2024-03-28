package fr.umontpellier.iut.trains.cartes;

import java.util.List;

public class TrainDeTourisme extends Carte {
    public TrainDeTourisme() {
        super("Train de tourisme", 1, 4, List.of(Type.TRAIN, Type.ACTION), Type.TRAIN.getCouleur(), "Cette carte vous fait gagner un Point de Victoire.");
    }
}
