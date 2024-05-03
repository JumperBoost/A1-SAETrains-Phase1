package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public class Aiguillage extends Carte {
    public Aiguillage() {
        super("Aiguillage", 0, 5, Type.ACTION, "Piochez 2 cartes.");
    }

    @Override
    public void jouer(Joueur joueur){
        super.jouer(joueur);
        int nbCartes = Math.min(2, joueur.getPioche().size() + joueur.getDefausse().size());
        joueur.getMain().addAll(joueur.piocher(nbCartes));
    }
}
