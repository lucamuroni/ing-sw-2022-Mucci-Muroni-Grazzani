package it.polimi.ingsw.controller.client.game;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.networkHandler.Network;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.view.ViewHandler;
import it.polimi.ingsw.view.asset.game.Cloud;
import it.polimi.ingsw.view.asset.game.Game;

import java.util.ArrayList;

public class ActionPhase3 implements GamePhase {
    private final Game game;
    private final ViewHandler view;
    private final Network network;
    private final ClientController controller;

    public ActionPhase3(Game game, ClientController controller, ViewHandler view) {
        this.game = game;
        this.view = view;
        this.controller = controller;
        this.network = this.controller.getNetwork();
    }
    @Override
    public void handle() {
        ArrayList<Cloud> clouds = new ArrayList<>();
        try {
            try {
                clouds.addAll(this.network.getPossibleClouds(this.game));
            } catch (MalformedMessageException e) {
                clouds.addAll(this.network.getPossibleClouds(this.game));
            }
        } catch (MalformedMessageException | TimeHasEndedException | ClientDisconnectedException e) {
            this.controller.handleError();
        }
        Cloud cloud = this.view.chooseCloud(clouds);
        try {
            try {
                this.network.sendCloud(cloud);
            } catch (MalformedMessageException e) {
                this.network.sendCloud(cloud);
            }
        } catch (MalformedMessageException | TimeHasEndedException | FlowErrorException e) {
            this.controller.handleError();
        }
    }

    @Override
    public GamePhase next() {
        return null;
    }
}
