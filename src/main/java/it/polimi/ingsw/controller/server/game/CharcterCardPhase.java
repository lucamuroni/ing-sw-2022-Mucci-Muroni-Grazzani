package it.polimi.ingsw.controller.server.game;

import it.polimi.ingsw.controller.server.game.gameController.GameController;
import it.polimi.ingsw.controller.server.virtualView.View;
import it.polimi.ingsw.model.game.Game;

/**
 * This class represents the phase where can be managed all the aspects of an expertGame
 */
public class CharcterCardPhase implements GamePhase{
    private final Game game;
    private final GameController controller;
    private final View view;

    /**
     * Constructor of the game
     * @param game represents the current game
     * @param controller represents the controller linked with this game
     */
    public CharcterCardPhase(Game game, GameController controller) {
        this.game = game;
        this.controller = controller;
        this.view = this.controller.getView();
    }

    /**
     * This is the main method that handles the CharacterCardPhase
     */
    public void handle() {

    }

    /**
     * This method changes the phase to the next one
     * @return the next GamePhase
     */
    public GamePhase next() {
        return null;
    }
}
