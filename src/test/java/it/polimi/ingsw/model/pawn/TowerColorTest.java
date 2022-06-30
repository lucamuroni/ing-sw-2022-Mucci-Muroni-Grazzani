package it.polimi.ingsw.model.pawn;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TowerColorTest {
    @Test
    void testToString() {
        assertEquals("grey", TowerColor.GREY.toString());
    }

    @Test
    void getAcronym() {
        assertEquals('G', TowerColor.GREY.getAcronym());
    }
}