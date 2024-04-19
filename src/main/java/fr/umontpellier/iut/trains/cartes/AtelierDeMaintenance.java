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
        joueur.setPeutPasser(false);
        for(Carte carte : joueur.getMain())
            if(carte.getFirstType() == Type.TRAIN)
                joueur.ajouterChoixPossibleAction(carte.getNom());
    }

    @Override
    public void jouer(Joueur joueur, String nomCarte) {
        if(!joueur.getJeu().getReserve().get(nomCarte).isEmpty())
            joueur.getCartesRecues().add(joueur.getJeu().getReserve().get(nomCarte).retirer(nomCarte));
        joueur.setCarteAction(null);
        joueur.setPeutPasser(true);
    }

    // PRÉ-REQUIS : Au moins une carte TRAIN dans la main du joueur
    @Override
    public boolean peutJouer(Joueur joueur) {
        return super.peutJouer(joueur) && joueur.getMain().count(Type.TRAIN) > 0;
    }
}
