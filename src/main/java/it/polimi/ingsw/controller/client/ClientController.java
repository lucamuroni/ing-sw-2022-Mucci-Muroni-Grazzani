package it.polimi.ingsw.controller.client;

import it.polimi.ingsw.controller.client.game.ConnectionPhase;
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
    private Game game;

    public ClientController(MessageHandler messageHandler,ViewHandler viewHandler){
        this.network = new NetworkHandler(messageHandler);
        this.viewHandler = viewHandler;
        this.run();
    }

    private void run(){
        this.phase = new ConnectionPhase(this);
    }

    public Network getNetwork() {
        return this.network;
    }

    public ViewHandler getViewHandler() {
        return this.viewHandler;
    }

    public void handleError() {
        System.out.print("\n\n\n");
        System.out.println(AnsiColor.RED.toString()+"Error revealed: shutting down process"+AnsiColor.RESET.toString());
        System.exit(1);
    }
}
