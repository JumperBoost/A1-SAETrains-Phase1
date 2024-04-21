package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public class PassageEnGare extends Carte {
    public PassageEnGare() {
        super("Passage en gare", 1, 3, Type.ACTION, "Piochez une carte.");
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);
        joueur.getMain().add(joueur.piocher());
    }

    // PRÉ-REQUIS : Au moins une carte dans la pioche ou la défausse.
    @Override
    public boolean peutJouer(Joueur joueur) {
        return super.peutJouer(joueur) && joueur.getPioche().size() + joueur.getDefausse().size() > 0;
    }
}
