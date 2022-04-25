package it.polimi.ingsw.controller.networking;

public class ClientDisconnectedException extends Exception{

    public ClientDisconnectedException(){
        super();
    }

    public ClientDisconnectedException(String s){
        super(s);
    }
}
