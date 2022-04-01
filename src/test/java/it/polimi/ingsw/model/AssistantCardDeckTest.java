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
        AssistantCard card = deck.getCurrentSelection();
        assertEquals(card, deck.getCurrentSelection());
    }

    @Test
    void getPastSelection() {
        AssistantCard card = deck.getPastSelection();
        assertEquals(card, deck.getPastSelection());
    }

    @Test
    void setCurrentSelection() {
        ArrayList<AssistantCard> cards = new ArrayList<AssistantCard>(deck.getCardList());
        deck.setCurrentSelection(cards.get(1));
        assertEquals(2, deck.getCurrentSelection().getTurnValue());
        assertEquals(1, deck.getCurrentSelection().getMovement());
        assertNotEquals(cards.get(1), deck.getCardList().get(1));
    }
}