package it.polimi.ingsw.controller.server.game;

import it.polimi.ingsw.controller.server.game.gameController.GameController;
import it.polimi.ingsw.controller.server.virtualView.View;
import it.polimi.ingsw.model.game.Game;

public class CharcterCardPhase implements GamePhase{
    private final Game game;
    private final GameController controller;
    private final View view;

    public CharcterCardPhase(Game game, GameController controller) {
        this.game = game;
        this.controller = controller;
        this.view = this.controller.getView();
    }

    public void handle() {

    }

    public GamePhase next() {
        return null;
    }
}
