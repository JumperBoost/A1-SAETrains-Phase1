package fr.umontpellier.iut.trains.cartes;

public enum Type {
    TRAIN("train", "bleu"),
    RAIL("rail", "vert"),
    GARE("gare", "violet"),
    ACTION("action", "rouge"),
    VICTOIRE("victoire", "jaune"),
    FERRAILLE("ferraille", "gris");

    private final String type;
    private final String couleur;

    Type(String type, String couleur) {
        this.type = type;
        this.couleur = couleur;
    }

    public String getCouleur() {
        return couleur;
    }
}
