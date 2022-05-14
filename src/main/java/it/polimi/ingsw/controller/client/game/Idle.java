package it.polimi.ingsw.controller.client.game;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.networkHandler.Network;
import it.polimi.ingsw.view.asset.game.Game;

public class Idle implements GamePhase{
    private Network network;
    private ClientController controller;
    private Game game;
    private GamePhase phase;

    public Idle(Game game ,ClientController controller) {
        this.game = game;
        this.controller = controller;
        this.network = this.controller.getNetwork();
    }

    @Override
    public void handle() {
        try {
            phase = this.network.getPhase();
        } catch ()
    }

    @Override
    public GamePhase next() {
        return null;
    }
}
