package it.polimi.ingsw.controller.networking.exceptions;

/**
 * @author Davide Grazzani
 * Class that represent a disconnection of the other host
 */
public class ClientDisconnectedException extends Exception{
    /**
     * Class builder
     */
    public ClientDisconnectedException(){
        super();
    }

    /**
     * Class builder with a custom message
     * @param s is the custom message
     */
    public ClientDisconnectedException(String s){
        super(s);
    }
}