package it.polimi.ingsw.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AssistantCardDeckTest {

    AssistantCardDeck deck = new AssistantCardDeck();

    @Test
    void getCardList() {
        ArrayList<AssistantCard> cards = deck.getCardList();
        assertTrue(deck.getCardList().containsAll(cards));
    }

    @Test
    void getCurrentSelection() {
        deck.setCurrentSelection(deck.getCardList().get(1));
        assertEquals(2, deck.getCurrentSelection().getTurnValue());
        assertEquals(1, deck.getCurrentSelection().getMovement());
    }

    @Test
    void getPastSelection() {
        deck.setCurrentSelection(deck.getCardList().get(0));
        deck.setPastSelection();
        deck.setCurrentSelection(deck.getCardList().get(1));
        assertEquals(1, deck.getPastSelection().getTurnValue());
        assertEquals(1, deck.getPastSelection().getMovement());
    }

    @Test
    void setCurrentSelection() {
        ArrayList<AssistantCard> cards = new ArrayList<AssistantCard>(deck.getCardList());
        deck.setCurrentSelection(cards.get(1));
        assertEquals(2, deck.getCurrentSelection().getTurnValue());
        assertEquals(1, deck.getCurrentSelection().getMovement());
        assertNotEquals(cards.get(1), deck.getCardList().get(1));
        assertFalse(deck.getCardList().containsAll(cards));
    }
}