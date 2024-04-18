package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public class CentreDeRenseignements extends Carte {
    private int nbCartes;

    public CentreDeRenseignements() {
        super("Centre de renseignements", 1, 4, Type.ACTION, "Dévoilez les 4 premières cartes de votre deck. Vous pouvez en prendre 1 dans votre main. Remettez les autres sur le dessus de votre deck dans l'ordre de votre choix.");
        nbCartes = 0;
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);
        joueur.setCarteAction(this);
        for(Carte carte : joueur.getPioche())
            joueur.ajouterChoixPossibleAction(carte.getNom());
    }

    @Override
    public void jouer(Joueur joueur, String nomCarte) {
        if(nbCartes == 0 && !nomCarte.isEmpty())
            joueur.getMain().add(joueur.getPioche().retirer(nomCarte));
        joueur.retirerChoixPossibleAction(nomCarte);
        nbCartes++;
        if(nbCartes == 4)
            joueur.setCarteAction(null);
    }

    // PRÉ-REQUIS : Au moins 4 cartes quelconques dans la pioche du joueur
    @Override
    public boolean peutJouer(Joueur joueur) {
        return super.peutJouer(joueur) && joueur.getPioche().size() >= 4;
    }
}
