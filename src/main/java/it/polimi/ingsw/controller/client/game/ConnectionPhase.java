package it.polimi.ingsw.controller.client.game;

import it.polimi.ingsw.controller.networking.Phase;
import it.polimi.ingsw.view.asset.game.Game;
import it.polimi.ingsw.view.asset.game.Gamer;

public class ConnectionPhase implements GamePhase{
    private Game game;
    private Gamer gamer;
    @Override
    public void handle() {

    }

    @Override
    public GamePhase next() {
        return ;
    }

}
