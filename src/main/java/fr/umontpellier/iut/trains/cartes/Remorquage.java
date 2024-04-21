package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

import java.util.ArrayList;
import java.util.List;

public class Remorquage extends Carte {
    public Remorquage() {
        super("Remorquage", 0, 3, Type.ACTION, "Prenez une carte Train de votre défausse et ajoutez-la à votre main.");
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);
        joueur.setCarteAction(this);
        List<String> choixPossibles = new ArrayList<>();
        for(Carte carte : joueur.getDefausse())
            if(carte.getFirstType() == Type.TRAIN)
                choixPossibles.add(carte.getNom());

        if(!choixPossibles.isEmpty()) {
            for(String nomCarte : choixPossibles)
                joueur.ajouterChoixPossibleAction(nomCarte);
            joueur.setPeutPasser(false);
        }
    }

    @Override
    public void jouer(Joueur joueur, String choix) {
        joueur.setCarteAction(null);
        joueur.setPeutPasser(true);
        Carte carte = joueur.getDefausse().retirer(choix);
        if(carte == null)
            return;
        joueur.getMain().add(carte);
    }
}
