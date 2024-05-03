package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public class SalleDeControle extends Carte {
    public SalleDeControle() {
        super("Salle de contrôle", 0, 7, Type.ACTION, "Piochez 3 cartes.");
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);
        int nbCartes = Math.min(3, joueur.getPioche().size() +  joueur.getDefausse().size());
        joueur.getMain().addAll(joueur.piocher(nbCartes));
    }
}
