package it.polimi.ingsw.controller.networking.exceptions;

public class MalformedMessageException extends Exception{
    private final boolean areConnectionLinesBeenReset;

    public MalformedMessageException(){
        super();
        areConnectionLinesBeenReset = false;
    }

    public MalformedMessageException(String s){
        super(s);
        areConnectionLinesBeenReset = false;
    }

    public MalformedMessageException(boolean bol,String s){
        super(s);
        areConnectionLinesBeenReset = bol;
    }

    public MalformedMessageException (boolean bol){
        super();
        areConnectionLinesBeenReset = bol;
    }
}
