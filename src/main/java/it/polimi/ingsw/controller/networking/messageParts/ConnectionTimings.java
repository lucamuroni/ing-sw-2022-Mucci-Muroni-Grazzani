package it.polimi.ingsw.controller.networking.messageParts;

/**
 * @author Davide Grazzani
 * Enum used to define standard timing constrains for user action
 */
public enum ConnectionTimings {
    CONNECTION_STARTUP(40000),
    PLAYER_MOVE(30000),
    INFINITE(600000);

    private int timeToRespond;

    /**
     * Enum Builder
     * @param timeToRespond represent the maximum time given to the client to make an action
     */
    private ConnectionTimings(int timeToRespond){
        this.timeToRespond = timeToRespond;
    }
    /**
     * Getter method
     * @return the time associated with the enum's keyword
     */
    public int getTiming(){
        return this.timeToRespond;
    }
}
