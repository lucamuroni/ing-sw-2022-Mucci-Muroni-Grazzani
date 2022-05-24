package it.polimi.ingsw.model;

import java.util.ArrayList;

/**
 * @author Luca Muroni
 * Class that represents the deck of AssistantCards that every player has
 */
public class AssistantCardDeck {
    private ArrayList<AssistantCard> cardList;
    private AssistantCard currentSelection;
    private AssistantCard pastSelection;

    /**
     * Class constructor
     * It is based on a simple pattern: every time are created two cards, the move increments by 1. This is proved by
     * the fact that the values for the cards are (turn, move):
     * (1,1), (2,1), (3,2), (4,2), (5,3), (6,3), (7,4), (8,4), (9,5), (10,5)
     */
    public AssistantCardDeck(){
        cardList = new ArrayList<AssistantCard>();
        for( AssistantCard assistantCard : AssistantCard.values()){
            cardList.add(assistantCard);
        }
    }

    /**
     * Getter method
     * @return the list of cards that a player can still play
     */
    public ArrayList<AssistantCard> getCardList() {
        return cardList;
    }

    /**
     * Getter method
     * @return the card selected by the player this round
     */
    public AssistantCard getCurrentSelection() {
        return currentSelection;
    }

    /**
     * Getter method
     * @return the card selected by the player the previous round
     */
    public AssistantCard getPastSelection() {
        return pastSelection;
    }

    /**
     * Setter method. It also cals the private method setPastSelection to set the currentSelection of the last round
     * as the new past selection
     * @param currentSelection is the card selected by the player, from his cardList, to play this round
     */
    public void setCurrentSelection(AssistantCard currentSelection) {
        this.currentSelection = currentSelection;
        cardList.removeIf( card -> card.getTurnValue()==currentSelection.getTurnValue());
    }

    /**
     * Setter method used for setting the past selection right before starting a new round
     */
    public void setPastSelection() {
        this.pastSelection = this.currentSelection;
    }
}
