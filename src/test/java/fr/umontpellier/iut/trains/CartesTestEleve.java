package fr.umontpellier.iut.trains;

import org.junit.jupiter.api.Test;

import fr.umontpellier.iut.trains.cartes.*;

import static org.junit.jupiter.api.Assertions.*;

public class CartesTestEleve extends BaseTestClass {

    public void test_achat_carte_victoire(Carte c) {
        setupJeu(c.getNom());
        initialisation();

        Carte fondPioche = new Ferraille();
        Carte f = reserve.get("Ferraille").get(0);

        addAll(pioche, fondPioche);
        joueur.setArgent(c.getCout());

        jouerTourPartiel("ACHAT:" + c.getNom());

        System.out.println(main);
        assertTrue(containsReferences(main));
        assertTrue(containsReferencesInOrder(pioche, fondPioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu));
        boolean carteAppRecue = false;
        for(Carte carte : cartesRecues) {
            if (carte.getNom().equals(c.getNom())) {
                carteAppRecue = true;
                break;
            }
        }
        if(!cartesRecues.contains(f))
            carteAppRecue = false;
        assertTrue(carteAppRecue);
        assertFalse(containsReference(reserve.get("Ferraille"), f));
        assertEquals(0, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }

    @Test
    public void test_achat_carte_victoire_appartement(){
        Carte carte = new Appartement();
        test_achat_carte_victoire(carte);
        assertEquals(1, joueur.getScoreTotal());
    }

    @Test
    public void test_achat_carte_victoire_gratte_ciel(){
        Carte carte = new GratteCiel();
        test_achat_carte_victoire(carte);
        assertEquals(4, joueur.getScoreTotal());
    }

    @Test
    public void test_achat_carte_victoire_immeuble(){
        Carte carte = new Immeuble();
        test_achat_carte_victoire(carte);
        assertEquals(2, joueur.getScoreTotal());
    }
}
