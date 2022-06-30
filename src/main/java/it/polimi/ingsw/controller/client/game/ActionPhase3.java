package it.polimi.ingsw.controller.client.game;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.networkHandler.Network;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.view.ViewHandler;
import it.polimi.ingsw.view.asset.exception.AssetErrorException;
import it.polimi.ingsw.view.asset.game.Cloud;
import it.polimi.ingsw.view.asset.game.Game;

import java.util.ArrayList;

/**
 * @author Luca Muroni
 * This class implements the fourth phase of the game, which is the ActionPhase3, where the currentPlayer chooses a cloud
 */
public class ActionPhase3 implements GamePhase {
    private final Game game;
    private final ViewHandler view;
    private final Network network;
    private final ClientController controller;

    /**
     * Constructor of the class
     * @param controller is the controller of client side
     */
    public ActionPhase3(ClientController controller) {
        this.game = controller.getGame();
        this.view = controller.getViewHandler();
        this.controller = controller;
        this.network = this.controller.getNetwork();
    }

    /**
     * This is the main method that handles the ActionPhase3
     */
    @Override
    public void handle() {
        ArrayList<Cloud> clouds = null;
        try {
            try {
                clouds = this.network.getPossibleClouds(this.game);
            } catch (MalformedMessageException e) {
                clouds = this.network.getPossibleClouds(this.game);
            }
        } catch (MalformedMessageException | ClientDisconnectedException e) {
            this.controller.handleError();
        } catch (AssetErrorException e) {
            this.controller.handleError("Doesn't found cloud");
        }
        Cloud cloud = this.view.chooseCloud(clouds);
        try {
            try {
                this.network.sendCloud(cloud);
            } catch (MalformedMessageException e) {
                this.network.sendCloud(cloud);
            }
        } catch (MalformedMessageException | FlowErrorException e) {
            this.controller.handleError();
        }
    }

    /**
     * This method changes the phase to the next one
     * @return the next GamePhase
     */
    @Override
    public GamePhase next() {
        return new Idle(this.controller);
    }
}

