package it.polimi.ingsw.view.asset.game;

import it.polimi.ingsw.controller.networking.AssistantCardDeckFigures;
import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.expert.CharacterCard;
import it.polimi.ingsw.model.pawn.PawnColor;
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
    private AssistantCardDeckFigures figure;
    private final DashBoard dashBoard;
    private TowerColor color;
    private CharacterCard currentExpertCardSelection;
    private ArrayList<PawnColor> selectedColors;
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

    public DashBoard getDashBoard() {
        return dashBoard;
    }

    public ArrayList<AssistantCard> getCards() {
        return cards;
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

    public CharacterCard getCurrentExpertCardSelection(){
        return currentExpertCardSelection;
    }

    public ArrayList<PawnColor> getSelectedColors() {
        return selectedColors;
    }

    public PawnColor getSelectedColor() {
        return selectedColor;
    }

    public Island getSelectedisland() {
        return selectedisland;
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

    public void setSelectedColors(ArrayList<PawnColor> colors) {
        this.selectedColors.clear();
        this.selectedColors.addAll(colors);
    }

    public void setSelectedColor(PawnColor color) {
        this.selectedColor = color;
    }

    public void setSelectedisland(Island island) {
        this.selectedisland = island;
    }
}
