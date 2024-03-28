package fr.umontpellier.iut.trains.cartes;

public class VoieSouterraine extends Carte {
    public VoieSouterraine() {
        super("Voie souterraine", 0, 7, Type.RAIL, "Cette carte vous permet de poser un rail, mais vous obtenez une carte ferraille. Aucun surcout pour poser des rails.");
    }
}
