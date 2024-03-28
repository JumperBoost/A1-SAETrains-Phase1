package fr.umontpellier.iut.trains.cartes;

public class PassageEnGare extends Carte {
    public PassageEnGare() {
        super("Passage en gare", 0, 2, Type.ACTION, "Choisissez 1 parmis ces 3 options :\n---Piochez 1 carte.\n---Recevez 1 poin Valeur.\n---Remettez 1 Ferraille sur la pile Ferraille.");
    }
}
