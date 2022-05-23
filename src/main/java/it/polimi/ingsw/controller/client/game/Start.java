package it.polimi.ingsw.controller.client.game;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.networkHandler.Network;
import it.polimi.ingsw.controller.networking.Phase;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.view.ViewHandler;
import it.polimi.ingsw.view.asset.game.Game;

public class Start extends GamePhase {
    private final Game game;
    private final ViewHandler view;
    private final Network network;
    private final ClientController controller;

    public Start(Game game, ClientController controller, ViewHandler view) {
        this.game = game;
        this.view = view;
        this.controller = controller;
        this.network = this.controller.getNetwork();
    }

    @Override
    public void handle() {
        this.updateMNPlace();
        //TODO: eri qui
    }

    private void updateMNPlace() {
        try {
            try {
                this.network.getMotherNaturePlace(this.game);
            } catch (MalformedMessageException e) {
                this.network.getMotherNaturePlace(this.game);
            }
        } catch (MalformedMessageException | TimeHasEndedException | ClientDisconnectedException e) {
            this.controller.handleError();
        }
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
