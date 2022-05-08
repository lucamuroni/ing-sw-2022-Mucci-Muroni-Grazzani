package it.polimi.ingsw.controller.server.game;

import it.polimi.ingsw.controller.server.game.gameController.GameController;
import it.polimi.ingsw.model.game.Game;

public class ConquerIslandPhase implements GamePhase{
    private final Game game;
    private final GameController controller;

    public ConquerIslandPhase(Game game, GameController controller){
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
