package it.polimi.ingsw.controller.server.game;

import it.polimi.ingsw.controller.server.game.gameController.GameController;
import it.polimi.ingsw.model.game.Game;

public interface GamePhase {
    public void handle (Game game, GameController controller);
    public GamePhase next();
}
