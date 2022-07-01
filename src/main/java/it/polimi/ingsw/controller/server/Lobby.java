package it.polimi.ingsw.controller.server;

import it.polimi.ingsw.controller.networking.GameType;
import it.polimi.ingsw.controller.networking.Player;
import it.polimi.ingsw.controller.server.game.GameController;
import java.util.ArrayList;

/**
 * Class that represent a game before the actual starting of the game
 * @author Davide Grazzani
 */
public class Lobby {
    private final GameType type;
    private final int numPlayers;
    private final ArrayList<Player> players;

    /**
     * Class builder
     * @param type is the type of the lobby
     * @param numPlayers is the size of the lobby
     * @param gamer is the first player that creates this lobby
     */
    public Lobby(GameType type, int numPlayers, Player gamer){
        this.type = type;
        this.numPlayers = numPlayers;
        this.players = new ArrayList<Player>();
        this.players.add(gamer);
    }

    /**
     * Method used to add a player to the lobby
     * @param gamer is the player
     */
    public void addPlayer(Player gamer){
        this.players.add(gamer);
    }

    /**
     * Getter method
     * @return the game type
     */
    public GameType getType(){
        return this.type;
    }

    /**
     * Getter method
     * @return the lobby size
     */
    private int getLobbySize(){
        return this.players.size();
    }

    /**
     * Method used to establish if a lobby is ready to start
     * @return true if the lobby size and the number of player are the same
     */
    public boolean isLobbyReady(){
        return this.getLobbySize() == this.numPlayers;
    }

    /**
     * Method used for starting a game from this lobby
     */
    public void startGame(){
        GameController gameController = new GameController(this.players,this.type);
        gameController.start();
    }

    /**
     * Method used to remove a player in case of an early error
     * @param player is the player to remove
     */
    public void removePlayer(Player player){
        this.players.remove(player);
    }

    /**
     * method used to check if a player is in the lobby
     * @param player is the searched player
     * @return true if the player is in this lobby
     */
    public boolean contains(Player player){
        return this.players.contains(player);
    }

}
