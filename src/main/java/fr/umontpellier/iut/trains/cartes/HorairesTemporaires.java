package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public class HorairesTemporaires extends Carte {
    public HorairesTemporaires() {
        super("Horaires temporaires", 0, 5, Type.ACTION, "Dévoilez des cartes de votre deck jusqu'à ce que vous dévoiliez 2 Trains. Ajoutez ces cartes Trains à votre main et défaussez le reste.");
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);
        int nbTrains = Math.min(2, joueur.getPioche().count(Type.TRAIN) + joueur.getDefausse().count(Type.TRAIN));
        while (nbTrains > 0) {
            Carte carte = joueur.piocher();
            if(carte.getFirstType() == Type.TRAIN) {
                joueur.getMain().add(carte);
                nbTrains--;
            } else joueur.getDefausse().add(carte);
        }
    }
}
