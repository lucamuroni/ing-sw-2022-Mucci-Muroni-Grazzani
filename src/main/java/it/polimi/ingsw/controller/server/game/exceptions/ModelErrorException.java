package it.polimi.ingsw.controller.server.game.exceptions;

public class ModelErrorException extends Exception{
    public ModelErrorException(){
        super();
    }

    public ModelErrorException(String s){
        super(s);
    }
}
