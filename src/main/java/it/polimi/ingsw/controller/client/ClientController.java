package it.polimi.ingsw.controller.client;

import it.polimi.ingsw.controller.client.game.ConnectionPhase;
import it.polimi.ingsw.controller.client.game.ExpertPhase;
import it.polimi.ingsw.controller.client.game.GamePhase;
import it.polimi.ingsw.controller.client.networkHandler.Network;
import it.polimi.ingsw.controller.client.networkHandler.NetworkHandler;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.GameType;
import it.polimi.ingsw.view.ViewHandler;
import it.polimi.ingsw.view.asset.game.Game;
import it.polimi.ingsw.view.cli.AnsiColor;
import java.io.IOException;


/**
 * This class implements the controller of the client
 */
public class ClientController {
    private Network network;
    private ViewHandler viewHandler;
    private GamePhase phase;
    private Game game;
    private boolean isGameFinished;
    private GameType type;

    public ClientController(MessageHandler messageHandler,ViewHandler viewHandler) {
        try {
            messageHandler.startConnection();
        } catch (IOException e) {
            System.out.println(AnsiColor.RED.toString()+"Could not initiate connection to server: Please retry"+AnsiColor.RESET.toString());
        }
        this.network = new NetworkHandler(messageHandler);
        this.viewHandler = viewHandler;
        this.viewHandler.setController(this);
        this.isGameFinished = false;
        this.run();
    }

    private void run(){
        this.phase = new ConnectionPhase(this);
        while (this.getGameStatus()){
            phase.handle();
            if(this.type == GameType.EXPERT){
                GamePhase expertPhase = new ExpertPhase(this);
                expertPhase.handle();
            }
            phase = phase.next();
        }
        // TODO : fare qualcosa per far ricominciare il gioco
    }

    public Network getNetwork() {
        return this.network;
    }

    public ViewHandler getViewHandler() {
        return this.viewHandler;
    }

    public void handleError() {
        this.handleError("Error revealed: shutting down process");
    }

    public void handleError(String s) {
        System.out.print("\n\n\n");
        System.out.println(AnsiColor.RED.toString()+s+AnsiColor.RESET.toString());
        System.exit(1);
    }

    public void setGame(Game game){
        this.game = game;
    }

    public Game getGame() {
        return this.game;
    }

    public synchronized void gameIsFinished() {
        isGameFinished = true;
    }

    private synchronized boolean getGameStatus(){
        return !this.isGameFinished;
    }

    public void setGameType(GameType type){
        this.type = type;
    }
}
