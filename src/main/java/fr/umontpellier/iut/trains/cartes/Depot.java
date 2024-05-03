package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public class Depot extends Carte {
    private int nbCartes;

    public Depot() {
        super("Dépôt", 1, 3, Type.ACTION, "Piochez 2 cartes. Défaussez 2 cartes de votre main.");
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);
        joueur.setCarteAction(this);
        nbCartes = Math.min(2, joueur.getPioche().size() + joueur.getDefausse().size());
        if(nbCartes != 0) {
            joueur.getMain().addAll(joueur.piocher(nbCartes));
            joueur.setPeutPasser(false);
            for(Carte carte : joueur.getMain())
                joueur.ajouterChoixPossibleAction(carte.getNom());
        }
    }

    @Override
    public void jouer(Joueur joueur, String choix) {
        Carte carte = joueur.getMain().retirer(choix);
        if(carte != null)
            joueur.getDefausse().add(carte);
        joueur.retirerChoixPossibleAction(choix);
        if(--nbCartes <= 0) {
            joueur.setCarteAction(null);
            joueur.setPeutPasser(true);
        }
    }
}
