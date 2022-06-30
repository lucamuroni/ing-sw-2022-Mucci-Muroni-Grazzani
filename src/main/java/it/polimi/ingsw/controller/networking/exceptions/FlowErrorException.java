package it.polimi.ingsw.controller.networking.exceptions;

/**
 * @author Davide Grazzani
 * Class that represent a synchronization error between host
 */
public class FlowErrorException extends Exception{
    /**
     * Class builder
     */
    public FlowErrorException(){
        super();
    }

    /**
     * Class builder with a custom message
     * @param s is the custom message
     */
    public FlowErrorException(String s){
        super(s);
    }
}