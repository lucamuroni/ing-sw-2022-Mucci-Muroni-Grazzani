package it.polimi.ingsw.controller.client.game;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.networkHandler.Network;
import it.polimi.ingsw.controller.networking.GameType;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.view.ViewHandler;
import it.polimi.ingsw.view.asset.exception.AssetErrorException;
import it.polimi.ingsw.view.asset.game.Game;

/**
 * @author Luca Muroni
 * @author Davide grazzani
 * This class is the setup phase of the game, where all the info to start a game are taken from the server
 */
public class Start implements GamePhase {
    private final Game game;
    private final ViewHandler view;
    private final Network network;
    private final ClientController controller;

    /**
     * Constructor of the class
     * @param controller represents the controller linked with this game
     */
    public Start(ClientController controller) {
        this.game = controller.getGame();
        this.view = controller.getViewHandler();
        this.controller = controller;
        this.network = this.controller.getNetwork();
    }

    /**
     * This is the main method that handles the Start
     */
    @Override
    public void handle() {
        this.view.setController(controller);
        this.getUsernames();
        this.updateMNPlace();
        for(int i = 0; i< 12;i++){
            this.updateIslandStatus();
        }
        if(this.game.getGameType().equals(GameType.EXPERT.getName())){
            this.getCharacterCards();
            this.getCoins();
        }
        for (int i = 0; i<this.game.getGamers().size(); i++) {
            this.updateColor();
            this.updateDashboards();
        }
    }

    /**
     * Method called by handle() only if the game is an expert one
     * It is used to receive the characterCards that can be played in this game
     */
    private void getCharacterCards() {
        for (int i = 0; i<3; i++) {
            try {
                try {
                    this.network.getCharacterCard(game);
                } catch (MalformedMessageException e) {
                    this.network.getCharacterCard(game);
                }
            } catch (MalformedMessageException | ClientDisconnectedException e) {
                this.controller.handleError();
            } catch (AssetErrorException e) {
                this.controller.handleError("Doesn't found character card");
            }
        }
    }

    /**
     * Method called by handle() only if the game is an expert one
     * It is used to receive the coins owned by the player at the start of game
     */
    private void getCoins() {
        try {
            try {
                this.network.getCoins(game);
            } catch (MalformedMessageException e) {
                this.network.getCoins(game);
            }
        } catch (MalformedMessageException | ClientDisconnectedException e) {
            this.controller.handleError();
        }
    }

    /**
     * Method called by handle() to get the usernames of all others players
     */
    private void getUsernames() {
        for(int i = 0 ; i< this.game.getLobbySize()-1;i++) {
            try{
                try {
                    this.network.getUsernames(this.game);
                } catch (MalformedMessageException e) {
                    this.network.getUsernames(this.game);
                }
            }catch (MalformedMessageException | ClientDisconnectedException e) {
                this.controller.handleError("Could not receive info about other players");
            }
        }
    }

    /**
     * Method called by handle() to get the position of motherNature at the start of game
     */
    private void updateMNPlace() {
        try {
            try {
                this.network.getMotherNaturePlace(this.game);
            } catch (MalformedMessageException e) {
                this.network.getMotherNaturePlace(this.game);
            }
        } catch (MalformedMessageException  | ClientDisconnectedException e) {
            this.controller.handleError();
        } catch (AssetErrorException e) {
            this.controller.handleError("Doesn't found island of mother nature");
        }
    }

    /**
     * Method called by handle() and is used to update all the islands at the start of game
     */
    private void updateIslandStatus() {
        try {
            try {
                this.network.getIslandStatus(this.game);
            } catch (MalformedMessageException e) {
                this.network.getIslandStatus(this.game);
            }
        } catch (MalformedMessageException | ClientDisconnectedException e) {
            this.controller.handleError();
        } catch (AssetErrorException e) {
            this.controller.handleError("Doesn't found island");
        }
    }

    /**
     * Method called by handle() to get infos about dashboards of all others players
     */
    private void updateDashboards() {
        try {
            try {
                this.network.getDashboard(this.game);
            } catch (MalformedMessageException e) {
                this.network.getDashboard(this.game);
            }
        } catch (MalformedMessageException | ClientDisconnectedException e) {
            this.controller.handleError();
        } catch (AssetErrorException e) {
            this.controller.handleError("Doesn't found dashboard");
        }
    }

    /**
     * Method called by handle() to update the colors associated with the other players
     */
    private void updateColor() {
        try {
            try {
                this.network.getTowerColor(this.game);
            } catch (MalformedMessageException e) {
                this.network.getTowerColor(this.game);
            }
        } catch (MalformedMessageException | ClientDisconnectedException e) {
            this.controller.handleError();
        } catch (AssetErrorException e) {
            this.controller.handleError("Doesn't found tower color/gamer");
        }
    }

    /**
     * This method changes the phase to the next one
     * @return the next GamePhase
     */
    @Override
    public GamePhase next() {
        Idle phase = new Idle(this.controller);
        phase.isGameStarted(false);
        return phase;
    }
}
