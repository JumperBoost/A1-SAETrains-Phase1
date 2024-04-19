package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

import java.util.ArrayList;
import java.util.List;

public class CentreDeRenseignements extends Carte {
    private int nbCartes;
    private int nbCartesMax;

    private final List<String> ordreCartes;
    private final ListeDeCartes cartes;
    private String carteChoisis;

    public CentreDeRenseignements() {
        super("Centre de renseignements", 1, 4, Type.ACTION, "Dévoilez les 4 premières cartes de votre deck. Vous pouvez en prendre 1 dans votre main. Remettez les autres sur le dessus de votre deck dans l'ordre de votre choix.");
        nbCartes = 0;
        ordreCartes = new ArrayList<>();
        cartes = new ListeDeCartes();
        carteChoisis = "";
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);
        joueur.setCarteAction(this);
        nbCartesMax = Math.min(4, joueur.getPioche().size() + joueur.getDefausse().size());
        // Piocher les cartes
        cartes.addAll(joueur.piocher(nbCartesMax));
        for(Carte carte : cartes)
            joueur.ajouterChoixPossibleAction(carte.getNom());
    }

    @Override
    public void jouer(Joueur joueur, String nomCarte) {
        if(nbCartes == 0 && joueur.getPeutPasser()) {
            carteChoisis = nomCarte;
            joueur.setPeutPasser(false);
        } else ordreCartes.add(nomCarte); // Ajouter la carte à l'ordre si elle n'est pas la carte choisie

        if(!nomCarte.isEmpty()) {
            joueur.retirerChoixPossibleAction(nomCarte);
            nbCartes++;
        }

        if(nbCartes == nbCartesMax) {
            if(cartes.count(carteChoisis) > 0)
                joueur.getMain().add(cartes.retirer(carteChoisis));
            joueur.setCarteAction(null);
            // Remettre les cartes de la pioche sur le dessus dans l'ordre choisi
            for(String nomCarteO : ordreCartes)
                joueur.getPioche().add(0, cartes.retirer(nomCarteO));
            joueur.setPeutPasser(true);
        }
    }

    // PRÉ-REQUIS : Au moins une carte dans la pioche ou la défausse du joueur
    @Override
    public boolean peutJouer(Joueur joueur) {
        return super.peutJouer(joueur) && (joueur.getPioche().size() + joueur.getDefausse().size()) > 0;
    }
}
