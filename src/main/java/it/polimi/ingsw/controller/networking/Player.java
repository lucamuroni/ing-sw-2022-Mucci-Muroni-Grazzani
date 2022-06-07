package it.polimi.ingsw.controller.networking;

import it.polimi.ingsw.controller.server.game.exceptions.ModelErrorException;
import it.polimi.ingsw.model.gamer.Gamer;
import java.util.ArrayList;

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
     * Method used to set username and token for a player
     * @param username is the username of a specific Gamer (model class)
     * @param token is the unique token given to a Gamer. It's used to identify the Gamer
     */
    public void createGamer(String username,int token) {
        this.username = username;
        this.token = token;
    }

    /**
     * Getter method
     * @return the gamer token
     */
    public int getToken() {
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

    /**
     * Getter method that return the corrispettive Gamer found in the Model package
     * @param gamers is the arrayList of gamers that can be found in the model
     * @return the gamer that is the corrispettive of this player
     * @throws ModelErrorException if the model has no such gamer that is equals to this player
     */
    public Gamer getGamer(ArrayList<Gamer> gamers) throws ModelErrorException {
        for(Gamer gamer : gamers){
            if(gamer.getUsername().equals(this.username) && this.token==gamer.getToken()){
                System.out.println(" colore giocatore "+ gamer.getTowerColor().toString());
                return gamer;
            }
        }
        throw new ModelErrorException("No such player found in model");
    }
}
