package fr.umontpellier.iut.trains.cartes;

public class PersonnelDeGare extends Carte {
    public PersonnelDeGare() {
        super("Personnel de gare", 0, 2, Type.ACTION, "Choisissez 1 parmi ces 3 options : Piochez une carte, Recevez 1 point de valeur, Remettez 1 carte féraille sur la pile de féraille.");
    }
}
