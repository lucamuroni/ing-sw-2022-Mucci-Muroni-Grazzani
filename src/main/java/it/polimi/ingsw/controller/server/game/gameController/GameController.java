package it.polimi.ingsw.controller.server.game.gameController;

import it.polimi.ingsw.controller.networking.Player;
import it.polimi.ingsw.controller.server.game.GameSetup;
import it.polimi.ingsw.controller.server.game.exceptions.ModelErrorException;
import it.polimi.ingsw.controller.server.Server;
import it.polimi.ingsw.controller.networking.AssistantCardDeckFigures;
import it.polimi.ingsw.controller.server.game.GamePhase;
import it.polimi.ingsw.controller.server.virtualView.View;
import it.polimi.ingsw.controller.server.virtualView.VirtualViewHandler;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.gamer.Gamer;
import it.polimi.ingsw.model.pawn.TowerColor;

import java.util.ArrayList;

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
        ArrayList<TowerColor> colors = new ArrayList<TowerColor>();
        for(TowerColor color : TowerColor.values()){
            colors.add(color);
        }
        ArrayList<Gamer> gamers = new ArrayList<Gamer>();
        for(Player player : players){
            Gamer gamer = new Gamer(player.getToken(), player.getUsername(), colors.get(0));
            gamers.add(gamer);
            colors.remove(0);
        }
        return gamers;
    }

    @Override
    public void run() {
        this.gamePhase = new GameSetup(this,this.game);
        this.gamePhase.handle();
        while (this.isOK){
            this.gamePhase = this.gamePhase.next();
            this.gamePhase.handle();
        }
    }

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
        this.players.remove(player);
        this.shutdown();
    }

    public Player getPlayer(Gamer currentPlayer) throws ModelErrorException {
        for(Player player : this.players){
            if(currentPlayer.getToken() == player.getToken() && currentPlayer.getUsername().equals(player.getUsername())){
                return player;
            }
        }
        throw new ModelErrorException();
    }

    public void updatePlayersOrder(){
        ArrayList<Player> cp = new ArrayList<>(this.players);
        this.players.clear();
        for(Gamer gamer : this.game.getGamers()){
            try {
                this.players.add(this.getPlayer(gamer));
            } catch (ModelErrorException e) {
                this.shutdown();
            }
        }
    }
}
