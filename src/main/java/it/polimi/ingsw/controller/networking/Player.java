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
        this.gamer = new Gamer(token, username);
    }

    public Gamer getGamer() {
        return this.gamer;
    }

    public MessageHandler getMessageHandler() {
        return this.messageHandler;
    }
}
