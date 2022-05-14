package it.polimi.ingsw.view.asset.game;

import it.polimi.ingsw.controller.server.game.AssistantCardDeckFigures;
import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.AssistantCardDeck;

import java.util.ArrayList;

/**
 * This class represents a Gamer
 */
public class Gamer {
    private final int id;
    private String username;
    private ArrayList<AssistantCard> cards;
    private AssistantCard currentSelection;
    private AssistantCard pastSelection;
    private AssistantCardDeckFigures figure;
    private DashBoard dashBoard;

    /**
     * Constructor of the class
     * @param id is the unique token that identifies the gamer
     * @param username is the nick choose by the player
     */
    public Gamer(int id, String username, DashBoard dashBoard) {
        this.id = id;
        this.username = username;
        this.cards = new ArrayList<>();
        this.dashBoard = dashBoard;
    }

    /**
     * This method is used to update the cards of the player when he needs to choose one of them in planning phase
     * @param cards are the possible choices
     */
    public void updateCards(ArrayList<AssistantCard> cards) {
        this.cards.clear();
        this.cards.addAll(cards);
    }

    /**
     * This method is used to update the back of the cards of the players
     * @param figure is the chosen image
     */
    public void updateFigure(AssistantCardDeckFigures figure) {
        this.figure = figure;
    }

    /**
     * Method used to update the current chosen card
     * @param card is the chosen card
     */
    public void updateCurrentSelection(AssistantCard card) {
        this.currentSelection = card;
    }

    /**
     * Method used to update the past chosen card
     * @param card is the past chosen card
     */
    public void updatePastSelection(AssistantCard card) {
        this.pastSelection = card;
    }
}
