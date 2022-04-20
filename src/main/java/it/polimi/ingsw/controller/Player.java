package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.gamer.Gamer;

public class Player {
    private final ConnectionHandler connectionHandler;
    private Gamer gamer;

    public Player(ConnectionHandler connectionHandler){
        this.connectionHandler = connectionHandler;
    }

    public void createGamer(String username,int token) {
        this.gamer = new Gamer(token, username);
    }

    public Gamer getGamer() {
        return this.gamer;
    }

    public ConnectionHandler getConnectionHandler() {
        return this.connectionHandler;
    }
}
