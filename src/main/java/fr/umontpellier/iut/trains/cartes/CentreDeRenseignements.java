package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

import java.util.ArrayList;
import java.util.List;

public class CentreDeRenseignements extends Carte {
    private int nbCartes;
    private final List<String> ordreCartes;
    private final ListeDeCartes cartes;
    private String carteChoisis;

    public CentreDeRenseignements() {
        super("Centre de renseignements", 1, 4, Type.ACTION, "Dévoilez les 4 premières cartes de votre deck. Vous pouvez en prendre 1 dans votre main. Remettez les autres sur le dessus de votre deck dans l'ordre de votre choix.");
        ordreCartes = new ArrayList<>();
        cartes = new ListeDeCartes();
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);
        carteChoisis = "";
        ordreCartes.clear();
        cartes.clear();
        joueur.setCarteAction(this);
        nbCartes = Math.min(4, joueur.getPioche().size() + joueur.getDefausse().size());
        // Piocher les cartes
        cartes.addAll(joueur.piocher(nbCartes));
        for(Carte carte : cartes)
            joueur.ajouterChoixPossibleAction(carte.getNom());
    }

    @Override
    public void jouer(Joueur joueur, String choix) {
        if(joueur.getPeutPasser()) {
            carteChoisis = choix;
            joueur.setPeutPasser(false);
        } else ordreCartes.add(choix); // Ajouter la carte à l'ordre si elle n'est pas la carte choisie

        if(!choix.isEmpty()) {
            joueur.retirerChoixPossibleAction(choix);
            nbCartes--;
        }

        if(nbCartes == 0) {
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
