package it.polimi.ingsw.controller.server.game;

import it.polimi.ingsw.controller.server.game.gameController.GameController;
import it.polimi.ingsw.model.game.Game;

public class PlanningPhase implements GamePhase{
    private final Game game;
    private final GameController controller;

    public PlanningPhase(Game game, GameController controller){
        this.game = game;
        this.controller = controller;
    }
    public void handle (){
        //riempimento delle nuvole
        //scelta della carta assistente
    };

    public GamePhase next(){
        return new ActionPhase1(this.game,this.controller);
    };
}
