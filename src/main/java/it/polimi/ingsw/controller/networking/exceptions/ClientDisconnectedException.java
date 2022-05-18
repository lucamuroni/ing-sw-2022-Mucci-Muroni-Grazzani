package it.polimi.ingsw.controller.networking.exceptions;

public class ClientDisconnectedException extends Exception{

    public ClientDisconnectedException(){
        super();
    }

    public ClientDisconnectedException(String s){
        super(s);
    }
}
