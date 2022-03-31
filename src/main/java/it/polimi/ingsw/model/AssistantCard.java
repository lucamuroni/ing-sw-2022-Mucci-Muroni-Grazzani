package it.polimi.ingsw.debug;

public class AssistantCard {
    private int turnValue;
    private int movement;

    public AssistantCard(int turn, int move){
        turnValue = turn;
        movement = move;
    }

    public int getMovement() {
        return movement;
    }

    public int getTurnValue() {
        return turnValue;
    }
}
