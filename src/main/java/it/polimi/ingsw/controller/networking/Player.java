package it.polimi.ingsw.controller.networking;

import it.polimi.ingsw.controller.server.GameType;
import it.polimi.ingsw.model.gamer.Gamer;

/**
 * @author Davide Grazzani
 * Class that represent a Gamer in the Controller space
 */
public class Player {
    private final MessageHandler messageHandler;
    private String username;
    private int token;

    /**
     * Class builder
     * @param messageHandler is the MessageHandler associated to a certain player
     */
    public Player(MessageHandler messageHandler){
        this.messageHandler = messageHandler;
    }

    /**
     * Method used for setting up username and token of a player
     * @param username is the username of a specific Gamer (model class)
     * @param token is the unique token given to a Gamer. It's used to identify the Gamer
     */
    public void createGamer(String username,int token) {
        this.username = username;
        this.token = token;
    }

    /**
     * Getter method
     * @return the gamers' token
     */
    public int getGamer() {
        return this.token;
    }

    /**
     * Getter method
     * @return the MessageHandler of a specific gamer
     */
    public MessageHandler getMessageHandler() {
        return this.messageHandler;
    }

    /**
     * Getter method
     * @return the username of a player
     */
    public String getUsername(){
        return this.username;
    }
}
