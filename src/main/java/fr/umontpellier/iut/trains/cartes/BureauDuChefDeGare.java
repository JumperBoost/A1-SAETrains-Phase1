package fr.umontpellier.iut.trains.cartes;

public class BureauDuChefDeGare extends Carte {
    public BureauDuChefDeGare() {
        super("Bureau du chef de gare", 0, 4, Type.ACTION, "Choisissez une carte Action que vous avez en main. Cette carte copie l'effet de la carte choisie.");
    }
}
