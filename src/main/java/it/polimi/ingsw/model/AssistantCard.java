package it.polimi.ingsw.model;

/**
 * @author Luca Muroni
 * Class that represents the cards that every player must play during the first part of the round
 */
public class AssistantCard {
    private int turnValue;
    private int movement;

    /**
     * Class constructor
     * @param turn represents the value of the card used for turn order
     * @param move represents the number of steps that MotherNature can do
     */
    public AssistantCard(int turn, int move){
        turnValue = turn;
        movement = move;
    }

    /**
     * Getter method
     * @return the steps MotherNature can do
     */
    public int getMovement() {
        return movement;
    }

    /**
     * Getter method
     * @return the value for turn order
     */
    public int getTurnValue() {
        return turnValue;
    }
}
