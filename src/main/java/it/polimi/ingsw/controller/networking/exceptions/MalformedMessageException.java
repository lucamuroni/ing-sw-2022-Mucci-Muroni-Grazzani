package it.polimi.ingsw.controller.networking.exceptions;

public class MalformedMessageException extends Exception{

    public MalformedMessageException(){
        super();
    }

    public MalformedMessageException(String s){
        super(s);
    }
}
