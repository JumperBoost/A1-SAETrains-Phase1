package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public class Aiguillage extends Carte {
    public Aiguillage() {
        super("Aiguillage", 0, 5, Type.ACTION, "Piochez 2 cartes.");
    }

    @Override
    public void jouer(Joueur joueur){
        super.jouer(joueur);
        joueur.getMain().addAll(joueur.piocher(2));
    }
}
