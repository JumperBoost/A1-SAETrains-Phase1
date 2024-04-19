package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public class Decharge extends Carte {
    public Decharge() {
        super("Décharge", 0, 5, Type.ACTION, "Remettez toutes vos cartes férraille en main sur la pile de férraille.");
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);
        Carte c;
        while((c = joueur.getMain().retirer("Ferraille")) != null)
            joueur.getJeu().getReserve().get("Ferraille").add(c);
    }
}
