package it.polimi.ingsw.controller.server.game;

import it.polimi.ingsw.controller.networking.Player;
import it.polimi.ingsw.controller.server.GameType;
import it.polimi.ingsw.controller.server.Server;
import it.polimi.ingsw.model.gamer.Gamer;

import java.util.ArrayList;

// TODO : gameController estende thread
//TODO :trovare via alternativa per la classe game
public class GameController {
    private final Server server;
    private ArrayList<Gamer> gamers;
    private final GameType type;
    private final Game game;
    private final ExpertGame expertGame;

    public GameController(Server server, GameType type, ArrayList<Player> players){
        this.server = server;
        this.type = type;
        if(this.type==GameType.NORMAL){
            this.expertGame = null;
            this.game = new Game();
        }else{
            this.game = null;
            this.expertGame = new ExpertGame();
        }
        createGamers(players);
    }

    private void createGamers(ArrayList<Player> players){
        this.gamers = new ArrayList<Gamer>();
        for(Player player : players){
            Gamer gamer = new Gamer(player.getGamer(), player.getUsername());
            this.gamers.add(gamer);
        }
    }
}
