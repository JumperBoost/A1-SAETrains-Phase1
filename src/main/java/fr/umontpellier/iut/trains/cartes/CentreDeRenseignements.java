package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Bouton;
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
        if(joueur.getPeutPiocher()) {
            nbCartes = Math.min(4, joueur.getPioche().size() + joueur.getDefausse().size());
            // Piocher les cartes
            cartes.addAll(joueur.piocher(nbCartes));
            for (Carte carte : cartes) {
                joueur.log("Carte piochée: " + carte.getNom());
                joueur.ajouterBoutonPossibleAction(new Bouton(carte.getNom(), carte.getNom()));
            }
        }
    }

    @Override
    public void jouer(Joueur joueur, String choix) {
        if(joueur.getPeutPasser()) {
            carteChoisis = choix;
            joueur.setPeutPasser(false);
        } else ordreCartes.add(choix); // Ajouter la carte à l'ordre si elle n'est pas la carte choisie

        if(!choix.isEmpty()) {
            joueur.retirerBoutonPossibleAction(choix);
            nbCartes--;
        }

        if(nbCartes == 0) {
            if(cartes.count(carteChoisis) > 0)
                joueur.getMain().add(cartes.retirer(carteChoisis));
            // Remettre les cartes de la pioche sur le dessus dans l'ordre choisi
            for(String nomCarteO : ordreCartes)
                joueur.getPioche().add(0, cartes.retirer(nomCarteO));
            joueur.setCarteAction(null);
            joueur.setPeutPasser(true);
        }
    }
}
