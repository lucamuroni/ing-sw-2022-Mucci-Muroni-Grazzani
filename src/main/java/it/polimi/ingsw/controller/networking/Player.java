package it.polimi.ingsw.controller.networking;

import it.polimi.ingsw.controller.server.GameType;
import it.polimi.ingsw.model.gamer.Gamer;

public class Player {
    private final MessageHandler messageHandler;
    private String username;
    private int token;


    public Player(MessageHandler messageHandler){
        this.messageHandler = messageHandler;
    }

    public void createGamer(String username,int token) {
        this.username = username;
        this.token = token;
    }

    public int getGamer() {
        return this.token;
    }

    public MessageHandler getMessageHandler() {
        return this.messageHandler;
    }
}
