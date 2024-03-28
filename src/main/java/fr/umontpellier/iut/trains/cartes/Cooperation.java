package fr.umontpellier.iut.trains.cartes;

public class Cooperation extends Carte {
    public Cooperation() {
        super("Coopération", 0, 5, Type.RAIL, "Cette carte vous donne 1 point de pose de rail, mais vous donne une carte féraille. Cette carte vous octroie l'effet \"Aucun surcout pour poser des rails là où vos adversaires ont des rails. Vous ne recevez pas de cartes férailles\".");
    }
}
