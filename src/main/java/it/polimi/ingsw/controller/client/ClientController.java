package it.polimi.ingsw.controller.client;

import it.polimi.ingsw.controller.client.game.GamePhase;
import it.polimi.ingsw.controller.client.game.Start;
import it.polimi.ingsw.controller.client.networkHandler.Network;
import it.polimi.ingsw.controller.client.networkHandler.NetworkHandler;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.view.ViewHandler;
import it.polimi.ingsw.view.asset.game.Game;

/**
 * This class implements the controller of the client
 */
public class ClientController {
    private Network network;
    private ViewHandler viewHandler;
    private GamePhase phase;
    //private MessageHandler messageHandler;
    private Game game;

    public ClientController(MessageHandler messageHandler){
        //this.messageHandler = messageHandler;
        this.network = new NetworkHandler(messageHandler);
        this.run();
    }

    private void run(){

    }

    public Network getNetwork() {
        return this.network;
    }
}
