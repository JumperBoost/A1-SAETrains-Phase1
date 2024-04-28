package fr.umontpellier.iut.trains.plateau;

/**
 * Classe représentant une tuile plaine, fleuve ou montagne.
 */
public class TuileTerrain extends Tuile {
    /**
     * Type de terrain de la tuile ({@code PLAINE}, {@code FLEUVE} ou {@code MONTAGNE})
     */
    private final TypeTerrain type;

    public TuileTerrain(TypeTerrain type) {
        super(TypeTuile.TERRAIN);
        this.type = type;
    }

    public TypeTerrain getTypeTerrain(){
        return type;
    }
}
