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
import java.net.Socket;


/**
 * This class implements the controller of the client
 */
public class ClientController {
    private Network network;
    private ViewHandler viewHandler;
    private GamePhase phase;
    private Game game;
    private boolean isGameFinished;

    public ClientController(String ip,int port,ViewHandler viewHandler) {
        while(!getConnection(ip,port));
        this.viewHandler = viewHandler;
        this.viewHandler.setController(this);
        this.isGameFinished = false;
        this.run();
    }

    private boolean getConnection(String ip,int port){
        try{
            MessageHandler messageHandler = new MessageHandler(new Socket(ip,port));
            messageHandler.startConnection();
            this.network = new NetworkHandler(messageHandler);
            return true;
        }catch (IOException e){
            synchronized (this){
                try {
                    this.wait(1000);
                } catch (InterruptedException ex) {
                }
            }
            return false;
        }
    }

    private void run(){
        this.phase = new ConnectionPhase(this);
        while (this.getGameStatus()){
            phase.handle();
            phase = phase.next();
        }
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

}
