package it.polimi.ingsw.controller.server;

import java.net.ServerSocket;

public class ClientConnectionAccepter extends Thread{
    private final ServerSocket serverSocket;
    private boolean isON;

    public ClientConnectionAccepter(ServerSocket socket){
        this.serverSocket = socket;
        this.isON = true;
    }



}
