package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public class AtelierDeMaintenance extends Carte {
    public AtelierDeMaintenance() {
        super("Atelier de maintenance", 0, 5, Type.ACTION, "Dévoilez une carte Train de votre main. Recevez une carte identique à celle dévoilée (s'il en reste dans la réserve).");
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
        if(joueur.getJeu().estExistantDansLaReserve(choix))
            joueur.getCartesRecues().add(joueur.getJeu().prendreDansLaReserve(choix));
        joueur.setCarteAction(null);
        joueur.setPeutPasser(true);
    }
}
