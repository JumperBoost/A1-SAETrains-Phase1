package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public class SalleDeControle extends Carte {
    public SalleDeControle() {
        super("Salle de contrôle", 0, 7, Type.ACTION, "Piochez 3 cartes.");
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);
        int nbCartes = 3;
        Carte c;
        while(nbCartes > 0 && (c = joueur.piocher()) != null){
            joueur.getMain().add(c);
            nbCartes--;
        }
    }
}
