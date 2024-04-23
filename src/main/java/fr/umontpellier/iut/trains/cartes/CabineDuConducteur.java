package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public class CabineDuConducteur extends Carte {
    private int nbCartesDefaussees;

    public CabineDuConducteur() {
        super("Cabine du conducteur", 0, 2, Type.ACTION, "Défaussez autant de cartes que vous voulez de votre main. Piochez 1 carte par carte défaussée.");
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);
        joueur.setCarteAction(this);
        for(Carte carte : joueur.getMain())
            joueur.ajouterChoixPossibleAction(carte.getNom());
        nbCartesDefaussees = 0;
    }

    @Override
    public void jouer(Joueur joueur, String choix) {
        if(choix.isEmpty()) {
            joueur.getMain().addAll(joueur.piocher(nbCartesDefaussees));
            joueur.setCarteAction(null);
            return;
        }

        joueur.getDefausse().add(joueur.getMain().retirer(choix));
        if(joueur.getMain().count(choix) == 0)
            joueur.retirerChoixPossibleAction(choix);
        nbCartesDefaussees++;
    }
}
