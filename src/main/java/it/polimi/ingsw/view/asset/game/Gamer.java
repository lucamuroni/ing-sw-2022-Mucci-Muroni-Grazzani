package it.polimi.ingsw.view.asset.game;

import it.polimi.ingsw.controller.networking.AssistantCardDeckFigures;
import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.expert.CharacterCard;
import it.polimi.ingsw.model.pawn.TowerColor;

import java.util.ArrayList;

/**
 * This class represents a Gamer
 */
public class Gamer {
    private final int id;
    private String username;
    private final ArrayList<AssistantCard> cards;
    private AssistantCard currentSelection;
    private CharacterCard currentExpertCardSelection;
    private AssistantCardDeckFigures figure;
    private final DashBoard dashBoard;
    private TowerColor color;

    /**
     * Constructor of the class
     * @param id is the unique token that identifies the gamer
     */
    public Gamer(int id) {
        this.id = id;
        this.cards = new ArrayList<>();
        this.dashBoard = new DashBoard();
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

    public DashBoard getDashBoard() {
        return this.dashBoard;
    }

    public ArrayList<AssistantCard> getCards() {
        return this.cards;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public AssistantCard getCurrentSelection() {
        return currentSelection;
    }

    public AssistantCardDeckFigures getFigure() {
        return figure;
    }

    public TowerColor getColor() {
        return color;
    }

    public void setUsername(String username, boolean self) {
        this.username = username;
        if(self){
            this.dashBoard.setUsername("yours");
        }else{
            this.dashBoard.setUsername(username);
        }
    }

    public void setColor(TowerColor color) {
        this.color = color;
    }

    public void setCurrentSelection(AssistantCard currentSelection) {
        this.currentSelection = currentSelection;
    }

    public void setCurrentExpertCardSelection(CharacterCard card){
        this.currentExpertCardSelection = card;
    }

    public CharacterCard getCurrentExpertCardSelection(){
        return this.currentExpertCardSelection;
    }
}
