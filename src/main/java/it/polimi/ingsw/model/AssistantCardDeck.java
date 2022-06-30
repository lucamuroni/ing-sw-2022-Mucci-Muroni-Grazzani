package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Luca Muroni
 * Class that represents the deck of assistantCards that every gamer has
 */
public class AssistantCardDeck {
    private final ArrayList<AssistantCard> cardList;
    private AssistantCard currentSelection;
    private AssistantCard pastSelection;

    /**
     * Class constructor
     * It is based on a simple pattern: every time are created two cards, the move increments by 1. This is proved by
     * the fact that the values for the cards are (turn, move):
     * (1,1), (2,1), (3,2), (4,2), (5,3), (6,3), (7,4), (8,4), (9,5), (10,5)
     */
    public AssistantCardDeck(){
        cardList = new ArrayList<>();
        cardList.addAll(Arrays.asList(AssistantCard.values()));
    }

    /**
     * Method used to get the cards of a deck
     * @return the list of cards that a gamer can still play
     */
    public ArrayList<AssistantCard> getCardList() {
        return cardList;
    }

    /**
     * method used to get the card selected by a gamer in this round
     * @return the card selected by a gamer
     */
    public AssistantCard getCurrentSelection() {
        return currentSelection;
    }

    /**
     * Method used to get the card selected by a gamer the previous round
     * @return the last card selected by a gamer
     */
    public AssistantCard getPastSelection() {
        return pastSelection;
    }

    /**
     * Method used to set the new card selected by a gamer. It also calls private method setPastSelection to set the
     * currentSelection of the last round as the new pastSelection
     * @param currentSelection is the card selected by a gamer, from his cardList, to play this round
     */
    public void setCurrentSelection(AssistantCard currentSelection) {
        this.currentSelection = currentSelection;
        cardList.removeIf( card -> card.getTurnValue()==currentSelection.getTurnValue());
    }

    /**
     * Method used to set new pastSelection right before starting a new round
     */
    public void setPastSelection() {
        this.pastSelection = this.currentSelection;
    }
}
