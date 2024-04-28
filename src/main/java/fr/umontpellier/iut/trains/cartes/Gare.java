package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;
import fr.umontpellier.iut.trains.plateau.Tuile;
import fr.umontpellier.iut.trains.plateau.TuileVille;
import fr.umontpellier.iut.trains.plateau.TypeTuile;

public class Gare extends Carte {
    public Gare() {
        super("Gare", 0, 3, Type.GARE, "Cette carte vous permet d'ajouter une station sur une des villes du plateau, mais rejette 1 carte férraille.");
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);
        if(joueur.getRecevoirFerraille())
            joueur.getCartesRecues().add(joueur.getJeu().prendreDansLaReserve("Ferraille"));
        if(joueur.getJeu().getNbJetonsGare() > 0) {
            joueur.setCarteAction(this);
            // Déterminer la liste des villes où le joueur peut ajouter une station
            boolean tuileDispo = false;
            for (Tuile tuile : joueur.getJeu().getTuiles()) {
                if (tuile.getType() == TypeTuile.VILLE && ((TuileVille) tuile).getNbGaresDispo() > 0) {
                    joueur.ajouterChoixPossibleAction("TUILE:" + joueur.getJeu().getTuiles().indexOf(tuile));
                    tuileDispo = true;
                }
            }

            // Mettre l'action en attente d'une tuile à choisir (s'il y a des tuiles disponibles)
            if(!tuileDispo)
                joueur.setCarteAction(null);
            else joueur.setPeutPasser(false);
        }
    }

    @Override
    public void jouer(Joueur joueur, String choix) {
        TuileVille tuile = (TuileVille) joueur.getJeu().getTuile(Integer.parseInt(choix.split("TUILE:")[1]));
        tuile.ajouterGare();
        joueur.getJeu().desincrementerNbJetonsGare();
        joueur.setCarteAction(null);
        joueur.setPeutPasser(true);
    }
}
