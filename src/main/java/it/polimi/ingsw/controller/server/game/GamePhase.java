package it.polimi.ingsw.controller.server.game;

/**
 * @author Davide Grazzani
 * Interface that represents a generic phase of the game
 */
public interface GamePhase {
    /**
     * Method used to handle the phase
     */
    void handle ();

    /**
     * Method used to change to the next phase
     * @return the next GamePhase
     */
    GamePhase next();
}
