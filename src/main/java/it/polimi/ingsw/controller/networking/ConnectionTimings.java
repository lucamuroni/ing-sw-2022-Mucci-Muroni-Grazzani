package it.polimi.ingsw.controller.networking;

public enum ConnectionTimings {
    CONNECTION_STARTUP(40000),
    INFINTE(600000);

    private int timeToRespond;
    private ConnectionTimings(int timeToRespond){
        this.timeToRespond = timeToRespond;
    }

    public int getTiming(){
        return this.timeToRespond;
    }
}
