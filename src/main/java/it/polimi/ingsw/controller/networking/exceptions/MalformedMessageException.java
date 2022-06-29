package it.polimi.ingsw.controller.networking.exceptions;

/**
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
     * Class builder
     */
    public MalformedMessageException(String s){
        super(s);
    }
}
