package it.polimi.ingsw.controller.server.game;

import it.polimi.ingsw.controller.server.game.gameController.GameController;
import it.polimi.ingsw.model.game.Game;

public class ActionPhase1 implements GamePhase{
    private final Game game;
    private final GameController controller;

    public ActionPhase1(Game game, GameController controller){
        this.game = game;
        this.controller = controller;
    }
    @Override
    public void handle() {

    }

    @Override
    public GamePhase next() {
        return null;
    }
}
