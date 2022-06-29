package it.polimi.ingsw.controller.networking.exceptions;

/**
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
     * Class builder
     */
    public FlowErrorException(String s){
        super(s);
    }
}
