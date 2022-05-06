package it.polimi.ingsw.controller.server.game.gameController;

import it.polimi.ingsw.controller.networking.Player;
import it.polimi.ingsw.controller.server.GameType;
import it.polimi.ingsw.controller.server.Server;
import it.polimi.ingsw.controller.server.game.GamePhase;
import it.polimi.ingsw.model.gamer.Gamer;

import java.util.ArrayList;

// TODO : gameController estende thread
//TODO :creare classe ExpertGameController
public class GameController extends Thread{
    private final Server server;
    private ArrayList<Gamer> gamers;
    private final Game game;
    private GamePhase gamePhase;

    public GameController(Server server, ArrayList<Player> players){
        this.server = server;
        createGamers(players);
        this.game = new Game(this.gamers);
    }

    private void createGamers(ArrayList<Player> players){
        this.gamers = new ArrayList<Gamer>();
        for(Player player : players){
            Gamer gamer = new Gamer(player.getGamer(), player.getUsername());
            this.gamers.add(gamer);
        }
    }

    @Override
    public void run() {

    }
}
