package it.polimi.ingsw.model.pawn;

import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.MotherNature;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MotherNatureTest {
    Island island1 = new Island(1);
    Island island2 = new Island(2);
    MotherNature motherNature = new MotherNature(island1);

    @Test
    void setPlace() {
        assertEquals(island1, motherNature.getPlace());
        motherNature.setPlace(island2);
        assertNotEquals(island1, motherNature.getPlace());
        assertEquals(island2, motherNature.getPlace());
    }

    @Test
    void getPlace() {
        assertEquals(island1, motherNature.getPlace());
    }
}