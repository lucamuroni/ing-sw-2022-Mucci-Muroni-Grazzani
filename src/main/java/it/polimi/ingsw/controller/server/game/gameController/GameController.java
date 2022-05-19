package it.polimi.ingsw.controller.server.game.gameController;

import it.polimi.ingsw.controller.networking.Player;
import it.polimi.ingsw.controller.server.game.exceptions.ModelErrorException;
import it.polimi.ingsw.controller.server.Server;
import it.polimi.ingsw.controller.networking.AssistantCardDeckFigures;
import it.polimi.ingsw.controller.server.game.GamePhase;
import it.polimi.ingsw.controller.server.virtualView.View;
import it.polimi.ingsw.controller.server.virtualView.VirtualViewHandler;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.gamer.Gamer;

import java.util.ArrayList;

//TODO :creare classe ExpertGameController
public class GameController extends Thread{
    private final Server server;
    private ArrayList<Player> players;
    private final Game game;
    private GamePhase gamePhase;
    private View view;
    private boolean isOK;
    private ArrayList<AssistantCardDeckFigures> cardDesks;

    public GameController(Server server, ArrayList<Player> players){
        this.server = server;
        this.players = new ArrayList<>(players);
        this.game = new Game(createGamers(players));
        this.view = new VirtualViewHandler();
        this.isOK = true;
        this.cardDesks = new ArrayList<AssistantCardDeckFigures>();
        for(AssistantCardDeckFigures figure : AssistantCardDeckFigures.values()){
            this.cardDesks.add(figure);
        }
    }

    private ArrayList<Gamer> createGamers(ArrayList<Player> players){
        ArrayList<Gamer> gamers = new ArrayList<Gamer>();
        for(Player player : players){
            Gamer gamer = new Gamer(player.getToken(), player.getUsername());
            gamers.add(gamer);
        }
        return gamers;
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
        System.out.println("Error revealed on server side : shutting down game");
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
        System.out.println("Error revealed on client side");
        System.out.println("Gamer info : "+player.getUsername()+", token : "+player.getToken());
        this.view.setCurrentPlayer(player);
        this.view.haltOnError();
        player.getMessageHandler().shutDown();
        try {
            player.getGamer(this.game.getGamers()).setActivity(false);
        } catch (ModelErrorException e) {
            System.out.println("Could not find gamer in model");
        }
        this.players.remove(player);
    }

    public Player getPlayer(Gamer currentPlayer) throws ModelErrorException {
        for(Player player : this.players){
            ArrayList<Gamer> gamers = new ArrayList<Gamer>();
            gamers.add(currentPlayer);
            try{
                player.getGamer(gamers);
                return player;
            }catch(ModelErrorException e){}
        }
        throw new ModelErrorException();
    }
}
