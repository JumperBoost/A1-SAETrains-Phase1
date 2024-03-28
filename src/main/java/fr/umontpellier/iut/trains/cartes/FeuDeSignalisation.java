package fr.umontpellier.iut.trains.cartes;

public class FeuDeSignalisation extends Carte {
    public FeuDeSignalisation() {
        super("Feu de signalisation", 0, 2, Type.ACTION, "Piochez 1 carte puis consultez la première carte de votre deck. Défaussez-la ou replacez-la sur le dessus de votre deck.");
    }
}
