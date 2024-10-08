package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Bouton;
import fr.umontpellier.iut.trains.Joueur;

import java.util.List;

public class TrainMatinal extends Carte {
    private Carte carteAchete;

    public TrainMatinal() {
        super("Train matinal", 2, 5, List.of(Type.TRAIN, Type.ACTION), Type.TRAIN.getCouleur(), "Vous pouvez placer sur votre deck les cartes que vous achetez durant ce tour.");
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);
        joueur.setBonusTrainMatinal(this);
        carteAchete = null;
    }

    @Override
    public void jouer(Joueur joueur, String choix) {
        if(choix.startsWith("ACHAT:")) {
            if(carteAchete == null) {
                // Prendre une carte dans la réserve
                String nomCarte = choix.split(":")[1];
                Carte carte = joueur.getJeu().prendreDansLaReserve(nomCarte);
                if (carte != null) {
                    // En attente du choix de l'emplacement par le joueur
                    carteAchete = carte;
                    carte.acheter(joueur);
                    joueur.ajouterBoutonPossibleAction(new Bouton("Oui !", "oui"));
                    joueur.ajouterBoutonPossibleAction(new Bouton("Non !", "non"));
                }
            }
        } else if(choix.equals("oui") && carteAchete != null) {
            // Placer la carte achetée dans la pioche
            joueur.getPioche().add(0, carteAchete);
            carteAchete = null;
            joueur.viderBoutonPossiblesAction();
        } else if(choix.equals("non") && carteAchete != null) {
            // Placer la carte achetée dans les cartes reçues
            joueur.getCartesRecues().add(carteAchete);
            carteAchete = null;
            joueur.viderBoutonPossiblesAction();
        } else joueur.executerChoix(choix); // Exécuter le choix du joueur si ce n'est pas un choix de l'effet de la carte
    }
}
