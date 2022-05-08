package it.polimi.ingsw.controller.server.game.gameController;

import it.polimi.ingsw.controller.networking.Player;
import it.polimi.ingsw.controller.server.GameType;
import it.polimi.ingsw.controller.server.Server;
import it.polimi.ingsw.controller.server.game.AssistantCardDeckFigures;
import it.polimi.ingsw.controller.server.game.GamePhase;
import it.polimi.ingsw.controller.server.virtualView.View;
import it.polimi.ingsw.controller.server.virtualView.VirtualViewHandler;
import it.polimi.ingsw.model.AssistantCardDeck;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.gamer.Gamer;

import java.util.ArrayList;

//TODO :creare classe ExpertGameController
public class GameController extends Thread{
    private final Server server;
    private ArrayList<Gamer> gamers;
    private ArrayList<Player> players;
    private final Game game;
    private GamePhase gamePhase;
    private View view;
    private boolean isOK;
    private ArrayList<AssistantCardDeckFigures> cardDesks;

    public GameController(Server server, ArrayList<Player> players){
        this.server = server;
        this.players = new ArrayList<>(players);
        createGamers(players);
        this.game = new Game(this.gamers);
        this.view = new VirtualViewHandler();
        this.isOK = true;
        this.cardDesks = new ArrayList<AssistantCardDeckFigures>();
        for(AssistantCardDeckFigures figure : AssistantCardDeckFigures.values()){
            this.cardDesks.add(figure);
        }
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
    //TODO : fare una funzione che dai gamers del model si ordina l'array di players

    public View getView(){
        return this.view;
    }

    public ArrayList<Player> getPlayers(){
        return this.players;
    }

    public void shutdown(){
        for(Player player : this.players){
            this.view.setCurrentPlayer(player);
            this.view.haltOnError();
        }
        this.isOK = false;
    }

    public ArrayList<AssistantCardDeckFigures> getCardDesks(){
        return this.cardDesks;
    }

    public void handlePlayerError(Player player){
        this.view.setCurrentPlayer(player);
        this.view.haltOnError();
        player.getMessageHandler().shutDown();
        
    }
}
