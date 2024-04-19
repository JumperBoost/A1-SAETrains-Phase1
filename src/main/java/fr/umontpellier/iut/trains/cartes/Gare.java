package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;
import fr.umontpellier.iut.trains.plateau.Tuile;
import fr.umontpellier.iut.trains.plateau.TuileVille;

public class Gare extends Carte {
    public Gare() {
        super("Gare", 0, 3, Type.GARE, "Cette carte vous permet d'ajouter une station sur une des villes du plateau, mais rejette 1 carte férraille.");
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);
        joueur.setCarteAction(this);
        joueur.getCartesRecues().add(joueur.getJeu().prendreDansLaReserve("Ferraille"));
        // Déterminer la liste des villes où le joueur peut ajouter une station
        boolean tuileDispo = false;
        for(Tuile tuile : joueur.getJeu().getTuiles()) {
            if (tuile instanceof TuileVille && ((TuileVille) tuile).getNbGaresDispo() > 0) {
                joueur.ajouterChoixPossibleAction("TUILE:" + joueur.getJeu().getTuiles().indexOf(tuile));
                tuileDispo = true;
            }
        }

        // Mettre l'action en attente d'une tuile à choisir (s'il y a des tuiles disponibles)
        if(!tuileDispo)
            joueur.setCarteAction(null);
        else joueur.setPeutPasser(false);
    }

    @Override
    public void jouer(Joueur joueur, String nomCarte) {
        TuileVille tuile = (TuileVille) joueur.getJeu().getTuile(Integer.parseInt(nomCarte.split("TUILE:")[1]));
        if(!tuile.hasRail(joueur))
            tuile.ajouterRail(joueur);
        tuile.ajouterGare();
        joueur.setCarteAction(null);
        joueur.setPeutPasser(true);
    }
}
