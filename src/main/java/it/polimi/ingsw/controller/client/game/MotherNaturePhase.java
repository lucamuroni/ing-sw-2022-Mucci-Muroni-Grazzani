package it.polimi.ingsw.controller.client.game;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.networkHandler.Network;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.view.ViewHandler;
import it.polimi.ingsw.view.asset.exception.AssetErrorException;
import it.polimi.ingsw.view.asset.game.Game;
import it.polimi.ingsw.view.asset.game.Island;

import java.util.ArrayList;

/**
 * @author Luca Muroni
 * This class implements the first part of the third phase of the game, which is the MotherNaturePhase, and in particular this part
 * handles the movement of MotherNature
 */
public class MotherNaturePhase implements GamePhase{
    private final Game game;
    private final Network network;
    private final ViewHandler view;
    private final ClientController controller;

    /**
     * Constructor of the class
     * @param controller is the controller of client side
     */
    public MotherNaturePhase(ClientController controller) {
        this.game = controller.getGame();
        this.controller = controller;
        this.view = controller.getViewHandler();
        this.network = this.controller.getNetwork();
    }

    /**
     * This is the main method that handles the MotherNaturePhase
     */
    @Override
    public void handle() {
        ArrayList<Island> islands = new ArrayList<>();
        try {
            try {
                islands.addAll(this.network.getPossibleIslands(this.game));
            } catch (MalformedMessageException e) {
                islands.addAll(this.network.getPossibleIslands(this.game));
            }
        } catch (MalformedMessageException | ClientDisconnectedException e) {
            this.controller.handleError();
        } catch (AssetErrorException e) {
            this.controller.handleError("Doesn't found island");
        }
        Island island = this.view.chooseIsland(islands, false);
        try {
            try {
                this.network.sendIsland(island);
            } catch (MalformedMessageException e) {
                this.network.sendIsland(island);
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
