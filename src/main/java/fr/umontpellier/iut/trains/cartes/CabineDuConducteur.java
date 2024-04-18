package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public class CabineDuConducteur extends Carte {
    public CabineDuConducteur() {
        super("Cabine du conducteur", 0, 2, Type.ACTION, "Défaussez autant de cartes que vous voulez de votre main. Piochez 1 carte par carte défaussée.");
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);
        joueur.setCarteAction(this);
        for(Carte carte : joueur.getMain())
            joueur.ajouterChoixPossibleAction(carte.getNom());
    }

    @Override
    public void jouer(Joueur joueur, String nomCarte) {
        if(nomCarte.isEmpty()) {
            joueur.setCarteAction(null);
            return;
        }

        joueur.getDefausse().add(joueur.getMain().retirer(nomCarte));
        joueur.getMain().add(joueur.piocher());
        // Vérifier si l'on peut toujours défausser
        if(joueur.getMain().isEmpty())
            joueur.viderChoixPossiblesActions();
    }

    // PRÉ-REQUIS : Au moins une carte dans la main et la pioche
    @Override
    public boolean peutJouer(Joueur joueur) {
        return super.peutJouer(joueur) && !joueur.getMain().isEmpty() && !joueur.getPioche().isEmpty();
    }
}
