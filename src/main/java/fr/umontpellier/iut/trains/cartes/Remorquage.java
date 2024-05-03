package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public class Remorquage extends Carte {
    public Remorquage() {
        super("Remorquage", 0, 3, Type.ACTION, "Prenez une carte Train de votre défausse et ajoutez-la à votre main.");
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);
        joueur.setCarteAction(this);
        if(joueur.getDefausse().count(Type.TRAIN) > 0) {
            for(Carte carte : joueur.getDefausse())
                if(carte.getFirstType() == Type.TRAIN)
                    joueur.ajouterChoixPossibleAction(carte.getNom());
            joueur.setPeutPasser(false);
        }
    }

    @Override
    public void jouer(Joueur joueur, String choix) {
        Carte carte = joueur.getDefausse().retirer(choix);
        if(carte != null)
            joueur.getMain().add(carte);
        joueur.setCarteAction(null);
        joueur.setPeutPasser(true);
    }
}
