package it.polimi.ingsw.model.pawn;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PawnColorTest {

    @Test
    void testToString() {
        assertEquals("blue", PawnColor.BLUE.toString());
    }
}