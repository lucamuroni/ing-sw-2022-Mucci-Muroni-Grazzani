package it.polimi.ingsw.controller.client.game;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.networkHandler.Network;
import it.polimi.ingsw.controller.networking.Phase;
import it.polimi.ingsw.view.ViewHandler;
import it.polimi.ingsw.view.asset.game.Game;

public class MotherNaturePhase implements GamePhase{
    private final Game game;
    private final Network network;
    private final ViewHandler view;
    private final ClientController controller;
    private final PhaseName name = PhaseName.MOTHERNATURE_PHASE;

    public MotherNaturePhase(Game game, ClientController controller, ViewHandler view) {
        this.game = game;
        this.controller = controller;
        this.view = view;
        this.network = this.controller.getNetwork();
    }

    @Override
    public void handle() {
        try {

        } catch () {

        }
    }

    @Override
    public GamePhase next() {
        return new Idle(this.game, this.controller, this.view);
    }

    @Override
    public Phase getNamePhase() {
        return name;
    }
}
