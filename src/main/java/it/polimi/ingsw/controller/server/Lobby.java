package it.polimi.ingsw.controller.server;

import it.polimi.ingsw.controller.networking.Player;

import java.util.ArrayList;

public class Lobby {
    private final GameType type;
    private final int numPlayers;
    private ArrayList<Player> players;

    public Lobby(GameType type, int numPlayers, Player gamer){
        this.type = type;
        this.numPlayers = numPlayers;
        this.players = new ArrayList<Player>();
        this.players.add(gamer);
    }

    public synchronized void addPlayer(Player gamer) throws LobbyException {
        if(isLobbyReady()){
            throw new LobbyException("Lobby is already full");
        }
        this.players.add(gamer);
    }

    private GameType getType(){
        return this.type;
    }

    private int getLobbySize(){
        return this.players.size();
    }

    private boolean isLobbyReady(){
        if(getLobbySize()==this.numPlayers){
            return true;
        }else{
            return false;
        }
    }

    public synchronized void startGame() throws LobbyException {
        if(!isLobbyReady()){
            throw new LobbyException("Lobby is not ready");
        }else{
            //creazione gamecontroller e di conseguenza partita
        }
    }

}
