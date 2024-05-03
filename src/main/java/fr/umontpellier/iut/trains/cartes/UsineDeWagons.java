package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

import java.util.*;

public class UsineDeWagons extends Carte {
    public UsineDeWagons() {
        super("Usine de wagons", 0, 5, Type.ACTION, "Écartez une carte Train de votre main. Recevez une carte Train coutant jusqu'à 3 de plus que la carte écartée. Ajoutez cette nouvelle carte à votre main.");
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);
        joueur.setCarteAction(this);
        for(Carte carte : joueur.getMain())
            if(carte.getFirstType() == Type.TRAIN)
                joueur.ajouterChoixPossibleAction(carte.getNom());
        if(joueur.getNbChoixPossiblesAction() > 0)
            joueur.setPeutPasser(false);
    }

    @Override
    public void jouer(Joueur joueur, String choix) {
        if(choix.startsWith("ACHAT:")) {
            String nomCarte = choix.split("ACHAT:")[1];
            Carte carte = joueur.getJeu().prendreDansLaReserve(nomCarte);
            if(carte != null) {
                joueur.getMain().add(carte);
                joueur.setCarteAction(null);
                joueur.setPeutPasser(true);
            }
        } else if(choix.isEmpty())
            joueur.setCarteAction(null);    // Valable uniquement lorsque le joueur n'a aucune carte Train dans sa main
        else {
            Carte carte = joueur.getMain().retirer(choix);
            joueur.getJeu().getCartesEcartees().add(carte);

            joueur.viderChoixPossiblesActions();
            Carte c;
            for(Map.Entry<String, ListeDeCartes> carte_reserve : joueur.getJeu().getReserve().entrySet()) {
                if(!carte_reserve.getValue().isEmpty()) {
                    c = carte_reserve.getValue().get(0);
                    if(c.getFirstType() == Type.TRAIN && c.getCout() <= carte.getCout() + 3)
                        joueur.ajouterChoixPossibleAction("ACHAT:" + c.getNom());
                }
            }
        }
    }
}
