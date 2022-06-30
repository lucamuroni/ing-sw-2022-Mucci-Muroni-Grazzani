package it.polimi.ingsw.controller.client.game;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.networkHandler.Network;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.view.ViewHandler;

/**
 * Class used for handling initial connection between client and server, right before the start of a game
 * @author Davide Grazzani
 */
public class ConnectionPhase implements GamePhase{
    private final ClientController controller;
    private final ViewHandler view;
    private final Network network;

    /**
     * Class constructor
     * @param controller is the controller of the client (ClientController class)
     */
    public ConnectionPhase(ClientController controller){
        this.controller = controller;
        this.view = this.controller.getViewHandler();
        this.network = this.controller.getNetwork();
    }

    /**
     * Main class method that handle initial connection with server, the broadcasting of player info (such as nickname or game type)
     * and to await for a game to being started by server
     */
    @Override
    public void handle() {
        synchronized (this){
            try{
                this.wait(3000);
            }catch (InterruptedException e){
                this.controller.handleError();
            }
        }
        try{
            try{
                this.network.getConnection(this.controller);
            }catch (MalformedMessageException e){
                this.network.getConnection(this.controller);
            }
        }catch (MalformedMessageException | FlowErrorException | ClientDisconnectedException e){
            this.controller.handleError("Error while starting the connection");
        }
        this.view.getPlayerInfo();
        try{
            try{
                this.network.broadcastPlayerInfo(this.controller.getGame());
            }catch (MalformedMessageException e){
                e.printStackTrace();
                this.network.broadcastPlayerInfo(this.controller.getGame());
            }
        }catch (MalformedMessageException e){
            this.controller.handleError("Error while connecting to the server");
        }
        try{
            try {
                this.network.awaitForLobby();
            }catch (MalformedMessageException e){
                this.network.awaitForLobby();
            }
        }catch (MalformedMessageException | FlowErrorException | ClientDisconnectedException e){
            this.controller.handleError("Error while inserting player into the lobby");
        }
        this.view.lobbyFounded();
    }

    /**
     * Method used to link this GamePhase to the next one
     * @return a new Start Phase
     */
    @Override
    public GamePhase next() {
        return new Start(this.controller);
    }

}
