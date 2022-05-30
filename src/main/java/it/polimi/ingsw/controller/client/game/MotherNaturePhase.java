package it.polimi.ingsw.controller.client.game;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.networkHandler.Network;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.view.ViewHandler;
import it.polimi.ingsw.view.asset.game.Game;
import it.polimi.ingsw.view.asset.game.Island;

import java.util.ArrayList;

public class MotherNaturePhase implements GamePhase{
    private final Game game;
    private final Network network;
    private final ViewHandler view;
    private final ClientController controller;

    public MotherNaturePhase(ClientController controller) {
        this.game = controller.getGame();
        this.controller = controller;
        this.view = controller.getViewHandler();
        this.network = this.controller.getNetwork();
    }

    @Override
    public void handle() {
        ArrayList<Island> islands = new ArrayList<>();
        try {
            try {
                islands.addAll(this.network.getPossibleIslands(this.game));
            } catch (MalformedMessageException e) {
                islands.addAll(this.network.getPossibleIslands(this.game));
            }
        } catch (MalformedMessageException | TimeHasEndedException | ClientDisconnectedException e) {
            this.controller.handleError();
        }
        Island island = this.view.chooseIsland(islands);
        try {
            try {
                this.network.sendIsland(island);
            } catch (MalformedMessageException e) {
                this.network.sendIsland(island);
            }
        } catch (MalformedMessageException | TimeHasEndedException | FlowErrorException e) {
            this.controller.handleError();
        }
    }

    @Override
    public GamePhase next() {
        return new Idle(this.controller);
    }
}
