package it.polimi.ingsw.controller.networking.exceptions;

public class ModelErrorException extends Exception{
    public ModelErrorException(){
        super();
    }

    public ModelErrorException(String s){
        super(s);
    }
}
