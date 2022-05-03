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

    public void addPlayer(Player gamer){
        this.players.add(gamer);
    }

    public GameType getType(){
        return this.type;
    }

    private int getLobbySize(){
        return this.players.size();
    }

    public boolean isLobbyReady(){
        if(this.getLobbySize()==this.numPlayers){
            return true;
        }else{
            return false;
        }
    }
    // TODO : fare metodo startGame come Thread
    public void startGame(){
        Thread t = new Thread(()->{
            //creazione gamecontroller e di conseguenza partita
        });
    }

    public void removePlayer(Player player){
        this.players.remove(player);
    }

    public boolean contains(Player player){
        return this.players.contains(player);
    }

}
