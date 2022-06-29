package it.polimi.ingsw.controller.networking.exceptions;

/**
 * Class that represent a disconnection of the other host
 * @author Davide Grazzani
 */
public class ClientDisconnectedException extends Exception{
    /**
     * Class builder
     */
    public ClientDisconnectedException(){
        super();
    }

    /**
     * Class builder
     */
    public ClientDisconnectedException(String s){
        super(s);
    }
}
