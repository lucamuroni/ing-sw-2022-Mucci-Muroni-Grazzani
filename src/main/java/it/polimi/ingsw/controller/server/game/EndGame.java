package it.polimi.ingsw.controller.server.game;

import it.polimi.ingsw.controller.server.game.gameController.GameController;
import it.polimi.ingsw.controller.server.virtualView.View;
import it.polimi.ingsw.model.game.Game;

/**
 * This class represents the last phase of the game, when a player has won the game and now the game must be closed
 */
public class EndGame implements GamePhase {
    private final Game game;
    private final GameController controller;

    /**
     * Constructor of the game
     * @param game represents the current game
     * @param controller represents the controller linked with this game
     */
    public EndGame(Game game, GameController controller){
        this.game = game;
        this.controller = controller;
    }

    /**
     * This is the main method that handles the EndGame
     */
    @Override
    public void handle() {

    }

    /**
     * This method changes the phase to the next one
     * @return the next GamePhase
     */
    @Override
    public GamePhase next() {
        return null;
    }
}
