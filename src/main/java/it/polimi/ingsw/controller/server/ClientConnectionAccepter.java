package it.polimi.ingsw.controller.server;

import it.polimi.ingsw.controller.ConnectionHandler;
import it.polimi.ingsw.controller.Player;

import java.io.IOException;
import java.net.ServerSocket;

public class ClientConnectionAccepter extends Thread{
    private final ServerSocket serverSocket;
    private boolean isON;

    public ClientConnectionAccepter(ServerSocket socket){
        this.serverSocket = socket;
        this.isON = true;
    }

    @Override
    public void run() {
        ConnectionHandler clientHandler;
        while(this.isON){
            try {
                clientHandler = new ConnectionHandler(this.serverSocket.accept());
                clientHandler.start();
                Player player = new Player(clientHandler);
                playerHandShake(player);
            } catch (IOException e) {
                System.out.println("Error while accepting a new client on ServerSocket");
                e.printStackTrace();
            }
        }
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            System.out.println("Could not close Server Socket");
            e.printStackTrace();
        }
    }

    private void playerHandShake(Player player){
        //prima connessione con il client e aggiunta client a lobby
    }
}
