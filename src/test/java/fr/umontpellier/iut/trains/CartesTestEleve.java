package fr.umontpellier.iut.trains;

import org.junit.jupiter.api.Test;

import fr.umontpellier.iut.trains.cartes.*;

import static org.junit.jupiter.api.Assertions.*;

public class CartesTestEleve {

    @Test
    public void Test_utilisation_carte_victoire_appartement(){
        Carte appartement = new Appartement();

        assertEquals(1, appartement.getPv());
    }

    @Test
    public void Test_utilisation_carte_victoire_gratte_ciel(){
        Carte carte = new GratteCiel();

        assertEquals(4, carte.getPv());
    }

    @Test
    public void Test_utilisation_carte_victoire_immeuble(){
        Carte carte = new Immeuble();

        assertEquals(2, carte.getPv());
    }

    @Test
    public void Test_utilisation_carte_train_tourisme_get_pv(){
        Carte carte = new TrainDeTourisme();

        assertEquals(1, carte.getPv());
    }
}
