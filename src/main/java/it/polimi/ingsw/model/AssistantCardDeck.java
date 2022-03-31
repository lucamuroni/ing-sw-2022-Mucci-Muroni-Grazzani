package it.polimi.ingsw.model;

import it.polimi.ingsw.model.AssistantCard;

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
     */
    public AssistantCardDeck(){
        cardList = new ArrayList<AssistantCard>(10);
        int temp=1;
        for(int i=1; i<=10; i++){
            cardList.add(new AssistantCard(i,temp));
            if(i%2==0){
                temp++;
            }
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
     * Setter method
     * @param currentSelection is the card selected by the player, from his cardList, to play this round
     */
    public void setCurrentSelection(AssistantCard currentSelection) {
        this.currentSelection = currentSelection;
    }

    /**
     * Setter method
     * @param pastSelection is the card selected by the player the last round (previously it was the currentSelection)
     */
    public void setPastSelection(AssistantCard pastSelection) {
        this.pastSelection = pastSelection;
    }
}
