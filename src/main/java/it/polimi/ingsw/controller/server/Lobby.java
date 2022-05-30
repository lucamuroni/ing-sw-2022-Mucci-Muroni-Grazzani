package it.polimi.ingsw.controller.server;

import it.polimi.ingsw.controller.networking.Player;
import it.polimi.ingsw.controller.server.game.gameController.ExpertGameController;
import it.polimi.ingsw.controller.server.game.gameController.GameController;
import java.util.ArrayList;

//TOOD: javadoc
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
    // TODO : fare partire i controller
    public void startGame(Server server){
        Thread t = new Thread(()->{
            System.out.println("A new game is about to start");
            if(this.type==GameType.NORMAL){
                GameController gameController = new GameController(server,this.players);
            }else{
                ExpertGameController gameController = new ExpertGameController(server,this.players);
            }
        });
        t.start();
    }

    public void removePlayer(Player player){
        this.players.remove(player);
    }

    public boolean contains(Player player){
        return this.players.contains(player);
    }

}
