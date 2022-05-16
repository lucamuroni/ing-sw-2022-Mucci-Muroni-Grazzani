package it.polimi.ingsw.controller.client.game;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.networkHandler.Network;
import it.polimi.ingsw.view.ViewHandler;
import it.polimi.ingsw.view.asset.game.Game;

public class ConquerIslandPhase implements GamePhase{
    private final Game game;
    private final Network network;
    private final ViewHandler view;
    private final ClientController clientController;
    private final PhaseName name = PhaseName.CONQUERISLAND_PHASE;

    public ConquerIslandPhase(Game game, ClientController clientController, ViewHandler view) {
        this.game = game;
        this.view = view;
        this.clientController = clientController;
        this.network = this.clientController.getNetwork();
    }

    @Override
    public void handle() {

    }

    @Override
    public GamePhase next() {
        return new Idle(this.game, this.clientController, this.view);
    }

    @Override
    public PhaseName getNamePhase() {
        return name;
    }
}
