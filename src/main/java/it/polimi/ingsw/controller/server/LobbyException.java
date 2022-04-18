package it.polimi.ingsw.controller.server;

public class LobbyException extends Exception{
    public LobbyException(){
        super();
    }

    public LobbyException(String error){
        super(error);
    }
}
