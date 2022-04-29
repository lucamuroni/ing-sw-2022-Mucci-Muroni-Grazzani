package it.polimi.ingsw.controller.networking;

public class TimeHasEndedException extends Exception{
    public TimeHasEndedException(){
        super();
    }

    public TimeHasEndedException(String s){
        super(s);
    }
}
