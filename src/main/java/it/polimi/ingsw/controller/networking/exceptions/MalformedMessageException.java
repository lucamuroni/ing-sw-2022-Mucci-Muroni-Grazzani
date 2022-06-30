package it.polimi.ingsw.controller.networking.exceptions;

/**
 * @author Davide Grazzani
 * Class that represent a message format error
 */
public class MalformedMessageException extends Exception{
    /**
     * Class builder
     */
    public MalformedMessageException(){
        super();
    }

    /**
     * Class builder with a custom message
     * @param s is the custom message
     */
    public MalformedMessageException(String s){
        super(s);
    }
}