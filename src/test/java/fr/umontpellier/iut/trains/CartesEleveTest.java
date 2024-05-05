package fr.umontpellier.iut.trains;

import fr.umontpellier.iut.trains.plateau.TuileVille;
import org.junit.jupiter.api.Test;

import fr.umontpellier.iut.trains.cartes.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CartesEleveTest extends BaseTestClass {

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


    /**
     * Tests unitaires provenant du repo public 'turpinb/testspersos' (commit 07f6f158)
     * (https://gitlabinfo.iutmontp.univ-montp2.fr/turpinb/testspersos)
     * Certaines modifications, corrections et suppressions ont été apportées
     */
    @Test
    void test_achat_Appartemment_donne_Ferraille(){
        setupJeu("Appartement");
        initialisation();

        Carte ap = reserve.get("Appartement").get(0);
        Carte f = reserve.get("Ferraille").get(0);
        Carte td = new TrainDirect();

        addAll(main, td);

        jouerTourPartiel("Train direct", "ACHAT:Appartement");

        assertTrue(containsReferences(main));
        assertTrue(containsReferences(cartesEnJeu, td));
        assertTrue(containsReferences(cartesRecues, f, ap));
        assertEquals(0,joueur.getArgent());
    }
    @Test
    void test_achat_Appartemment_donne_Ferraille_set(){
        setupJeu("Appartement");
        initialisation();

        Carte ap = reserve.get("Appartement").get(0);
        Carte f = reserve.get("Ferraille").get(0);
        setAttribute(joueur,"argent",3);

        jouerTourPartiel("ACHAT:Appartement");

        assertTrue(containsReferences(main));
        assertTrue(containsReferences(cartesEnJeu));
        assertTrue(containsReferences(cartesRecues, ap, f));
        assertEquals(0,joueur.getArgent());
    }
    @Test
    void test_achat_Immeuble_donne_Ferraille(){
        setupJeu("Immeuble");
        initialisation();

        Carte im = reserve.get("Immeuble").get(0);
        Carte f = reserve.get("Ferraille").get(0);
        Carte td = new TrainDirect();
        Carte td2 = new TrainDirect();

        addAll(main, td, td2);

        jouerTourPartiel("Train direct","Train direct","ACHAT:Immeuble");

        assertTrue(containsReferences(main));
        assertTrue(containsReferences(cartesEnJeu, td, td2));
        assertTrue(containsReferences(cartesRecues, f, im));
        assertEquals(1,joueur.getArgent());
    }
    @Test
    void test_achat_Immeuble_donne_Ferraille_set(){
        setupJeu("Appartement");
        initialisation();

        Carte im = reserve.get("Immeuble").get(0);
        Carte f = reserve.get("Ferraille").get(0);
        setAttribute(joueur,"argent",5);

        jouerTourPartiel("ACHAT:Immeuble");

        assertTrue(containsReferences(main));
        assertTrue(containsReferences(cartesEnJeu));
        assertTrue(containsReferences(cartesRecues, f, im));
        assertEquals(0,joueur.getArgent());
    }
    @Test
    void test_achat_GratteCiel_donne_Ferraille(){
        setupJeu("Gratte-ciel");
        initialisation();

        Carte gc = reserve.get("Gratte-ciel").get(0);
        Carte f = reserve.get("Ferraille").get(0);
        Carte td = new TrainDirect();
        Carte td2 = new TrainDirect();
        Carte td3 = new TrainDirect();

        addAll(main, td, td2, td3);

        jouerTourPartiel("Train direct","Train direct","Train direct","ACHAT:Gratte-ciel");

        assertTrue(containsReferences(main));
        assertTrue(containsReferences(cartesEnJeu, td, td2, td3));
        assertTrue(containsReferences(cartesRecues, f, gc));
        assertEquals(1,joueur.getArgent());
    }
    @Test
    void test_achat_GratteCiel_donne_Ferraille_set(){
        setupJeu("Appartement");
        initialisation();

        Carte gc = reserve.get("Gratte-ciel").get(0);
        Carte f = reserve.get("Ferraille").get(0);
        setAttribute(joueur,"argent",8);

        jouerTourPartiel("ACHAT:Gratte-ciel");

        assertTrue(containsReferences(main));
        assertTrue(containsReferences(cartesEnJeu));
        assertTrue(containsReferences(cartesRecues, f, gc));
        assertEquals(0,joueur.getArgent());
    }
    @Test
    void test_Aiguillage_ajoute_1() {
        setupJeu("Aiguillage");
        initialisation();

        Carte c = new Aiguillage();
        Carte gare1 = new Gare();

        addAll(main, c);
        addAll(pioche, gare1);

        jouerTourPartiel("Aiguillage");

        assertTrue(containsReferences(main, gare1));
        assertTrue(containsReferencesInOrder(pioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(0, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }
    @Test
    void test_Aiguillage_ajoute_2() {
        setupJeu("Aiguillage");
        initialisation();

        Carte c = new Aiguillage();
        Carte c1 = new Aiguillage();
        Carte gare1 = new Gare();
        Carte c3 = new PoseDeRails();

        addAll(main, c, c1);
        addAll(pioche, gare1,c3);

        jouerTourPartiel("Aiguillage","Aiguillage");

        assertTrue(containsReferences(main, gare1,c3));
        assertTrue(containsReferencesInOrder(pioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c,c1));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(0, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }
    @Test
    void test_Aiguillage_pioche_vide() {
        setupJeu("Aiguillage");
        initialisation();

        Carte c = new Aiguillage();

        addAll(main, c);
        addAll(pioche);

        jouerTourPartiel("Aiguillage");

        assertTrue(containsReferences(main));
        assertTrue(containsReferencesInOrder(pioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(0, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }
    @Test
    void test_AtelierDeMaintenance_pas_possibles() {
        setupJeu("Atelier de maintenance", "Train omnibus");
        initialisation();

        Carte c = new AtelierDeMaintenance();
        Carte fondPioche = new Ferraille();
        for (int i = 0; i < 10; i++) {
            joueurs.get(0).getJeu().prendreDansLaReserve("Train omnibus");
        }
        Carte omni = new TrainOmnibus();

        addAll(main, c, omni);
        addAll(pioche, fondPioche);

        jouerTourPartiel("Atelier de maintenance", "Train omnibus");

        assertTrue(containsReferences(main, omni));
        assertTrue(containsReferencesInOrder(pioche, fondPioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(0, reserve.get("Train omnibus").size());
        assertEquals(0, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }
    @Test
    void test_AtelierDeMaintenance_passe_peut_passer(){
        setupJeu("Atelier de maintenance");
        initialisation();

        Carte adm = new AtelierDeMaintenance();
        Carte rdm = new PoseDeRails();

        addAll(main, adm,rdm);

        jouerTourPartiel("Atelier de maintenance","");

        assertTrue(containsReferences(cartesEnJeu,adm));
        assertTrue(containsReferences(main,rdm));
    }
    @Test
    void test_BureauDuChefDeGare_AtelierDeMaintenance(){
        setupJeu("Atelier de maintenance");
        initialisation();

        Carte bu = new BureauDuChefDeGare();
        Carte c = new AtelierDeMaintenance();
        Carte fondPioche = new Ferraille();
        Carte expr1 = new TrainExpress();
        Carte expr2 = reserve.get("Train express").get(0);

        addAll(main, c, expr1, bu);
        addAll(pioche, fondPioche);

        jouerTourPartiel("Bureau du chef de gare", "Atelier de maintenance", "Train express");

        assertTrue(containsReferences(main, expr1, c));
        assertTrue(containsReferencesInOrder(pioche, fondPioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, bu));
        assertTrue(containsReferences(cartesRecues, expr2));
        assertFalse(containsReference(reserve.get("Train express"), expr2));
        assertEquals(0, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }
    @Test
    void test_BureauDuChefDeGare_BureauDuChefDeGare_BureauDuChefDeGare_passe_peut_passer() {
        setupJeu("Bureau du chef de gare","Ferronnerie");
        initialisation();

        Carte bu = new BureauDuChefDeGare();
        Carte bu1 = new BureauDuChefDeGare();
        Carte bu2 = new BureauDuChefDeGare();
        Carte fondPioche = new Ferraille();

        addAll(main, bu,bu1,bu2);
        addAll(pioche, fondPioche);

        jouerTourPartiel("Bureau du chef de gare","Bureau du chef de gare", "");

        assertTrue(containsReferences(main,bu1,bu2));
        assertTrue(containsReferencesInOrder(pioche, fondPioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, bu));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(0, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }
    @Test
    void test_BureauDuChefDeGare_BureauDuChefDeGare_HorairesEstivaux_ecarte() {
        setupJeu("Horaires estivaux", "Bureau du chef de gare");
        initialisation();

        Carte c = new BureauDuChefDeGare();
        Carte c1 = new BureauDuChefDeGare();
        Carte he = new HorairesEstivaux();
        Carte fondPioche = new Ferraille();

        addAll(main, c, c1, he);
        addAll(pioche, fondPioche);

        jouerTourPartiel("Bureau du chef de gare","Bureau du chef de gare", "Horaires estivaux", "oui");

        assertTrue(containsReferences(main, c1, he));
        assertTrue(containsReferencesInOrder(pioche, fondPioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(3, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
        assertTrue(containsReferences(cartesEcartees, c));
    }
    @Test
    void test_BureauDuChefDeGare_BureauDuChefDeGare_joue() {
        setupJeu("Bureau du chef de gare","Ferronnerie");
        initialisation();

        Carte bu = new BureauDuChefDeGare();
        Carte bu1 = new BureauDuChefDeGare();
        Carte fe = new Ferronnerie();
        Carte fondPioche = new Ferraille();

        addAll(main, bu,bu1,fe);
        addAll(pioche, fondPioche);

        jouerTourPartiel("Bureau du chef de gare", "Bureau du chef de gare","Ferronnerie");

        assertTrue(containsReferences(main, bu1,fe));
        assertTrue(containsReferencesInOrder(pioche, fondPioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, bu));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(0, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }
    @Test
    void test_BureauDuChefDeGare_CabineDuConducteur_0() {
        setupJeu("Bureau du chef de gare", "Cabine du conducteur");
        initialisation();

        Carte c = new BureauDuChefDeGare();
        Carte cc = new CabineDuConducteur();

        addAll(main, c, cc);
        addAll(pioche);

        jouerTourPartiel("Bureau du chef de gare", "Cabine du conducteur", "");

        assertTrue(containsReferences(main, cc));
        assertTrue(containsReferencesInOrder(pioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(0, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }

    @Test
    void test_BureauDuChefDeGare_CabineDuConducteur_1(){
        setupJeu("Cabine du conducteur", "Bureau du chef de gare");
        initialisation();

        Carte c = new BureauDuChefDeGare();
        Carte cc = new CabineDuConducteur();
        Carte fondPioche = new Ferraille();
        Carte omni1 = new TrainOmnibus();
        Carte gare1 = new Gare();

        addAll(main, c, cc, omni1);
        addAll(pioche, gare1, fondPioche);

        jouerTourPartiel("Bureau du chef de gare", "Cabine du conducteur", "Train omnibus", "");

        assertTrue(containsReferences(main, gare1, cc));
        assertTrue(containsReferencesInOrder(pioche, fondPioche));
        assertTrue(containsReferences(defausse, omni1));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(0, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }

    @Test
    void test_BureauDuChefDeGare_CabineDuConducteur_2() {
        setupJeu("Cabine du conducteur", "Bureau du chef de gare");
        initialisation();

        Carte c = new BureauDuChefDeGare();
        Carte cc = new CabineDuConducteur();
        Carte fondPioche = new Ferraille();
        Carte omni1 = new TrainOmnibus();
        Carte omni2 = new TrainOmnibus();
        Carte omni3 = new TrainOmnibus();
        Carte gare1 = new Gare();
        Carte gare2 = new Gare();

        addAll(main, c, omni1, omni2, omni3, cc);
        addAll(pioche, gare1, gare2, fondPioche);

        jouerTourPartiel("Bureau du chef de gare", "Cabine du conducteur", "Train omnibus", "Train omnibus", "");

        assertTrue(containsReferences(main, omni3, gare1, gare2, cc));
        assertTrue(containsReferencesInOrder(pioche, fondPioche));
        assertTrue(containsReferences(defausse, omni1, omni2));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(0, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }
    @Test
    void test_BureauDuChefDeGare_CentreDeControle_gagne() {
        setupJeu("Centre de contrôle", "Bureau du chef de gare", "Train omnibus");
        initialisation();

        Carte c = new BureauDuChefDeGare();
        Carte cc = new CentreDeControle();
        Carte fondPioche = new Ferraille();
        Carte gare = new Gare();
        Carte omni = new TrainOmnibus();

        addAll(main, c, cc);
        addAll(pioche, gare, omni, fondPioche);

        jouerTourPartiel("Bureau du chef de gare", "Centre de contrôle", "Train omnibus");

        assertTrue(containsReferences(main, gare, omni, cc));
        assertTrue(containsReferencesInOrder(pioche, fondPioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(0, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }
    @Test
    void test_BureauDuChefDeGare_CentreDeControle_perdu() {
        setupJeu("Centre de contrôle", "Bureau du chef de gare");
        initialisation();

        Carte c = new BureauDuChefDeGare();
        Carte cc = new CentreDeControle();
        Carte fondPioche = new Ferraille();
        Carte gare = new Gare();
        Carte omni = new TrainOmnibus();

        addAll(main, c, cc);
        addAll(pioche, gare, omni, fondPioche);

        jouerTourPartiel("Bureau du chef de gare", "Centre de contrôle", "Gare");

        assertTrue(containsReferences(main, gare, cc));
        assertTrue(containsReferencesInOrder(pioche, omni, fondPioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(0, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }

    @Test
    void test_BureauDuChefDeGare_CentreDeRenseignements_passe() {
        setupJeu("Centre de renseignements", "Bureau du chef de gare");
        initialisation();

        Carte c = new BureauDuChefDeGare();
        Carte cr = new CentreDeRenseignements();
        Carte fondPioche = new Ferraille();
        Carte imm = new Immeuble();
        Carte omni = new TrainOmnibus();
        Carte gare = new Gare();
        Carte app = new Appartement();

        addAll(main, c, cr);
        addAll(pioche, imm, omni, gare, app, fondPioche);

        jouerTourPartiel("Bureau du chef de gare", "Centre de renseignements", "", "Gare", "Appartement", "Train omnibus", "Immeuble");

        assertTrue(containsReferences(main, cr));
        assertTrue(containsReferencesInOrder(pioche, imm, omni, app, gare, fondPioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(0, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }

    @Test
    void test_BureauDuChefDeGare_CentreDeRenseignements_prend_carte() {
        setupJeu("Centre de renseignements", "Bureau du chef de gare");
        initialisation();

        Carte c = new BureauDuChefDeGare();
        Carte cr = new CentreDeRenseignements();
        Carte fondPioche = new Ferraille();
        Carte imm = new Immeuble();
        Carte omni = new TrainOmnibus();
        Carte gare = new Gare();
        Carte app = new Appartement();

        addAll(main, c, cr);
        addAll(pioche, imm, omni, gare, app, fondPioche);

        jouerTourPartiel("Bureau du chef de gare", "Centre de renseignements", "Gare", "Appartement", "Train omnibus", "Immeuble");

        assertTrue(containsReferences(main, gare, cr));
        assertTrue(containsReferencesInOrder(pioche, imm, omni, app, fondPioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(0, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }
    @Test
    void test_BureauDuChefDeGare_CentreDeRenseignements_prend_carte_pas_assez_pioche() {
        setupJeu("Centre de renseignements", "Bureau du chef de gare");
        initialisation();

        Carte c = new BureauDuChefDeGare();
        Carte cr = new CentreDeRenseignements();
        Carte imm = new Immeuble();
        Carte omni = new TrainOmnibus();

        addAll(main, c, cr);
        addAll(pioche, imm, omni);

        jouerTourPartiel("Bureau du chef de gare", "Centre de renseignements", "Immeuble", "Train omnibus");

        assertTrue(containsReferences(main, imm, cr));
        assertTrue(containsReferencesInOrder(pioche, omni));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(0, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }
    @Test
    void test_BureauDuChefDeGare_Decharge() {
        setupJeu("Décharge", "Bureau du chef de gare");
        initialisation();

        Carte c = new BureauDuChefDeGare();
        Carte de = new Decharge();
        Carte fondPioche = new Ferraille();
        Carte f1 = new Ferraille();
        Carte f2 = new Ferraille();
        Carte omni = new TrainOmnibus();

        addAll(main, c, f1, f2, omni, de);
        addAll(pioche, fondPioche);

        jouerTourPartiel("Bureau du chef de gare", "Décharge");

        assertTrue(containsReferences(main, omni, de));
        assertTrue(containsReferencesInOrder(pioche, fondPioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(0, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));

        List<Carte> pileFerraille = reserve.get("Ferraille");
        assertTrue(containsReference(pileFerraille, f1));
        assertTrue(containsReference(pileFerraille, f2));
    }

    @Test
    void test_BureauDuChefDeGare_Depot() {
        setupJeu("Dépôt", "Bureau du chef de gare");
        initialisation();

        Carte c = new BureauDuChefDeGare();
        Carte de = new Depot();
        Carte fondPioche = new Ferraille();
        Carte imm = new Immeuble();
        Carte omni = new TrainOmnibus();
        Carte expr = new TrainExpress();

        addAll(main, c, imm, de);
        addAll(pioche, omni, expr, fondPioche);

        jouerTourPartiel("Bureau du chef de gare", "Dépôt", "Train express", "Immeuble");

        assertTrue(containsReferences(main, omni, de));
        assertTrue(containsReferencesInOrder(pioche, fondPioche));
        assertTrue(containsReferences(defausse, expr, imm));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(0, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }
    @Test
    void test_BureauDuChefDeGare_Depotoir_PoseDeRails() {
        setupJeu("Dépotoir", "Bureau du chef de gare");
        initialisation();

        Carte c = new BureauDuChefDeGare();
        Carte de = new Depotoir();
        Carte pr = new PoseDeRails();

        addAll(main, c, pr, de);

        jouerTourPartiel("Bureau du chef de gare", "Dépotoir", "Pose de rails");

        assertTrue(containsReferences(main, de));
        assertTrue(containsReferencesInOrder(pioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c, pr));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(0, getArgent(joueur));
        assertEquals(1, getPointsRails(joueur));
    }

    @Test
    void test_BureauDuChefDeGare_Depotoir_PoseDeRails_avec_adv() {
        setupJeu("Dépotoir", "Bureau du chef de gare");
        initialisation();
        tuiles.get(22).ajouterRail(joueur);
        tuiles.get(23).ajouterRail(joueurs.get(0));

        Carte c = new BureauDuChefDeGare();
        Carte de = new Depotoir();
        Carte pr = new PoseDeRails();
        Carte epx = new TrainExpress();
        Carte f = reserve.get("Ferraille").get(0);

        addAll(main, c, pr, epx, de);

        jouerTourPartiel("Train express", "Bureau du chef de gare", "Dépotoir", "Pose de rails", "TUILE:23");

        checkPlateau(List.of(23), List.of(22, 23), null);
        assertTrue(containsReferences(main, de));
        assertTrue(containsReferencesInOrder(pioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c, pr, epx));
        assertTrue(containsReferences(cartesRecues));
        assertTrue(containsReference(reserve.get("Ferraille"), f));
        assertEquals(1, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }

    @Test
    void test_BureauDuChefDeGare_Depotoir_PoseDeRails_avec_adv_compare_le_nombre() {
        setupJeu("Dépotoir", "Bureau du chef de gare");
        initialisation();
        tuiles.get(22).ajouterRail(joueur);
        tuiles.get(23).ajouterRail(joueurs.get(0));

        Carte c = new BureauDuChefDeGare();
        Carte de = new Depotoir();
        Carte pr = new PoseDeRails();
        Carte epx = new TrainExpress();
        int f = reserve.get("Ferraille").count("Ferraille");

        addAll(main, c, pr, epx, de);

        jouerTourPartiel( "Train express", "Bureau du chef de gare", "Dépotoir", "Pose de rails", "TUILE:23");

        checkPlateau(List.of(23), List.of(22, 23), null);
        assertTrue(containsReferences(main, de));
        assertTrue(containsReferencesInOrder(pioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c, pr, epx));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(f,reserve.get("Ferraille").count("Ferraille"));
        assertEquals(1, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }
    @Test
    void test_BureauDuChefDeGare_Echangeur() {
        setupJeu("Bureau du chef de gare", "Échangeur", "Train express");
        initialisation();

        Carte c = new BureauDuChefDeGare();
        Carte fondPioche = new Ferraille();
        Carte ech = new Echangeur();
        Carte expr = new TrainExpress();

        addAll(main, c, expr, ech);
        addAll(pioche, fondPioche);

        jouerTourPartiel("Train express", "Bureau du chef de gare", "Échangeur", "Train express");

        assertTrue(containsReferences(main, ech));
        assertTrue(containsReferencesInOrder(pioche, expr, fondPioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(2, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }

    @Test
    void test_BureauDuChefDeGare_Ferronerie() {
        setupJeu("Bureau du chef de gare");
        initialisation();

        Carte c = new BureauDuChefDeGare();
        Carte fero = new Ferronnerie();
        Carte pr = new PoseDeRails();
        Carte f = reserve.get("Ferraille").get(0);

        addAll(main, c, fero, pr);
        jouerTourPartiel("Bureau du chef de gare", "Ferronnerie", "Pose de rails");

        assertTrue(containsReferences(main, fero));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c, pr));
        assertFalse(containsReference(reserve.get("Ferraille"), f));
        assertEquals(2, getArgent(joueur));
        assertEquals(1, getPointsRails(joueur));
    }
    @Test
    void test_BureauDuChefDeGare_HorairesEstivaux_ecarte() {
        setupJeu("Horaires estivaux", "Bureau du chef de gare");
        initialisation();

        Carte c = new BureauDuChefDeGare();
        Carte he = new HorairesEstivaux();
        Carte fondPioche = new Ferraille();

        addAll(main, c, he);
        addAll(pioche, fondPioche);

        jouerTourPartiel("Bureau du chef de gare", "Horaires estivaux", "oui");

        assertTrue(containsReferences(main, he));
        assertTrue(containsReferencesInOrder(pioche, fondPioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(3, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
        assertTrue(containsReferences(cartesEcartees, c));
    }

    @Test
    void test_BureauDuChefDeGaree_HorairesEstivaux_ecarte_pas() {
        setupJeu("Horaires estivaux", "Bureau du chef de gare");
        initialisation();

        Carte c = new BureauDuChefDeGare();
        Carte he = new HorairesEstivaux();
        Carte fondPioche = new Ferraille();

        addAll(main, c, he);
        addAll(pioche, fondPioche);

        jouerTourPartiel("Bureau du chef de gare", "Horaires estivaux", "non");

        assertTrue(containsReferences(main, he));
        assertTrue(containsReferencesInOrder(pioche, fondPioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(0, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
        assertTrue(containsReferences(cartesEcartees));
    }

    @Test
    void test_BureauDuChefDeGare_HorairesTemporaires() {
        setupJeu("Horaires temporaires", "Bureau du chef de gare");
        initialisation();

        Carte c = new BureauDuChefDeGare();
        Carte ht = new HorairesTemporaires();
        Carte fondPioche = new Ferraille();
        Carte omni = new TrainOmnibus();
        Carte expr = new TrainExpress();
        Carte gare = new Gare();
        Carte imm = new Immeuble();

        addAll(main, c, ht);
        addAll(pioche, omni, gare, imm, expr, fondPioche);

        jouerTourPartiel("Bureau du chef de gare", "Horaires temporaires");

        assertTrue(containsReferences(main, omni, expr, ht));
        assertTrue(containsReferencesInOrder(pioche, fondPioche));
        assertTrue(containsReferences(defausse, gare, imm));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(0, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }

    @Test
    void test_BureauDuChefDeGare_FeuDeSignalisation_defausse() {
        setupJeu("Feu de signalisation", "Bureau du chef de gare");
        initialisation();

        Carte c = new BureauDuChefDeGare();
        Carte fs = new FeuDeSignalisation();
        Carte fondPioche = new Ferraille();
        Carte gare = new Gare();
        Carte imm = new Immeuble();

        addAll(main, c, fs);
        addAll(pioche, gare, imm, fondPioche);

        jouerTourPartiel("Bureau du chef de gare", "Feu de signalisation", "oui");

        assertTrue(containsReferences(main, gare, fs));
        assertTrue(containsReferencesInOrder(pioche, fondPioche));
        assertTrue(containsReferences(defausse, imm));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(0, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }

    @Test
    void test_BureauDuChefDeGare_FeuDeSignalisation_replace() {
        setupJeu("Feu de signalisation", "Bureau du chef de gare");
        initialisation();

        Carte c = new BureauDuChefDeGare();
        Carte fs = new FeuDeSignalisation();
        Carte fondPioche = new Ferraille();
        Carte gare = new Gare();
        Carte imm = new Immeuble();

        addAll(main, c, fs);
        addAll(pioche, gare, imm, fondPioche);

        jouerTourPartiel("Bureau du chef de gare", "Feu de signalisation", "non");

        assertTrue(containsReferences(main, gare, fs));
        assertTrue(containsReferencesInOrder(pioche, imm, fondPioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(0, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }
    @Test
    void test_BureauDuChefDeGare_ParcDattractions() {
        setupJeu("Parc d'attractions", "Bureau du chef de gare");
        initialisation();

        Carte c = new BureauDuChefDeGare();
        Carte pa = new ParcDAttractions();
        Carte fondPioche = new Ferraille();
        Carte omni = new TrainOmnibus();
        Carte expr = new TrainExpress();

        addAll(main, c, omni, expr, pa);
        addAll(pioche, fondPioche);

        jouerTourPartiel("Train omnibus", "Train express", "Bureau du chef de gare", "Parc d'attractions", "Train express");

        assertTrue(containsReferences(main, pa));
        assertTrue(containsReferencesInOrder(pioche, fondPioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c, omni, expr));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(5, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }
    @Test
    void test_BureauDuChefDeGare_passe_mais_peut_passer(){
        setupJeu("Bureau du chef de gare","Train omnibus");
        initialisation();

        Carte adm = new BureauDuChefDeGare();
        Carte omni = new TrainOmnibus();

        addAll(main,adm,omni);

        jouerTourPartiel("Bureau du chef de gare","");

        assertTrue(containsReferences(main,omni));
        assertTrue(containsReferences(cartesEnJeu,adm));
    }
    @Test
    void test_BureauDuChefDeGare_PassageEnGare() {
        setupJeu("Bureau du chef de gare");
        initialisation();

        Carte c = new BureauDuChefDeGare();
        Carte fondPioche = new Ferraille();
        Carte aig = new SalleDeControle();
        Carte gare1 = new Gare();
        Carte gare2 = new Gare();
        Carte gare3 = new Gare();

        addAll(main, c, aig);
        addAll(pioche, gare1, gare2, gare3, fondPioche);
        jouerTourPartiel("Bureau du chef de gare", "Salle de contrôle");

        assertTrue(containsReferences(main, aig, gare1, gare2, gare3));
        assertTrue(containsReferencesInOrder(pioche, fondPioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(0, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }
    @Test
    void test_BureauDuChefDeGare_PersonnelDeGare_argent() {
        setupJeu("Personnel de gare", "Bureau du chef de gare");
        initialisation();

        Carte c = new BureauDuChefDeGare();
        Carte pe = new PersonnelDeGare();
        Carte fondPioche = new Ferraille();
        Carte ferraille = new Ferraille();
        Carte gare = new Gare();

        addAll(main, c, ferraille, pe);
        addAll(pioche, gare, fondPioche);

        jouerTourPartiel("Bureau du chef de gare", "Personnel de gare", "argent");

        assertTrue(containsReferences(main, ferraille, pe));
        assertTrue(containsReferencesInOrder(pioche, gare, fondPioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(1, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }

    @Test
    void test_BureauDuChefDeGare_PersonnelDeGare_Ferraille() {
        setupJeu("Personnel de gare", "Bureau du chef de gare");
        initialisation();

        Carte c = new BureauDuChefDeGare();
        Carte pe = new PersonnelDeGare();
        Carte fondPioche = new Ferraille();
        Carte ferraille = new Ferraille();
        Carte gare = new Gare();

        addAll(main, c, ferraille, pe);
        addAll(pioche, gare, fondPioche);

        jouerTourPartiel("Bureau du chef de gare", "Personnel de gare", "ferraille");

        assertTrue(containsReferences(main, pe));
        assertTrue(containsReferencesInOrder(pioche, gare, fondPioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(0, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
        assertTrue(containsReference(reserve.get("Ferraille"), ferraille));
    }

    @Test
    void test_BureauDuChefDeGare_PersonnelDeGare_piocher() {
        setupJeu("Personnel de gare", "Bureau du chef de gare");
        initialisation();

        Carte c = new BureauDuChefDeGare();
        Carte pe = new PersonnelDeGare();
        Carte fondPioche = new Ferraille();
        Carte ferraille = new Ferraille();
        Carte gare = new Gare();

        addAll(main, c, ferraille, pe);
        addAll(pioche, gare, fondPioche);

        jouerTourPartiel("Bureau du chef de gare", "Personnel de gare", "piocher");

        assertTrue(containsReferences(main, ferraille, gare, pe));
        assertTrue(containsReferencesInOrder(pioche, fondPioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(0, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }
    @Test
    void test_BureauDuChefDeGare_Remorquage() {
        setupJeu("Remorquage", "Bureau du chef de gare");
        initialisation();

        Carte c = new BureauDuChefDeGare();
        Carte r = new Remorquage();
        Carte fondPioche = new Ferraille();
        Carte gare = new Gare();
        Carte omni = new TrainOmnibus();
        Carte expr = new TrainExpress();
        Carte imm = new Immeuble();

        addAll(main, c, r);
        addAll(pioche, fondPioche);
        addAll(defausse, gare, omni, expr, imm);

        jouerTourPartiel("Bureau du chef de gare", "Remorquage", "Train express");

        assertTrue(containsReferences(main, expr, r));
        assertTrue(containsReferencesInOrder(pioche, fondPioche));
        assertTrue(containsReferences(defausse, gare, omni, imm));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(0, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }
    @Test
    void test_BureauDuChefDeGare_Remorquage_peut_passer() {
        setupJeu("Remorquage", "Bureau du chef de gare");
        initialisation();

        Carte c = new BureauDuChefDeGare();
        Carte r = new Remorquage();
        Carte fondPioche = new Ferraille();

        addAll(main, c, r);
        addAll(pioche, fondPioche);

        jouerTourPartiel("Bureau du chef de gare", "Remorquage", "");

        assertTrue(containsReferences(main, r));
        assertTrue(containsReferencesInOrder(pioche, fondPioche));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(0, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }

    @Test
    void test_BureauDuChefDeGare_SalleDeControle() {
        setupJeu("Bureau du chef de gare");
        initialisation();

        Carte c = new BureauDuChefDeGare();
        Carte fondPioche = new Ferraille();
        Carte aig = new PassageEnGare();
        Carte gare1 = new Gare();
        Carte gare2 = new Gare();
        Carte gare3 = new Gare();

        addAll(main, c, aig);
        addAll(pioche, gare1, gare2, gare3, fondPioche);
        jouerTourPartiel("Bureau du chef de gare", "Passage en gare");

        assertTrue(containsReferences(main, aig, gare1));
        assertTrue(containsReferencesInOrder(pioche, gare2, gare3, fondPioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(0, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }

    @Test
    void test_BureauDuChefDeGare_TGV_avec_TrainOmnibus() {
        setupJeu("Bureau du chef de gare");
        initialisation();

        Carte c = new BureauDuChefDeGare();
        Carte fondPioche = new Ferraille();
        Carte tt = new TGV();
        Carte omni = new TrainOmnibus();

        addAll(main, c, tt, omni);
        addAll(pioche, fondPioche);
        jouerTourPartiel("Train omnibus", "Bureau du chef de gare" ,"TGV");

        assertTrue(containsReferences(main, tt));
        assertTrue(containsReferencesInOrder(pioche, fondPioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c, omni));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(2, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }

    @Test
    void test_BureauDuChefDeGare_TGV_avec_2_TrainOmnibus() {
        setupJeu("Bureau du chef de gare");
        initialisation();

        Carte c = new BureauDuChefDeGare();
        Carte fondPioche = new Ferraille();
        Carte tt = new TGV();
        Carte omni = new TrainOmnibus();
        Carte omni2 = new TrainOmnibus();

        addAll(main, c, tt, omni, omni2);
        addAll(pioche, fondPioche);
        jouerTourPartiel("Train omnibus" ,"Train omnibus", "Bureau du chef de gare", "TGV");

        assertTrue(containsReferences(main, tt));
        assertTrue(containsReferencesInOrder(pioche, fondPioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c, omni, omni2));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(3, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }

    @Test
    void test_BureauDuChefDeGare_TGV_sans_TrainOmnibus() {
        setupJeu("Bureau du chef de gare");
        initialisation();

        Carte c = new BureauDuChefDeGare();
        Carte fondPioche = new Ferraille();
        Carte tt = new TGV();

        addAll(main, c, tt);
        addAll(pioche, fondPioche);
        jouerTourPartiel("Bureau du chef de gare", "TGV");

        assertTrue(containsReferences(main, tt));
        assertTrue(containsReferencesInOrder(pioche, fondPioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(0, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }

    @Test
    void test_BureauDuChefDeGare_TrainDeMarchandises() {
        setupJeu("Bureau du chef de gare");
        initialisation();

        Carte c = new BureauDuChefDeGare();
        Carte fondPioche = new Ferraille();
        Carte tt = new TrainDeMarchandises();
        Carte f1 = new Ferraille();
        Carte f2 = new Ferraille();
        Carte f3 = new Ferraille();
        int nbFerraille = reserve.get("Ferraille").size();

        addAll(main, c, tt,  f1, f2, f3);
        addAll(pioche, fondPioche);
        jouerTourPartiel("Bureau du chef de gare", "Train de marchandises", "Ferraille", "Ferraille", "");

        assertTrue(containsReferences(main, tt, f3));
        assertTrue(containsReferencesInOrder(pioche, fondPioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(2, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
        assertEquals(reserve.get("Ferraille").size(), nbFerraille + 2);
    }

    @Test
    void test_BureauDuChefDeGare_TrainDeTourisme() {
        setupJeu("Bureau du chef de gare");
        initialisation();

        Carte c = new BureauDuChefDeGare();
        Carte fondPioche = new Ferraille();
        Carte tt = new TrainDeTourisme();

        addAll(main, c, tt);
        addAll(pioche, fondPioche);
        jouerTourPartiel("Bureau du chef de gare", "Train de tourisme");

        assertTrue(containsReferences(main, tt));
        assertTrue(containsReferencesInOrder(pioche, fondPioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(0, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
        assertEquals(1, joueur.getScoreTotal());
    }
    @Test
    void test_BureauDuChefDeGare_TrainMatinal_non() {
        setupJeu("Bureau du chef de gare", "Aiguillage", "Train matinal", "Train omnibus");
        initialisation();

        Carte bu = new BureauDuChefDeGare();
        Carte tt = new TrainMatinal();

        Carte c = reserve.get("Aiguillage").get(0);

        Carte omni1 = new TrainOmnibus();
        Carte omni2 = new TrainOmnibus();
        Carte omni3 = new TrainOmnibus();
        Carte omni4 = new TrainOmnibus();
        Carte omni5 = new TrainOmnibus();

        List<String> instructions = new ArrayList<>();

        for (int i = 0; i < 5 + 1; i++) {
            instructions.add("Train omnibus");
        }

        instructions.add("Bureau du chef de gare");
        instructions.add("Train matinal");
        instructions.add("ACHAT:" + "Aiguillage");
        instructions.add("non");

        addAll(main, bu, tt, omni1, omni2, omni3, omni4, omni5);
        jouerTourPartiel(instructions);

        assertTrue(containsReferences(main, tt ));
        assertTrue(containsReferencesInOrder(pioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, bu,  omni1, omni2, omni3, omni4, omni5));
        assertTrue(containsReferences(cartesRecues, c));
        assertEquals(0, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }

    @Test
    void test_BureauDuChefDeGare_TrainMatinal_oui() {
        setupJeu("Bureau du chef de gare", "Aiguillage", "Train matinal", "Train omnibus");
        initialisation();

        Carte bu = new BureauDuChefDeGare();
        Carte tt = new TrainMatinal();

        Carte c = reserve.get("Aiguillage").get(0);

        Carte omni1 = new TrainOmnibus();
        Carte omni2 = new TrainOmnibus();
        Carte omni3 = new TrainOmnibus();
        Carte omni4 = new TrainOmnibus();
        Carte omni5 = new TrainOmnibus();

        List<String> instructions = new ArrayList<>();

        for (int i = 0; i < 5 + 1; i++) {
            instructions.add("Train omnibus");
        }

        instructions.add("Bureau du chef de gare");
        instructions.add("Train matinal");
        instructions.add("ACHAT:" + "Aiguillage");
        instructions.add("oui");

        addAll(main, bu, tt, omni1, omni2, omni3, omni4, omni5);
        jouerTourPartiel(instructions);

        assertTrue(containsReferences(main, tt ));
        assertTrue(containsReferencesInOrder(pioche, c));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, bu, omni1, omni2, omni3, omni4, omni5));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(0, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }

    @Test
    void test_BureauDuChefDeGare_TrainPostal() {
        setupJeu("Bureau du chef de gare");
        initialisation();

        Carte c = new BureauDuChefDeGare();
        Carte tt = new TrainPostal();
        Carte im = new Immeuble();
        Carte f = new Ferraille();

        addAll(main, c, tt, im, f);
        addAll(pioche);
        jouerTourPartiel("Bureau du chef de gare", "Train postal", "Immeuble", "Ferraille", "");

        assertTrue(containsReferences(main, tt));
        assertTrue(containsReferencesInOrder(pioche));
        assertTrue(containsReferences(defausse, im, f));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(2, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }
    @Test
    void test_CabineDuConducteur_defausse_ferraille(){
        setupJeu("Cabine du conducteur");
        initialisation();

        Carte cdc = new CabineDuConducteur();
        Carte fe = new Ferraille();
        Carte fond = new TrainOmnibus();
        Carte gare = new Gare();
        Carte gare1 = new Gare();

        addAll(main, cdc, fe, fond);
        addAll(pioche, gare, gare1);

        jouerTourPartiel("Cabine du conducteur","Ferraille","");

        assertTrue(containsReferences(main, fond, gare));
        assertTrue(containsReferences(defausse, fe));
        assertTrue(containsReferences(cartesEnJeu, cdc));
        assertTrue(containsReferences(pioche, gare1));
    }
    @Test
    void test_calcul_fin() {
        setupJeu("Train de tourisme");
        initialisation();
        for (int i = 0; i < 19; i++) {
            tuiles.get(i).ajouterRail(joueur);
            joueur.retirerJetonsRails();

        }
        ((TuileVille) tuiles.get(1)).ajouterGare();
        for (int i = 0; i < 3; i++) {
            ((TuileVille) tuiles.get(8)).ajouterGare();
        }
        for (int i = 0; i < 2; i++) {
            ((TuileVille) tuiles.get(11)).ajouterGare();
        }
        ((TuileVille) tuiles.get(13)).ajouterGare(); //27 points pour les rails



        Carte c = new TrainDeTourisme(); // 1
        setAttribute(joueur, "argent", 40);
        addAll(main, c);

        jouerTourPartiel("ACHAT:Immeuble","ACHAT:Appartement","ACHAT:Gratte-ciel","Train de tourisme","");

        System.out.println(joueur.getScoreTotal());

        tuiles.get(20).ajouterRail(joueur);
        joueur.retirerJetonsRails();

        assertEquals(0, getPointsRails(joueur));
        assertEquals(0, getNbJetonsRails(joueur));
        assertEquals(35, joueur.getScoreTotal());
    }

    @Test
    void test_calcul_fin_toute_pioche() {
        setupJeu();
        initialisation();
        for (int i = 0; i < 20; i++) {
            tuiles.get(i).ajouterRail(joueur);
            joueur.retirerJetonsRails();
        }
        ((TuileVille) tuiles.get(1)).ajouterGare();
        for (int i = 0; i < 3; i++) {
            ((TuileVille) tuiles.get(8)).ajouterGare();
            joueur.getJeu().desincrementerNbJetonsGare();
        }
        for (int i = 0; i < 2; i++) {
            ((TuileVille) tuiles.get(11)).ajouterGare();
            joueur.getJeu().desincrementerNbJetonsGare();
        }
        ((TuileVille) tuiles.get(13)).ajouterGare(); // points pour les rails
        joueur.getJeu().desincrementerNbJetonsGare();

        Carte im = new Immeuble();
        Carte ap = new Appartement();
        Carte gr = new GratteCiel();
        addAll(main, ap);
        addAll(defausse, im);
        addAll(pioche, gr);

        assertTrue(containsReferences(main, ap));
        assertTrue(containsReferences(pioche, gr));
        assertTrue(containsReferences(defausse, im));
        assertEquals(0, getPointsRails(joueur));
        assertEquals(0, getNbJetonsRails(joueur));
        assertEquals(34, joueur.getScoreTotal());
    }

    @Test
    void test_CentreDeRenseignement_remettrePioche_carteChoisie()
    {
        setupJeu();
        initialisation();

        Carte c = new CentreDeRenseignements();

        Carte omni1 = new TrainOmnibus();
        Carte pdg = new PersonnelDeGare();
        Carte v = new Viaduc();
        Carte omni2 = new TrainOmnibus();
        Carte fondPioche = new Ferraille();

        addAll(pioche, omni1, pdg, v, omni2, fondPioche);
        addAll(main,c);

        jouerTourPartiel("Centre de renseignements", "Personnel de gare", "Personnel de gare", "Viaduc", "Train omnibus", "Train omnibus");

        assertTrue(containsReferences(main,pdg));
        assertTrue(containsReferencesInOrder(pioche,omni2,omni1,v,fondPioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu,c));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(0, getPointsRails(joueur));
        assertEquals(1, getArgent(joueur));
    }
    @Test
    void test_Cooperation_avecadv() {
        setupJeu("Coopération");
        initialisation();
        tuiles.get(22).ajouterRail(joueur);
        tuiles.get(23).ajouterRail(joueurs.get(0));

        Carte c = new Cooperation();
        Carte epx = new TrainExpress();
        Carte f = reserve.get("Ferraille").get(0);

        addAll(main, c, epx);
        jouerTourPartiel("Train express", "Coopération", "TUILE:23");

        checkPlateau(List.of(23), List.of(22, 23), null);
        assertTrue(containsReferences(main));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c, epx));
        assertFalse(containsReference(reserve.get("Ferraille"), f));
        assertEquals(2, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }

    @Test
    void test_Cooperation_sansadv() {
        setupJeu("Coopération");
        initialisation();
        tuiles.get(22).ajouterRail(joueur);

        Carte c = new Cooperation();
        Carte f = reserve.get("Ferraille").get(0);

        addAll(main, c);
        jouerTourPartiel("Coopération",  "TUILE:23");

        checkPlateau(null, List.of(22, 23), null);
        assertTrue(containsReferences(main));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertFalse(containsReference(reserve.get("Ferraille"), f));
        assertEquals(0, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }

    @Test
    void test_Cooperation_sans_Cooperation_avecadv() {
        setupJeu("Pose de rails");
        initialisation();
        tuiles.get(22).ajouterRail(joueur);
        tuiles.get(23).ajouterRail(joueurs.get(0));

        Carte c = new PoseDeRails();
        Carte epx = new TrainExpress();
        Carte f = reserve.get("Ferraille").get(0);
        Carte f2 = reserve.get("Ferraille").get(1);

        addAll(main, c, epx);
        jouerTourPartiel("Train express");
        System.out.println(main);
        jouerTourPartiel("Pose de rails");
        System.out.println(main);
        jouerTourPartiel("TUILE:23");

        checkPlateau(List.of(23), List.of(22,23), null);
        System.out.println(main);
        assertTrue(containsReferences(main));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c, epx));
        assertFalse(containsReference(reserve.get("Ferraille"), f));
        assertFalse(containsReference(reserve.get("Ferraille"), f2));
        assertEquals(1, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }
    @Test
    void test_Depotoir_Appartement() {
        setupJeu("Dépotoir","Appartement");
        initialisation();

        Carte c = new Depotoir();
        Carte a = reserve.get("Appartement").get(0);
        Carte f = reserve.get("Ferraille").get(0);
        setAttribute(joueur,"argent",10);

        addAll(main, c);

        jouerTourPartiel("Dépotoir", "ACHAT:"+a.getNom());

        assertTrue(containsReferences(main));
        assertTrue(containsReferencesInOrder(pioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertTrue(containsReferences(cartesRecues, a));
        assertFalse(containsReference(cartesRecues, f));
    }
    @Test
    void test_Depotoir_Immeuble() {
        setupJeu("Dépotoir","Immeuble");
        initialisation();

        Carte c = new Depotoir();
        Carte a = reserve.get("Immeuble").get(0);
        Carte f = reserve.get("Ferraille").get(0);
        setAttribute(joueur,"argent",10);

        addAll(main, c);

        jouerTourPartiel("Dépotoir", "ACHAT:"+a.getNom());

        assertTrue(containsReferences(main));
        assertTrue(containsReferencesInOrder(pioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertTrue(containsReferences(cartesRecues, a));
        assertFalse(containsReference(cartesRecues, f));
    }
    @Test
    void test_Depotoir_GratteCiel() {
        setupJeu("Dépotoir","Gratte-ciel");
        initialisation();

        Carte c = new Depotoir();
        Carte a = reserve.get("Gratte-ciel").get(0);
        Carte f = reserve.get("Ferraille").get(0);
        setAttribute(joueur,"argent",10);

        addAll(main, c);

        jouerTourPartiel("Dépotoir", "ACHAT:"+a.getNom());

        assertTrue(containsReferences(main));
        assertTrue(containsReferencesInOrder(pioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertTrue(containsReferences(cartesRecues, a));
        assertFalse(containsReference(cartesRecues, f));
    }
    @Test
    void test_Depotoir_PoseDeRails() {
        setupJeu("Dépotoir");
        initialisation();

        Carte c = new Depotoir();
        Carte a = new PoseDeRails();

        addAll(main, c, a);

        jouerTourPartiel("Dépotoir", "Pose de rails");

        assertTrue(containsReferences(main));
        assertTrue(containsReferencesInOrder(pioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c, a));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(1, getArgent(joueur));
        assertEquals(1, getPointsRails(joueur));
    }

    @Test
    void test_Depotoir_PoseDeRails_avec_adv() {
        setupJeu("Dépotoir");
        initialisation();
        tuiles.get(22).ajouterRail(joueur);
        tuiles.get(23).ajouterRail(joueurs.get(0));

        Carte c = new Depotoir();
        Carte pr = new PoseDeRails();
        Carte epx = new TrainExpress();
        Carte f = reserve.get("Ferraille").get(0);

        addAll(main, c, pr, epx);

        jouerTourPartiel("Train express", "Dépotoir", "Pose de rails", "TUILE:23");

        checkPlateau(List.of(23), List.of(22, 23), null);
        assertTrue(containsReferences(main));
        assertTrue(containsReferencesInOrder(pioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c, pr, epx));
        assertTrue(containsReferences(cartesRecues));
        assertTrue(containsReference(reserve.get("Ferraille"), f));
        assertEquals(2, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }
    @Test
    void test_Depotoir_PoseDeRails_avec_adv_compare_le_nombre() {
        setupJeu("Dépotoir");
        initialisation();
        tuiles.get(22).ajouterRail(joueur);
        tuiles.get(23).ajouterRail(joueurs.get(0));

        Carte c = new Depotoir();
        Carte pr = new PoseDeRails();
        Carte epx = new TrainExpress();
        int f = reserve.get("Ferraille").count("Ferraille");

        addAll(main, c, pr, epx);

        jouerTourPartiel("Train express", "Dépotoir", "Pose de rails", "TUILE:23");

        checkPlateau(List.of(23), List.of(22, 23), null);
        assertTrue(containsReferences(main));
        assertTrue(containsReferencesInOrder(pioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c, pr, epx));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(f,reserve.get("Ferraille").count("Ferraille"));
        assertEquals(2, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }
    @Test
    void test_fin_de_partie_3_piles_pas_fini(){
        setupJeu("Atelier de maintenance", "Bureau du chef de gare", "Ferronnerie");
        initialisation();

        while(reserve.get("Atelier de maintenance").count("Atelier de maintenance") > 1){
            reserve.get("Atelier de maintenance").remove(0);
        }
        while(reserve.get("Bureau du chef de gare").count("Bureau du chef de gare") > 1){
            reserve.get("Bureau du chef de gare").remove(0);
        }
        while(reserve.get("Ferraille").count("Ferraille") > 1){
            reserve.get("Ferraille").remove(0);
        }
        setAttribute(joueur, "argent", 50);

        jouerTourPartiel("ACHAT:Atelier de maintenance", "ACHAT:Bureau du chef de gare", "ACHAT:Ferronnerie","");

        assertFalse(jeu.estFini());
    }
    @Test
    void test_fin_de_partie_4_piles_fini(){
        setupJeu("Atelier de maintenance", "Bureau du chef de gare", "Ferronnerie","Tunnel");
        initialisation();

        setAttribute(joueur, "argent", 50);

        while(reserve.get("Atelier de maintenance").count("Atelier de maintenance") > 1){
            reserve.get("Atelier de maintenance").remove(0);
        }
        while(reserve.get("Bureau du chef de gare").count("Bureau du chef de gare") > 1){
            reserve.get("Bureau du chef de gare").remove(0);
        }
        while(reserve.get("Ferronnerie").count("Ferronnerie") > 1){
            reserve.get("Ferronnerie").remove(0);
        }
        while(reserve.get("Tunnel").count("Tunnel") > 1){
            reserve.get("Tunnel").remove(0);
        }


        jouerTourPartiel("ACHAT:Atelier de maintenance", "ACHAT:Bureau du chef de gare", "ACHAT:Ferronnerie","ACHAT:Tunnel","");
        assertTrue(jeu.estFini());
    }
    @Test
    void test_fin_de_partie_4_piles_pas_fini(){
        setupJeu("Atelier de maintenance", "Bureau du chef de gare", "Immeuble");
        initialisation();

        setAttribute(joueur, "argent", 50);

        while(reserve.get("Atelier de maintenance").count("Atelier de maintenance") > 1){
            reserve.get("Atelier de maintenance").remove(0);
        }
        while(reserve.get("Bureau du chef de gare").count("Bureau du chef de gare") > 1){
            reserve.get("Bureau du chef de gare").remove(0);
        }
        while(reserve.get("Immeuble").count("Immeuble") > 1){
            reserve.get("Immeuble").remove(0);
        }
        while(reserve.get("Ferraille").count("Ferraille") > 2){
            reserve.get("Ferraille").remove(0);
        }


        jouerTourPartiel("ACHAT:Atelier de maintenance", "ACHAT:Bureau du chef de gare", "ACHAT:Immeuble","");
        assertFalse(jeu.estFini());
    }
    @Test
    void test_Ferronnerie() {
        setupJeu("Ferronnerie");
        initialisation();

        Carte fero1 = new Ferronnerie();
        Carte pr = new PoseDeRails();
        Carte f = reserve.get("Ferraille").get(0);

        addAll(main, fero1, pr);
        jouerTourPartiel("Ferronnerie", "Pose de rails");

        assertTrue(containsReferences(main));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, pr, fero1));
        assertFalse(containsReference(reserve.get("Ferraille"), f));
        assertEquals(3, getArgent(joueur));
        assertEquals(1, getPointsRails(joueur));
    }
    @Test
    void test_Ferronnerie_effet_cumule() {
        setupJeu("Ferronnerie");
        initialisation();

        Carte fero1 = new Ferronnerie();
        Carte fero2 = new Ferronnerie();
        Carte pr = new PoseDeRails();
        Carte f = reserve.get("Ferraille").get(0);

        addAll(main, fero1, fero2, pr);
        jouerTourPartiel("Ferronnerie", "Ferronnerie", "Pose de rails");

        assertTrue(containsReferences(main));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, pr, fero1, fero2));
        assertFalse(containsReference(reserve.get("Ferraille"), f));
        assertEquals(6, getArgent(joueur));
        assertEquals(1, getPointsRails(joueur));
    }

    @Test
    void test_Ferronnerie_effet_cumule_2() {
        setupJeu("Ferronnerie");
        initialisation();

        Carte fero1 = new Ferronnerie();
        Carte fero2 = new Ferronnerie();
        Carte fero3 = new Ferronnerie();
        Carte pr = new PoseDeRails();
        Carte f = reserve.get("Ferraille").get(0);

        addAll(main, fero1, fero2, fero3, pr);
        jouerTourPartiel("Ferronnerie", "Ferronnerie", "Ferronnerie" , "Pose de rails");

        assertTrue(containsReferences(main));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, pr, fero1, fero2, fero3));
        assertFalse(containsReference(reserve.get("Ferraille"), f));
        assertEquals(9, getArgent(joueur));
        assertEquals(1, getPointsRails(joueur));
    }

    @Test
    void test_Ferronnerie_effet_cumule_3() {
        setupJeu("Ferronnerie");
        initialisation();

        Carte fero1 = new Ferronnerie();
        Carte fero2 = new Ferronnerie();
        Carte fero3 = new Ferronnerie();
        Carte fero4 = new Ferronnerie();
        Carte pr = new PoseDeRails();
        Carte f = reserve.get("Ferraille").get(0);

        addAll(main, fero1, fero2, fero3, fero4 , pr);
        jouerTourPartiel("Ferronnerie", "Ferronnerie", "Ferronnerie" , "Ferronnerie" , "Pose de rails");

        assertTrue(containsReferences(main));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, pr, fero1, fero2, fero3, fero4));
        assertFalse(containsReference(reserve.get("Ferraille"), f));
        assertEquals(12, getArgent(joueur));
        assertEquals(1, getPointsRails(joueur));
    }

    @Test
    void test_Ferronnerie_plusieurs_CarteVerte() {
        setupJeu("Ferronnerie");
        initialisation();

        Carte fero1 = new Ferronnerie();
        Carte pr = new PoseDeRails();
        Carte pt = new PontEnAcier();
        Carte vi = new Viaduc();
        Carte tu = new Tunnel();
        Carte vo = new VoieSouterraine();
        Carte co = new Cooperation();
        Carte f = reserve.get("Ferraille").get(0);

        addAll(main, fero1, pr, pt, vi, tu, vo, co);
        jouerTourPartiel("Ferronnerie", "Pose de rails", "Pont en acier", "Tunnel", "Viaduc", "Voie souterraine", "Coopération");

        assertTrue(containsReferences(main));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, pr, fero1, pt, vi, tu, vo, co));
        assertFalse(containsReference(reserve.get("Ferraille"), f));
        assertEquals(13, getArgent(joueur));
        assertEquals(6, getPointsRails(joueur));
    }

    @Test
    void test_Ferronnerie_plusieurs_rails() {
        setupJeu("Ferronnerie");
        initialisation();

        Carte fero1 = new Ferronnerie();
        Carte pr = new PoseDeRails();
        Carte pt = new PontEnAcier();
        Carte f = reserve.get("Ferraille").get(0);

        addAll(main, fero1, pr, pt);
        jouerTourPartiel("Ferronnerie", "Pose de rails", "Pont en acier");

        assertTrue(containsReferences(main));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, pr, fero1, pt));
        assertFalse(containsReference(reserve.get("Ferraille"), f));
        assertEquals(5, getArgent(joueur));
        assertEquals(2, getPointsRails(joueur));
    }
    @Test
    void test_Gare_double_pose() {
        setupJeu("Gare");
        initialisation();
        setAttribute(jeu, "nbJetonsGare",2);

        Carte c = new Gare();
        Carte c1 = new Gare();
        Carte fondPioche = new Ferraille();
        Carte f = reserve.get("Ferraille").get(0);

        addAll(main, c, c1);
        addAll(pioche, fondPioche);

        jouerTourPartiel("Gare", "TUILE:12", "Gare", "TUILE:13");

        assertTrue(containsReferencesInOrder(pioche, fondPioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c,c1));
        assertTrue(containsReferences(cartesRecues, f,f));
        assertFalse(containsReference(reserve.get("Ferraille"), f));
        assertEquals(0, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
        assertEquals(1, jeu.getTuile(12).getNbGares());
        assertEquals(1, jeu.getTuile(13).getNbGares());
    }
    @Test
    void test_Gare_si_nbjetonsgare_est_0_passe() {
        setupJeu("Gare");
        initialisation();
        setAttribute(jeu, "nbJetonsGare",1);

        Carte c = new Gare();
        Carte c1 = new Gare();
        Carte fondPioche = new Ferraille();
        Carte f = reserve.get("Ferraille").get(0);

        addAll(main, c, c1);
        addAll(pioche, fondPioche);

        jouerTourPartiel("Gare", "TUILE:12", "Gare");

        assertTrue(containsReferences(main));
        assertTrue(containsReferencesInOrder(pioche, fondPioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c,c1));
        assertTrue(containsReferences(cartesRecues, f,f));
        assertFalse(containsReference(reserve.get("Ferraille"), f));
        assertEquals(0, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
        assertEquals(1, jeu.getTuile(12).getNbGares());
    }
    @Test
    void test_HorairesTemporaires_Moins_2_Cartes_Trains_Pioche() {
        setupJeu("Horaires temporaires");
        initialisation();
        Carte ht = new HorairesTemporaires();
        Carte fondPioche = new Ferraille();
        Carte omni = new TrainOmnibus();
        Carte gare = new Gare();
        Carte imm = new Immeuble();

        addAll(main, ht);
        addAll(pioche, omni, gare, imm, fondPioche);

        jouerTourPartiel("Horaires temporaires");
        System.out.println(main);
        System.out.println(cartesEnJeu);
        System.out.println(defausse);

        assertTrue(containsReferences(main, omni));
        assertTrue(containsReferences(defausse, gare, imm, fondPioche));
        assertTrue(containsReferences(pioche));
        assertTrue(containsReferences(cartesEnJeu,ht));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(0, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }
    @Test
    void test_HorairesTemporaires_Moins_2_Cartes_Trains_Defausse() {
        setupJeu("Horaires temporaires");
        initialisation();
        Carte ht = new HorairesTemporaires();
        Carte fondPioche = new Ferraille();
        Carte omni = new TrainOmnibus();
        Carte gare = new Gare();
        Carte imm = new Immeuble();

        addAll(main, ht);
        addAll(defausse, omni, gare, imm, fondPioche);

        jouerTourPartiel("Horaires temporaires");


        assertTrue(containsReferences(main, omni));
        assertTrue(containsReferences(defausse, gare, imm, fondPioche));
        assertTrue(containsReferences(pioche));
        assertTrue(containsReferences(cartesEnJeu,ht));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(0, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }
    @Test
    void test_PassageEnGare_pioche_vide() {
        setupJeu("Passage en gare");
        initialisation();

        Carte c = new PassageEnGare();

        addAll(main, c);
        addAll(pioche);

        jouerTourPartiel("Passage en gare");

        assertTrue(containsReferences(main));
        assertTrue(containsReferencesInOrder(pioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(1, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }
    @Test
    void test_PersonnelDeGare_PiocheNonNull(){
        setupJeu("Personnel de gare");
        initialisation();

        Carte c = new PersonnelDeGare();

        addAll(main, c);
        jouerTourPartiel("Personnel de gare","piocher");
        assertFalse(containsReference(main,null));
        assertTrue(containsReferences(pioche));
        assertTrue(containsReferences(cartesEnJeu,c));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(0,getArgent(joueur));
        assertEquals(0,getPointsRails(joueur));

    }
    @Test
    void test_PersonnelDeGare_NePeutMettreDansLaReserverFeraille() {
        setupJeu("Personnel de gare");
        initialisation();

        Carte c = new PersonnelDeGare();

        addAll(main, c);
        assertDoesNotThrow(() -> jouerTourPartiel("Personnel de gare", "ferraille"));
        assertTrue(containsReferences(main));
        assertTrue(containsReferences(pioche));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertTrue(containsReferences(cartesRecues));
        assertFalse(containsReference(reserve.get("Ferraille"), null));
        assertEquals(0, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }

    @Test
    void test_PontEnAcier() {
        setupJeu("Pont en acier");
        initialisation();
        tuiles.get(23).ajouterRail(joueur);

        Carte c = new PontEnAcier();
        Carte omni = new TrainOmnibus();
        Carte f = reserve.get("Ferraille").get(0);

        addAll(main, c, omni);
        jouerTourPartiel("Train omnibus", "Pont en acier", "TUILE:33");

        checkPlateau(null, List.of(23, 33), null);
        assertTrue(containsReferences(main));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c, omni));
        assertFalse(containsReference(reserve.get("Ferraille"), f));
        assertEquals(1, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }
    @Test
    void test_PontEnAcier_sans_le_pont() {
        setupJeu("Pont en acier");
        initialisation();
        tuiles.get(23).ajouterRail(joueur);

        Carte c = new PoseDeRails();
        Carte omni = new TrainExpress();
        Carte f = reserve.get("Ferraille").get(0);

        addAll(main, c, omni);
        jouerTourPartiel("Train express", "Pose de rails", "TUILE:33");

        checkPlateau(null, List.of(23, 33), null);
        assertTrue(containsReferences(main));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c, omni));
        assertFalse(containsReference(reserve.get("Ferraille"), f));
        assertEquals(1, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }
    @Test
    void test_PoseDeRails_mais_barriere() {
        setupJeu("Viaduc");
        initialisation();
        tuiles.get(42).ajouterRail(joueur);
        ((TuileVille) tuiles.get(42)).ajouterGare();

        Carte c = new PoseDeRails();
        Carte epx = new TrainExpress();
        Carte f = reserve.get("Ferraille").get(0);

        addAll(main, c, epx);
        jouerTourPartiel( "Train express", "Pose de rails", "TUILE:52");

        checkPlateau(null, List.of(42), null);
        assertTrue(containsReferences(main));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c, epx));
        assertFalse(containsReference(reserve.get("Ferraille"), f));
        assertEquals(2, getArgent(joueur));
        assertEquals(1, getPointsRails(joueur));
    }
    @Test
    void test_PoseDeRails_sur_mer() {
        setupJeu("Pose de rails");
        initialisation();
        tuiles.get(41).ajouterRail(joueur);

        Carte c = new PoseDeRails();
        Carte f = reserve.get("Ferraille").get(0);

        addAll(main, c);
        jouerTourPartiel( "Pose de rails", "TUILE:40");

        checkPlateau(null, List.of(41), null);
        assertTrue(containsReferences(main));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertFalse(containsReference(reserve.get("Ferraille"), f));
        assertEquals(0, getArgent(joueur));
        assertEquals(1, getPointsRails(joueur));
    }
    @Test
    void test_SalleDeControle_pioche_vide() {
        setupJeu("Salle de contrôle");
        initialisation();

        Carte c = new SalleDeControle();

        addAll(main, c);
        addAll(pioche);

        jouerTourPartiel("Salle de contrôle");

        assertTrue(containsReferences(main));
        assertTrue(containsReferencesInOrder(pioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(0, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }
    @Test
    void test_TrainDeMarchandises_2_Ferraille() {
        setupJeu("Train de marchandises");
        initialisation();

        Carte fondPioche = new Ferraille();
        Carte c = new TrainDeMarchandises();
        Carte f1 = new Ferraille();
        Carte f2 = new Ferraille();
        Carte f3 = new Ferraille();
        int nbFerraille = reserve.get("Ferraille").size();

        addAll(main, c,  f1, f2, f3);
        addAll(pioche, fondPioche);
        jouerTourPartiel("Train de marchandises", "Ferraille", "Ferraille", "");

        assertTrue(containsReferences(main, f3));
        assertTrue(containsReferencesInOrder(pioche, fondPioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(3, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
        assertEquals(reserve.get("Ferraille").size(), nbFerraille + 2);
    }

    @Test
    void test_TrainDeMarchandises_6_Ferraille() {
        setupJeu("Train de marchandises");
        initialisation();

        Carte fondPioche = new Ferraille();
        Carte c = new TrainDeMarchandises();
        Carte f1 = new Ferraille();
        Carte f2 = new Ferraille();
        Carte f3 = new Ferraille();
        Carte f4 = new Ferraille();
        Carte f5 = new Ferraille();
        Carte f6 = new Ferraille();
        int nbFerraille = reserve.get("Ferraille").size();

        addAll(main, c, f1, f2, f3, f4,f5,f6);
        addAll(pioche, fondPioche);
        jouerTourPartiel("Train de marchandises", "Ferraille", "Ferraille", "Ferraille", "Ferraille", "Ferraille","Ferraille","");

        assertTrue(containsReferences(main));
        assertTrue(containsReferencesInOrder(pioche, fondPioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(7, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
        assertEquals(reserve.get("Ferraille").size(), nbFerraille + 6);
    }
    @Test
    void test_TraindeMarchandise_passe_sans_Fin_Tour() {
        setupJeu("Train de marchandises");
        setAttribute(joueur,"argent",0);
        initialisation();
        Carte tm = new TrainDeMarchandises();
        Carte f1 = new Ferraille();
        Carte f2 = new Ferraille();
        Carte f3 = new Ferraille();
        Carte f4 = new Ferraille();

        Carte fondPioche = new Ferraille();
        Carte imm = new Immeuble();

        addAll(main, tm, f1, f2, f3, f4);
        addAll(pioche, fondPioche);
        addAll(defausse, imm);
        jouerTourPartiel("Train de marchandises", "Ferraille", "Ferraille", "Ferraille", "Ferraille","");

        assertTrue(containsReferences(main));
        assertTrue(containsReferencesInOrder(pioche, fondPioche));
        assertTrue(containsReferences(defausse, imm));
        assertTrue(containsReferences(cartesEnJeu, tm));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(5, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }

    @Test
    void test_TrainDeMarchandise_passe_avec_Fin_Tour() {
        setupJeu("Train de marchandises");
        setAttribute(joueur,"argent",0);
        initialisation();
        Carte tm = new TrainDeMarchandises();
        Carte f1 = new Ferraille();
        Carte f2 = new Ferraille();
        Carte f3 = new Ferraille();
        Carte f4 = new Ferraille();

        Carte fondPioche = new Ferraille();
        Carte imm = new Immeuble();

        addAll(main, tm, f1, f2, f3, f4);
        addAll(pioche, fondPioche);
        addAll(defausse, imm);
        jouerTourPartiel("Train de marchandises", "Ferraille", "Ferraille", "Ferraille", "Ferraille","","");

        assertTrue(containsReferences(main,fondPioche,imm,tm));
        assertTrue(containsReferencesInOrder(pioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(0, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }

    @Test
    void test_TrainMatinal_non() {
        setupJeu("Train matinal", "Aiguillage", "Train omnibus");
        initialisation();

        Carte tt = new TrainMatinal();
        Carte c = reserve.get("Aiguillage").get(0);

        Carte omni1 = new TrainOmnibus();
        Carte omni2 = new TrainOmnibus();
        Carte omni3 = new TrainOmnibus();
        Carte omni4 = new TrainOmnibus();
        Carte omni5 = new TrainOmnibus();

        List<String> instructions = new ArrayList<>();

        for (int i = 0; i < 5 + 1; i++) {
            instructions.add("Train omnibus");
        }
        instructions.add("Train matinal");
        instructions.add("ACHAT:" + "Aiguillage");
        instructions.add("non");

        addAll(main, tt, omni1, omni2, omni3, omni4, omni5);
        jouerTourPartiel(instructions);

        assertTrue(containsReferences(main));
        assertTrue(containsReferencesInOrder(pioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, tt, omni1, omni2, omni3, omni4, omni5));
        assertTrue(containsReferences(cartesRecues, c));
        assertEquals(2, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }
    @Test
    void test_TrainMatinal_oui() {
        setupJeu("Train matinal", "Aiguillage", "Train omnibus");
        initialisation();

        Carte tt = new TrainMatinal();

        Carte c = reserve.get("Aiguillage").get(0);

        Carte omni1 = new TrainOmnibus();
        Carte omni2 = new TrainOmnibus();
        Carte omni3 = new TrainOmnibus();
        Carte omni4 = new TrainOmnibus();
        Carte omni5 = new TrainOmnibus();

        List<String> instructions = new ArrayList<>();
        for (int i = 0; i < 5 + 1; i++) {
            instructions.add("Train omnibus");
        }
        instructions.add("Train matinal");
        instructions.add("ACHAT:" + "Aiguillage");
        instructions.add("oui");

        addAll(main, tt, omni1, omni2, omni3, omni4, omni5);
        jouerTourPartiel(instructions);

        assertTrue(containsReferences(main));
        assertTrue(containsReferencesInOrder(pioche, c));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, tt, omni1, omni2, omni3, omni4, omni5));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(2, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }

    @Test
    void test_TrainPostal_Ferraille() {
        setupJeu("Train postal");
        initialisation();

        Carte c = new TrainPostal();
        Carte im = new Immeuble();
        Carte f = new Ferraille();

        addAll(main, c, im, f);
        addAll(pioche);
        jouerTourPartiel( "Train postal", "Immeuble", "Ferraille", "");

        assertTrue(containsReferences(main));
        assertTrue(containsReferencesInOrder(pioche));
        assertTrue(containsReferences(defausse, im, f));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(3, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }
    @Test
    void test_TuileEtoile_2etoiles() {
        setupJeu("Pose de rails");
        initialisation();
        tuiles.get(60).ajouterRail(joueur);

        Carte epx = new TrainExpress();
        Carte epx1 = new TrainExpress();
        Carte pr = new PoseDeRails();
        Carte f = reserve.get("Ferraille").get(0);

        addAll(main, epx, epx1, pr);
        jouerTourPartiel( "Train express", "Train express", "Pose de rails",  "TUILE:69");

        checkPlateau(null, List.of(60, 69), null);
        assertTrue(containsReferences(main));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, epx, epx1, pr));
        assertFalse(containsReference(reserve.get("Ferraille"), f));
        assertEquals(2, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }

    @Test
    void test_TuileEtoile_2etoiles_paspossible() {
        setupJeu("Pose de rails");
        initialisation();
        tuiles.get(60).ajouterRail(joueur);

        Carte c = new TrainOmnibus();
        Carte pr = new PoseDeRails();
        Carte f = reserve.get("Ferraille").get(0);

        addAll(main, c, pr);
        jouerTourPartiel( "Train omnibus", "Pose de rails",  "TUILE:69");

        checkPlateau(null, List.of(60), null);
        assertTrue(containsReferences(main));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c,  pr));
        assertFalse(containsReference(reserve.get("Ferraille"), f));
        assertEquals(1, getArgent(joueur));
        assertEquals(1, getPointsRails(joueur));
    }

    @Test
    void test_TuileEtoile_3etoiles() {
        setupJeu("Pose de rails");
        initialisation();
        tuiles.get(66).ajouterRail(joueur);

        Carte epx = new TrainExpress();
        Carte epx1 = new TrainExpress();
        Carte pr = new PoseDeRails();
        Carte f = reserve.get("Ferraille").get(0);

        addAll(main, epx, epx1, pr);
        jouerTourPartiel( "Train express", "Train express", "Pose de rails",  "TUILE:75");

        checkPlateau(null, List.of(66, 75), null);
        assertTrue(containsReferences(main));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, epx, epx1, pr));
        assertFalse(containsReference(reserve.get("Ferraille"), f));
        assertEquals(1, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }

    @Test
    void test_TuileEtoile_3etoiles_paspossible() {
        setupJeu("Pose de rails");
        initialisation();
        tuiles.get(65).ajouterRail(joueur);

        Carte epx = new TrainExpress();
        Carte pr = new PoseDeRails();
        Carte f = reserve.get("Ferraille").get(0);

        addAll(main, epx, pr);
        jouerTourPartiel( "Train express", "Pose de rails",  "TUILE:75");

        checkPlateau(null, List.of(65), null);
        assertTrue(containsReferences(main));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, epx, pr));
        assertFalse(containsReference(reserve.get("Ferraille"), f));
        assertEquals(2, getArgent(joueur));
        assertEquals(1, getPointsRails(joueur));
    }

    @Test
    void test_TuileEtoile_4etoiles() {
        setupJeu("Pose de rails");
        initialisation();
        tuiles.get(1).ajouterRail(joueur);

        Carte epx = new TrainExpress();
        Carte epx1 = new TrainExpress();
        Carte pr = new PoseDeRails();
        Carte f = reserve.get("Ferraille").get(0);

        addAll(main, epx, epx1, pr);
        jouerTourPartiel( "Train express", "Train express", "Pose de rails",  "TUILE:0");

        checkPlateau(null, List.of(0,1), null);
        assertTrue(containsReferences(main));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, epx, epx1, pr));
        assertFalse(containsReference(reserve.get("Ferraille"), f));
        assertEquals(0, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }

    @Test
    void test_TuileEtoile_4etoiles_paspossible() {
        setupJeu("Pose de rails");
        initialisation();
        tuiles.get(1).ajouterRail(joueur);

        Carte epx = new TrainExpress();
        Carte pr = new PoseDeRails();
        Carte f = reserve.get("Ferraille").get(0);

        addAll(main, epx, pr);
        jouerTourPartiel( "Train express", "Pose de rails",  "TUILE:0");

        checkPlateau(null, List.of(1), null);
        assertTrue(containsReferences(main));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, epx, pr));
        assertFalse(containsReference(reserve.get("Ferraille"), f));
        assertEquals(2, getArgent(joueur));
        assertEquals(1, getPointsRails(joueur));
    }
    @Test
    void test_Tunnel_sans_montagne() {
        setupJeu("Tunnel");
        initialisation();
        tuiles.get(23).ajouterRail(joueur);

        Carte c = new Tunnel();
        Carte f = reserve.get("Ferraille").get(0);

        addAll(main, c);
        jouerTourPartiel( "Tunnel", "TUILE:22");

        checkPlateau(null, List.of(23, 22), null);
        assertTrue(containsReferences(main));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertFalse(containsReference(reserve.get("Ferraille"), f));
        assertEquals(0, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }

    @Test
    void test_Tunnel_sur_montagne() {
        setupJeu("Tunnel");
        initialisation();
        tuiles.get(23).ajouterRail(joueur);

        Carte c = new Tunnel();
        Carte f = reserve.get("Ferraille").get(0);

        addAll(main, c);
        jouerTourPartiel( "Tunnel", "TUILE:14");

        checkPlateau(null, List.of(23, 14), null);
        assertTrue(containsReferences(main));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertFalse(containsReference(reserve.get("Ferraille"), f));
        assertEquals(0, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }

    @Test
    void test_Tunnel_montagne_sans_Tunnel() {
        setupJeu("Tunnel");
        initialisation();
        tuiles.get(23).ajouterRail(joueur);

        Carte c = new PoseDeRails();
        Carte epx = new TrainExpress();
        Carte f = reserve.get("Ferraille").get(0);

        addAll(main, c, epx);
        jouerTourPartiel( "Train express", "Pose de rails", "TUILE:14");

        checkPlateau(null, List.of(23, 14), null);
        assertTrue(containsReferences(main));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c, epx));
        assertFalse(containsReference(reserve.get("Ferraille"), f));
        assertEquals(0, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }
    @Test
    void test_Viaduc_sans_ville() {
        setupJeu("Viaduc");
        initialisation();
        tuiles.get(23).ajouterRail(joueur);

        Carte c = new Viaduc();
        Carte f = reserve.get("Ferraille").get(0);

        addAll(main, c);
        jouerTourPartiel( "Viaduc", "TUILE:22");

        checkPlateau(null, List.of(23, 22), null);
        assertTrue(containsReferences(main));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertFalse(containsReference(reserve.get("Ferraille"), f));
        assertEquals(0, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }

    @Test
    void test_Viaduc_sur_ville() {
        setupJeu("Viaduc");
        initialisation();
        tuiles.get(23).ajouterRail(joueur);

        Carte c = new Viaduc();
        Carte f = reserve.get("Ferraille").get(0);

        addAll(main, c);
        jouerTourPartiel( "Viaduc", "TUILE:24");

        checkPlateau(null, List.of(23, 24), null);
        assertTrue(containsReferences(main));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertFalse(containsReference(reserve.get("Ferraille"), f));
        assertEquals(0, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }

    @Test
    void test_ville_sans_Viaduc() {
        setupJeu("Viaduc");
        initialisation();
        tuiles.get(23).ajouterRail(joueur);

        Carte c = new PoseDeRails();
        Carte epx = new TrainExpress();
        Carte f = reserve.get("Ferraille").get(0);

        addAll(main, c, epx);
        jouerTourPartiel( "Train express", "Pose de rails", "TUILE:24");

        checkPlateau(null, List.of(23, 24), null);
        assertTrue(containsReferences(main));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c, epx));
        assertFalse(containsReference(reserve.get("Ferraille"), f));
        assertEquals(1, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }

    @Test
    void test_ville_sans_Viaduc_sur_ville_avec_Gare() {
        setupJeu("Viaduc");
        initialisation();
        tuiles.get(23).ajouterRail(joueur);
        ((TuileVille) tuiles.get(24)).ajouterGare();

        Carte c = new PoseDeRails();
        Carte epx = new TrainExpress();
        Carte f = reserve.get("Ferraille").get(0);

        addAll(main, c, epx);
        jouerTourPartiel( "Train express", "Pose de rails", "TUILE:24");

        checkPlateau(null, List.of(23, 24), null);
        assertTrue(containsReferences(main));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c, epx));
        assertFalse(containsReference(reserve.get("Ferraille"), f));
        assertEquals(0, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }

    @Test
    void test_ville_sans_Viaduc_sur_ville_avec_2_Gare() {
        setupJeu("Viaduc");
        initialisation();
        tuiles.get(32).ajouterRail(joueur);
        ((TuileVille) tuiles.get(42)).ajouterGare();
        ((TuileVille) tuiles.get(42)).ajouterGare();

        Carte c = new PoseDeRails();
        Carte epx = new TrainExpress();
        Carte epx1 = new TrainExpress();
        Carte f = reserve.get("Ferraille").get(0);

        addAll(main, c, epx, epx1);
        jouerTourPartiel( "Train express", "Train express", "Pose de rails", "TUILE:42");

        checkPlateau(null, List.of(32, 42), null);
        assertTrue(containsReferences(main));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c, epx, epx1));
        assertFalse(containsReference(reserve.get("Ferraille"), f));
        assertEquals(1, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }

    @Test
    void test_ville_sans_Viaduc_sur_ville_avec_3_Gare() {
        setupJeu("Viaduc");
        initialisation();
        tuiles.get(20).ajouterRail(joueur);
        ((TuileVille) tuiles.get(29)).ajouterGare();
        ((TuileVille) tuiles.get(29)).ajouterGare();
        ((TuileVille) tuiles.get(29)).ajouterGare();

        Carte c = new PoseDeRails();
        Carte epx = new TrainExpress();
        Carte epx1 = new TrainExpress();
        Carte f = reserve.get("Ferraille").get(0);

        addAll(main, c, epx, epx1);
        jouerTourPartiel( "Train express", "Train express", "Pose de rails", "TUILE:29");

        checkPlateau(null, List.of(20, 29), null);
        assertTrue(containsReferences(main));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c, epx, epx1));
        assertFalse(containsReference(reserve.get("Ferraille"), f));
        assertEquals(0, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }
    @Test
    void test_VoieSouterraine_sur_TuileEtoile() {
        setupJeu("Voie souterraine");
        initialisation();
        tuiles.get(29).ajouterRail(joueur);

        Carte c = new VoieSouterraine();
        Carte epx = new TrainExpress();
        Carte f = reserve.get("Ferraille").get(0);

        addAll(main, c, epx);
        jouerTourPartiel( "Train express", "Voie souterraine", "TUILE:38");

        checkPlateau(null, List.of(29, 38), null);
        assertTrue(containsReferences(main));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c, epx));
        assertFalse(containsReference(reserve.get("Ferraille"), f));
        assertEquals(2, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }

    @Test
    void test_VoieSouterraine_sur_montagne() {
        setupJeu("Voie souterraine");
        initialisation();
        tuiles.get(21).ajouterRail(joueur);

        Carte c = new VoieSouterraine();
        Carte epx = new TrainExpress();
        Carte f = reserve.get("Ferraille").get(0);

        addAll(main, c, epx);
        jouerTourPartiel( "Train express", "Voie souterraine", "TUILE:20");

        checkPlateau(null, List.of(21, 20), null);
        assertTrue(containsReferences(main));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c, epx));
        assertFalse(containsReference(reserve.get("Ferraille"), f));
        assertEquals(2, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }

    @Test
    void test_VoieSouterraine_sur_riviere() {
        setupJeu("Voie souterraine");
        initialisation();
        tuiles.get(23).ajouterRail(joueur);

        Carte c = new VoieSouterraine();
        Carte epx = new TrainExpress();
        Carte f = reserve.get("Ferraille").get(0);

        addAll(main, c, epx);
        jouerTourPartiel( "Train express", "Voie souterraine", "TUILE:32");

        checkPlateau(null, List.of(23, 32), null);
        assertTrue(containsReferences(main));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c, epx));
        assertFalse(containsReference(reserve.get("Ferraille"), f));
        assertEquals(2, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }

    @Test
    void test_VoieSouterraine_sur_ville() {
        setupJeu("Voie souterraine");
        initialisation();
        tuiles.get(20).ajouterRail(joueur);
        ((TuileVille) tuiles.get(29)).ajouterGare();
        ((TuileVille) tuiles.get(29)).ajouterGare();
        ((TuileVille) tuiles.get(29)).ajouterGare();

        Carte c = new VoieSouterraine();
        Carte epx = new TrainExpress();
        Carte f = reserve.get("Ferraille").get(0);

        addAll(main, c, epx);
        jouerTourPartiel( "Train express", "Voie souterraine", "TUILE:29");

        checkPlateau(null, List.of(29, 20), null);
        assertTrue(containsReferences(main));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c, epx));
        assertFalse(containsReference(reserve.get("Ferraille"), f));
        assertEquals(2, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }

    @Test
    void test_VoieSouteraine_sur_mer() {
        setupJeu("Voie souterraine");
        initialisation();
        tuiles.get(41).ajouterRail(joueur);

        Carte c = new VoieSouterraine();
        Carte epx = new TrainExpress();
        Carte f = reserve.get("Ferraille").get(0);

        addAll(main, c, epx);
        jouerTourPartiel( "Train express", "Voie souterraine", "TUILE:50");

        checkPlateau(null, List.of(41), null);
        assertTrue(containsReferences(main));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c, epx));
        assertFalse(containsReference(reserve.get("Ferraille"), f));
        assertEquals(2, getArgent(joueur));
        assertEquals(1, getPointsRails(joueur));
    }
}
