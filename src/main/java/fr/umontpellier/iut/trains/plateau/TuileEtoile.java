package fr.umontpellier.iut.trains.plateau;

/**
 * Classe représentant une tuile étoile (lieu éloigné)
 */
public class TuileEtoile extends Tuile {
    /**
     * Valeur du lieu éloigné (valeur indiquée sur le plateau)
     */
    private final int valeur;

    public TuileEtoile(int valeur) {
        super(TypeTuile.ETOILE);
        this.valeur = valeur;
    }

    public int getValeur() {
        return valeur;
    }
}
