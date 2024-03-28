package fr.umontpellier.iut.trains.cartes;

public class Viaduc extends Carte {
    public Viaduc() {
        super("Viaduc", 0, 5, Type.RAIL, "Cette carte vous permet de poser un rail, mais vous obtenez une carte ferraille. Aucun surcout pour poser des rails sur des villes");
    }
}
