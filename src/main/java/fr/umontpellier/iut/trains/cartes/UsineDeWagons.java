package fr.umontpellier.iut.trains.cartes;

public class UsineDeWagons extends Carte {
    public UsineDeWagons() {
        super("Usine de wagons", 0, 2, Type.ACTION, "Écartez une carte Train de votre main. Recevez une carte Train coutant jusqu'à 3 de plus que la carte écartée. Ajoutez cette nouvelle carte à votre main.");
    }
}
