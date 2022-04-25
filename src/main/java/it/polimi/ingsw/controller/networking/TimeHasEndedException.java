package it.polimi.ingsw.controller.networking;

class TimeHasEndedException extends Exception{
    public TimeHasEndedException(){
        super();
    }

    public TimeHasEndedException(String s){
        super(s);
    }
}
