package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public class FeuDeSignalisation extends Carte {
    public FeuDeSignalisation() {
        super("Feu de signalisation", 0, 2, Type.ACTION, "Piochez 1 carte puis consultez la première carte de votre deck. Défaussez-la ou replacez-la sur le dessus de votre deck.");
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);
        joueur.getMain().add(joueur.piocher());
        if(joueur.getPioche().size() + joueur.getDefausse().size() > 0) {
            joueur.setCarteAction(this);
            joueur.setPeutPasser(false);
            joueur.ajouterChoixPossibleAction("oui");
            joueur.ajouterChoixPossibleAction("non");
        }
    }

    @Override
    public void jouer(Joueur joueur, String nomCarte) {
        Carte carte = joueur.piocher();
        if(nomCarte.equals("oui"))
            joueur.getDefausse().add(carte);
        else joueur.getPioche().add(0, carte);
        joueur.setCarteAction(null);
        joueur.setPeutPasser(true);
    }

    // PRÉ-REQUIS : Au moins une carte dans la pioche ou la défausse
    @Override
    public boolean peutJouer(Joueur joueur) {
        return super.peutJouer(joueur) && (joueur.getPioche().size() + joueur.getDefausse().size()) > 0;
    }
}
