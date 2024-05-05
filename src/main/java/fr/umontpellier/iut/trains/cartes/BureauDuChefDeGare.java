package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Bouton;
import fr.umontpellier.iut.trains.Joueur;

import java.util.ArrayList;
import java.util.List;

public class BureauDuChefDeGare extends Carte {
    public BureauDuChefDeGare() {
        super("Bureau du chef de gare", 0, 4, Type.ACTION, "Choisissez une carte Action que vous avez en main. Cette carte copie l'effet de la carte choisie.");
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);
        joueur.setCarteAction(this);
        List<Bouton> boutonAjouter = new ArrayList<>();
        for (Carte carte : joueur.getMain())
            if (carte.getTypesCarte().contains(Type.ACTION) && !carte.getNom().equals(getNom())) {
                joueur.ajouterChoixPossibleAction(carte.getNom());
                joueur.ajouterBoutonPossibleAction(new Bouton(carte.getNom(), carte.getNom()));
            }
        if (joueur.getNbChoixPossiblesAction() > 0){
            joueur.setPeutPasser(false);
        }
    }

    @Override
    public void jouer(Joueur joueur, String choix) {
        Carte carte = joueur.getMain().getCarte(choix);
        joueur.setCarteAction(null);
        joueur.setPeutPasser(true);
        if(carte != null) {
            joueur.log("Exécute la carte action " + carte.getNom());
            carte.jouer(joueur);
            // Remise de la carte choisie dans la main du joueur pour pouvoir la jouer à nouveau plus tard + Retrait de l'argent gagné
            if(joueur.getCartesEnJeu().contains(carte) && !carte.getNom().equals("Horaires estivaux")) {
                joueur.getCartesEnJeu().remove(carte);
                joueur.getMain().add(carte);
            }
            joueur.setArgent(joueur.getArgent() - carte.getValeur());
        }
    }
}
