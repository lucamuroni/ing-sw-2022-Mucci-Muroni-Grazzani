package it.polimi.ingsw.controller.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class Server {
    private ServerSocket serverSocket;
    private final ClientReception clientReception;
    private final static int maxNumOfInstances = 4;
    private int numOfInstances = 0;
    private boolean isEveryThingOK = true;

    public Server(int portNumber){
        try {
            this.serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            System.out.println("Could not instantiate a new ServerSocket ; Exiting");
            e.printStackTrace();
        }
        this.clientReception = new ClientReception(this.serverSocket);
        this.run();

    }

    private void run(){
        this.clientReception.start();
        this.gameStarter();
    }


    private void gameStarter(){
        Thread t = new Thread(()->{
            while(isEveryThingOK){
                Lobby lobbyToStart = null;
                boolean lobbyAlreadyChosen = false;
                synchronized (this.clientReception.lobbiesLock){
                    while (this.clientReception.getLobbies().isEmpty() || this.numOfInstances<maxNumOfInstances){
                        try {
                            this.clientReception.lobbiesLock.wait();
                        } catch (InterruptedException e) {
                            System.out.println("Could not wait for scannig of lobbyes");
                            e.printStackTrace();
                        }
                    }
                    for(Lobby lobby : this.clientReception.getLobbies()){
                        if(lobby.isLobbyReady() && !lobbyAlreadyChosen){
                            lobbyAlreadyChosen = true;
                            lobbyToStart = lobby;
                            this.clientReception.getLobbies().remove(lobby);
                        }
                    }
                }
                if(lobbyToStart != null){
                    this.numOfInstances++;
                    lobbyToStart.startGame(this);
                }
            }
        });
        t.start();
    }

    public void signalFatalError(){
        System.out.println("Fatal Error revealed -> Shutting down everything");
        this.isEveryThingOK = false;
        this.clientReception.shutdown();
    }
}
