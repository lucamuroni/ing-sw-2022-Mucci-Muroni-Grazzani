package it.polimi.ingsw.controller.server;

import it.polimi.ingsw.controller.networking.MalformedMessageException;
import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.Player;

import java.io.IOException;
import java.net.ServerSocket;

public class ClientReception extends Thread{
    private final ServerSocket serverSocket;
    private boolean isON;

    public ClientReception(ServerSocket socket){
        this.serverSocket = socket;
        this.isON = true;
    }

    @Override
    public void run() {
        MessageHandler messageHandler;
        while(this.isON){
            try {
                messageHandler = new MessageHandler(this.serverSocket.accept());
                messageHandler.startConnection();
                Player player = new Player(messageHandler);
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
        int uniqueMsgID = player.getMessageHandler().getNewUniqueTopicID();
        Message m0 = new Message("auth","",uniqueMsgID);
        try {
            player.getMessageHandler().write(m0);
        } catch (MalformedMessageException e) {
            System.out.println("Some strange behavior detected : while initializing some messages where already present");
            player.getMessageHandler().writeOut();
        } finally {
            try {
                player.getMessageHandler().write(m0);
            } catch (MalformedMessageException e) {
                System.out.println("Broken MessageHandler revealed");
                e.printStackTrace();
            }
        }
        player.getMessageHandler().writeOut();
    }
}
