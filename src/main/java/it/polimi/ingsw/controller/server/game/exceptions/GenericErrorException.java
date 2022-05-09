package it.polimi.ingsw.controller.server.game.exceptions;

public class GenericErrorException extends Exception{
    public GenericErrorException(){
        super();
    }

    public GenericErrorException(String s){
        super(s);
    }
}
