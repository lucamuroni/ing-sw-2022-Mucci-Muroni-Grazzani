package it.polimi.ingsw.controller.networking.exceptions;

public class TimeHasEndedException extends Exception{
    public TimeHasEndedException(){
        super();
    }

    public TimeHasEndedException(String s){
        super(s);
    }
}
