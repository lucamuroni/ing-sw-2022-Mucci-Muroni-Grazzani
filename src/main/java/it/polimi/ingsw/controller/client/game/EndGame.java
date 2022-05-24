package it.polimi.ingsw.controller.client.game;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.networkHandler.Network;
import it.polimi.ingsw.controller.networking.Phase;
import it.polimi.ingsw.view.ViewHandler;
import it.polimi.ingsw.view.asset.game.Game;

public class EndGame implements GamePhase{
    private final Game game;
    private final ViewHandler view;
    private final Network network;
    private final ClientController controller;
    public EndGame(Game game, ClientController controller, ViewHandler view) {
        this.game = game;
        this.view = view;
        this.controller = controller;
        this.network = this.controller.getNetwork();
    }

    @Override
    public void handle() {
        
    }

    @Override
    public GamePhase next() {
        return null;
    }

    @Override
    public Phase getPhase() {
        return null;
    }
}
