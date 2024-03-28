package fr.umontpellier.iut.trains.cartes;

public class AtelierDeMaintenance extends Carte {
    public AtelierDeMaintenance() {
        super("Atelier de maintenance", 0, 5, Type.ACTION, "Dévoilez une carte Train de votre main. Recevez une carte identique à celle dévoilée (s'il en reste dans la réserve).");
    }
}
