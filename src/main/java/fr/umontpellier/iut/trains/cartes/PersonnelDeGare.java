package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Bouton;
import fr.umontpellier.iut.trains.Joueur;

public class PersonnelDeGare extends Carte {
    public PersonnelDeGare() {
        super("Personnel de gare", 0, 2, Type.ACTION, "Choisissez 1 parmi ces 3 options : Piochez une carte, Recevez 1 point de valeur, Remettez 1 carte férraille de votre main sur la pile de férraille.");
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);
        joueur.setCarteAction(this);
        joueur.setPeutPasser(false);
        joueur.ajouterBoutonPossibleAction(new Bouton("Piochez", "piocher"));
        joueur.ajouterBoutonPossibleAction(new Bouton("Argent", "argent"));
        joueur.ajouterBoutonPossibleAction(new Bouton("Ferraille", "ferraille"));
    }

    @Override
    public void jouer(Joueur joueur, String choix) {
        switch (choix) {
            case "piocher":
                if(joueur.getPeutPiocher())
                    joueur.getMain().add(joueur.piocher());
                break;
            case "argent":
                joueur.setArgent(joueur.getArgent() + 1);
                break;
            case "ferraille":
                Carte c = joueur.getMain().retirer("Ferraille");
                if(c != null)
                    joueur.getJeu().getReserve().get("Ferraille").add(c);
                break;
        }
        joueur.setCarteAction(null);
        joueur.setPeutPasser(true);
    }
}
