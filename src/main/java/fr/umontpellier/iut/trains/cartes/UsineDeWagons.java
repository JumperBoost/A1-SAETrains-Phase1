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
        joueur.setPeutPasser(false);
        for(Carte carte : joueur.getMain())
            if(carte.getFirstType() == Type.TRAIN)
                joueur.ajouterChoixPossibleAction(carte.getNom());
    }

    @Override
    public void jouer(Joueur joueur, String choix) {
        Carte carte = joueur.getMain().retirer(choix);
        joueur.getJeu().getCartesEcartees().add(carte);
        List<Map.Entry<String, Integer>> cartes_reserve_trains = new ArrayList<>();
        // Récupère les cartes trains de la réserve du joueur par leur nom et leur coût, si elles sont moins chères que la carte écartée + 3
        for(Map.Entry<String, ListeDeCartes> carte_reserve : joueur.getJeu().getReserve().entrySet())
            if(!carte_reserve.getValue().isEmpty() && carte_reserve.getValue().get(0).getFirstType() == Type.TRAIN
                && carte_reserve.getValue().get(0).getCout() <= carte.getCout() + 3)
                cartes_reserve_trains.add(new AbstractMap.SimpleEntry<>(carte_reserve.getKey(), carte_reserve.getValue().get(0).getCout()));
        // Trier les cartes par ordre décroissant de coût
        cartes_reserve_trains.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        // Ajoute à la main du joueur la carte train la plus chère possible
        joueur.getMain().add(joueur.getJeu().prendreDansLaReserve(cartes_reserve_trains.get(0).getKey()));
        joueur.setCarteAction(null);
        joueur.setPeutPasser(true);
    }
}
