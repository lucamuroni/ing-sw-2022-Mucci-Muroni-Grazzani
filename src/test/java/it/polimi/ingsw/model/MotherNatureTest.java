package it.polimi.ingsw.model;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MotherNatureTest {

    @Test
    void setPlace() {
        Island island1 = new Island(1);
        Island island2 = new Island(2);
        MotherNature motherNature = new MotherNature(island1);
        assertEquals(island1,motherNature.getPlace());
        motherNature.setPlace(island2);
        assertNotEquals(island1,motherNature.getPlace());
        assertEquals(island2,motherNature.getPlace());
    }

    @Test
    void getPlace() {
        Island island1 = new Island(1);
        MotherNature motherNature = new MotherNature(island1);
        assertEquals(island1,motherNature.getPlace());
    }
}