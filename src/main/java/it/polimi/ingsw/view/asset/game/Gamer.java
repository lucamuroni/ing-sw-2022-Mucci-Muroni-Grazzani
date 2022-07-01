package it.polimi.ingsw.view.asset.game;

import it.polimi.ingsw.controller.networking.AssistantCardDeckFigures;
import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.expert.CharacterCard;
import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.model.pawn.TowerColor;

import java.util.ArrayList;

/**
 * @author Luca Muroni
 * @author Davide Grazzani
 * This class represents a Gamer
 */
public class Gamer {
    private final int id;
    private String username;
    private final ArrayList<AssistantCard> cards;
    private AssistantCard currentSelection;
    private AssistantCardDeckFigures figure;
    private final DashBoard dashBoard;
    private TowerColor color;
    private CharacterCard currentExpertCardSelection;
    private final ArrayList<PawnColor> selectedColors;
    private PawnColor selectedColor;
    private Island selectedisland;

    /**
     * Constructor of the class
     * @param id is the unique token that identifies the gamer
     */
    public Gamer(int id) {
        this.id = id;
        this.cards = new ArrayList<>();
        this.dashBoard = new DashBoard();
        this.selectedColors = new ArrayList<>();
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
     * Getter method
     * @return the dashboard
     */
    public DashBoard getDashBoard() {
        return dashBoard;
    }

    /**
     * Getter method
     * @return the arrayList of assistantCards
     */
    public ArrayList<AssistantCard> getCards() {
        return cards;
    }

    /**
     * Getter method
     * @return the id associated with the gamer
     */
    public int getId() {
        return id;
    }

    /**
     * Getter method
     * @return the username of the gamer
     */
    public String getUsername() {
        return username;
    }

    /**
     * Getter method
     * @return the assistantCard selected for this turn
     */
    public AssistantCard getCurrentSelection() {
        return currentSelection;
    }

    /**
     * Getter method
     * @return the figure of the deck of assistantCards of the player
     */
    public AssistantCardDeckFigures getFigure() {
        return figure;
    }

    /**
     * Getter method
     * @return the player's tower color
     */
    public TowerColor getColor() {
        return color;
    }

    /**
     * Getter method
     * @return the characterCard selected this turn
     */
    public CharacterCard getCurrentExpertCardSelection(){
        return currentExpertCardSelection;
    }

    /**
     * Getter method
     * @return the arrayList of possible colors
     */
    public ArrayList<PawnColor> getSelectedColors() {
        return selectedColors;
    }

    /**
     * Getter method
     * @return the selected color by player
     */
    public PawnColor getSelectedColor() {
        return selectedColor;
    }

    /**
     * Getter method
     * @return the island selected by the player
     */
    public Island getSelectedIsland() {
        return selectedisland;
    }

    /**
     * Setter method
     * @param username is the username of the gamer
     * @param self if the gamer is exactly self
     */
    public void setUsername(String username, boolean self) {
        this.username = username;
        if(self){
            this.dashBoard.setUsername("yours");
        }else{
            this.dashBoard.setUsername(username);
        }
    }

    /**
     * Setter method
     * @param color is the color of towers associated with the player
     */
    public void setColor(TowerColor color) {
        this.color = color;
    }

    /**
     * Setter method
     * @param currentSelection is the assistantCard selected by the player
     */
    public void setCurrentSelection(AssistantCard currentSelection) {
        this.currentSelection = currentSelection;
    }

    /**
     * Setter method
     * @param card is the characterCard selected by the player
     */
    public void setCurrentExpertCardSelection(CharacterCard card){
        this.currentExpertCardSelection = card;
    }

    /**
     * Setter method
     * @param colors is the arrayList of selected colors by the player
     */
    public void setSelectedColors(ArrayList<PawnColor> colors) {
        this.selectedColors.clear();
        this.selectedColors.addAll(colors);
    }

    /**
     * Setter method
     * @param color is the selected color by gamer
     */
    public void setSelectedColor(PawnColor color) {
        this.selectedColor = color;
    }

    /**
     * Setter method
     * @param island is the selected island by the player
     */
    public void setSelectedIsland(Island island) {
        this.selectedisland = island;
    }
}