package it.polimi.ingsw.controller.client;

import it.polimi.ingsw.controller.client.game.ConnectionPhase;
import it.polimi.ingsw.controller.client.game.GamePhase;
import it.polimi.ingsw.controller.client.networkHandler.Network;
import it.polimi.ingsw.controller.client.networkHandler.NetworkHandler;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.view.ViewHandler;
import it.polimi.ingsw.view.asset.game.Game;
import it.polimi.ingsw.view.cli.AnsiColor;

import java.io.IOException;
import java.net.Socket;


/**
 * This class implements the controller of the client, which is responsible for coordination between different phases and therefore
 * the synchronization between client and server
 */
public class ClientController {
    private Network network;
    private final ViewHandler viewHandler;
    private Game game;
    private boolean isGameOn;

    /**
     * Class builder
     * @param ip is the ip of the server
     * @param port is the port of the server
     * @param viewHandler is the interface throughout is possible to control the view package
     */
    public ClientController(String ip,int port,ViewHandler viewHandler) {
        while(!getConnection(ip,port));
        this.viewHandler = viewHandler;
        this.viewHandler.setController(this);
        this.isGameOn = true;
        this.run();
    }

    /**
     * Method used to open up connection between client and server
     * @param ip is the ip of the server
     * @param port is the port of the server
     * @return true if a connection has been established between client and server
     */
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

    /**
     * Main method that cycles through different game phases
     */
    private void run(){
        GamePhase phase = new ConnectionPhase(this);
        while (getGameStatus()){
            phase.handle();
            phase = phase.next();
        }
        System.exit(0);
    }

    /**
     * Getter method
     * @return network interface used to comunicate with the server
     */
    public Network getNetwork() {
        return this.network;
    }

    /**
     * Getter method
     * @return the view interface used to control the view
     */
    public ViewHandler getViewHandler() {
        return this.viewHandler;
    }

    /**
     * Method called when a generic error occurs
     */
    public void handleError() {
        this.handleError("Error revealed: shutting down process");
    }

    /**
     * Method called when a generic error occurs
     * @param s is a verbose string
     */
    public void handleError(String s) {
        System.out.print("\n\n\n");
        System.out.println(AnsiColor.RED.toString()+s+AnsiColor.RESET.toString());
        System.exit(1);
    }

    /**
     * Setter method
     * @param game is the local game class used to store all sort of information of the game
     */
    public void setGame(Game game){
        this.game = game;
    }

    /**
     * Getter method
     * @return the game associated
     */
    public Game getGame() {
        return this.game;
    }

    /**
     * Setter method used to power off the client after ending a game
     */
    public synchronized void setGameOff(){
        this.isGameOn = false;
    }

    /**
     * Getter method
     * @return true if the game is still going on
     */
    private synchronized boolean getGameStatus(){
        return this.isGameOn;
    }
}
