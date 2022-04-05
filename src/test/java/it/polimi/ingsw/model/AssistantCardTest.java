package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AssistantCardTest {

    AssistantCardDeck deck = new AssistantCardDeck();

    @Test
    void getMovement() {
        assertEquals(1, deck.getCardList().get(0).getMovement());
        assertEquals(1, deck.getCardList().get(1).getMovement());
        assertEquals(2, deck.getCardList().get(2).getMovement());
        assertEquals(2, deck.getCardList().get(3).getMovement());
        assertEquals(3, deck.getCardList().get(4).getMovement());
        assertEquals(3, deck.getCardList().get(5).getMovement());
        assertEquals(4, deck.getCardList().get(6).getMovement());
        assertEquals(4, deck.getCardList().get(7).getMovement());
        assertEquals(5, deck.getCardList().get(8).getMovement());
        assertEquals(5, deck.getCardList().get(9).getMovement());
    }

    @Test
    void getTurnValue() {
        assertEquals(1, deck.getCardList().get(0).getTurnValue());
        assertEquals(2, deck.getCardList().get(1).getTurnValue());
        assertEquals(3, deck.getCardList().get(2).getTurnValue());
        assertEquals(4, deck.getCardList().get(3).getTurnValue());
        assertEquals(5, deck.getCardList().get(4).getTurnValue());
        assertEquals(6, deck.getCardList().get(5).getTurnValue());
        assertEquals(7, deck.getCardList().get(6).getTurnValue());
        assertEquals(8, deck.getCardList().get(7).getTurnValue());
        assertEquals(9, deck.getCardList().get(8).getTurnValue());
        assertEquals(10, deck.getCardList().get(9).getTurnValue());
    }
}