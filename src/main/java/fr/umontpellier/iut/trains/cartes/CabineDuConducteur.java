package fr.umontpellier.iut.trains.cartes;

public class CabineDuConducteur extends Carte {
    public CabineDuConducteur() {
        super("Cabine du conducteur", 0, 2, Type.ACTION, "Défaussez autant de cartes que vous voulez de votre main. Piochez 1 carte par carte défaussée.");
    }
}
