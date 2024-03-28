package fr.umontpellier.iut.trains.cartes;

public class CentreDeControle extends Carte {
    public CentreDeControle() {
        super("Centre de contrôle", 0, 3, Type.ACTION, "Piochez 1 carte puis nommez une carte. Dévoilez la première carte de votre deck. Si c'est la carte nommée, ajoutez-la à votre main. Sinon, remettez-la sur votre deck.");
    }
}
