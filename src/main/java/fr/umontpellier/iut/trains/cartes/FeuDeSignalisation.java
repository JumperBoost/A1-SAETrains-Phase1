package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Bouton;
import fr.umontpellier.iut.trains.Joueur;

public class FeuDeSignalisation extends Carte {
    public FeuDeSignalisation() {
        super("Feu de signalisation", 0, 2, Type.ACTION, "Piochez 1 carte puis consultez la première carte de votre deck. Défaussez-la ou replacez-la sur le dessus de votre deck.");
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);
        joueur.setCarteAction(this);
        Carte carte = joueur.piocher();
        if(carte != null)
            joueur.getMain().add(carte);
        if(joueur.getPeutPiocher()) {
            joueur.setPeutPasser(false);
            joueur.log("Carte à défausser: " + joueur.recuperer().getNom());
            joueur.ajouterChoixPossibleAction("oui");
            joueur.ajouterChoixPossibleAction("non");
            joueur.ajouterBoutonPossibleAction(new Bouton("Oui !", "oui"));
            joueur.ajouterBoutonPossibleAction(new Bouton("Non !", "non"));
        } else if(carte != null)
            joueur.setCarteAction(null);    // Si la seconde carte n'est pas piochable, l'effet de la carte est terminé
    }

    @Override
    public void jouer(Joueur joueur, String choix) {
        Carte carte = joueur.piocher();
        if(carte != null) {
            if (choix.equals("oui"))
                joueur.getDefausse().add(carte);
            else joueur.getPioche().add(0, carte);
        }
        joueur.setCarteAction(null);
        joueur.setPeutPasser(true);
    }
}
