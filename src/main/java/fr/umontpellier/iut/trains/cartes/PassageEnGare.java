package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public class PassageEnGare extends Carte {
    public PassageEnGare() {
        super("Passage en gare", 1, 3, Type.ACTION, "Piochez une carte.");
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);
        if(joueur.getPeutPiocher())
            joueur.getMain().add(joueur.piocher());
    }
}
