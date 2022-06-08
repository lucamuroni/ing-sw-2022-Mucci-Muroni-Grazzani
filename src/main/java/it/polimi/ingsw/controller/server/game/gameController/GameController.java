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
import it.polimi.ingsw.view.cli.AnsiColor;

import java.util.ArrayList;
import java.util.Arrays;

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
        this.cardDesks.addAll(Arrays.asList(AssistantCardDeckFigures.values()));
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
        System.out.print("\n"+AnsiColor.GREEN.toString()+"A game has been concluded"+AnsiColor.RESET.toString());
    }

    public View getView(){
        return this.view;
    }

    public ArrayList<Player> getPlayers(){
        return this.players;
    }

    public void shutdown(String s){
        //TODO : scrivere per chiamare la fase di vittoria ed inviarla
        System.out.println(AnsiColor.RED.toString()+s+AnsiColor.RESET.toString());
        for(Player player : this.players){
            this.view.setCurrentPlayer(player);
            this.view.haltOnError();
        }
        this.isOK = false;
    }

    public ArrayList<AssistantCardDeckFigures> getCardDesks(){
        return this.cardDesks;
    }

    public void handlePlayerError(Player player,String s){
        System.out.println(AnsiColor.RED.toString()+s+AnsiColor.RESET.toString());
        System.out.println("Involved gamer info : "+AnsiColor.RED.toString()+player.getUsername()+AnsiColor.RESET.toString()+", token : "+AnsiColor.RED.toString()+player.getToken()+AnsiColor.RESET.toString());
        player.getMessageHandler().shutDown();
        this.players.remove(player);
        this.shutdown("An Gamer reported an error");
    }

    public Player getPlayer(Gamer currentPlayer, ArrayList<Player> players) throws ModelErrorException {
        System.out.println("CurrentPlayer "+currentPlayer.getUsername()+"  "+currentPlayer.getToken());
        System.out.println("players size  "+players.size());
        for(Player player : players){
            System.out.println("player :"+player.getUsername()+ "    "+player.getToken());
            if(currentPlayer.getToken() == player.getToken() && currentPlayer.getUsername().equals(player.getUsername())){
                return player;
            }
        }
        throw new ModelErrorException();
    }

    public Player getPlayer(Gamer currentPlayer) throws ModelErrorException {
        return this.getPlayer(currentPlayer,this.players);
    }

    public void updatePlayersOrder(){
        ArrayList<Player> cp = new ArrayList<>(this.players);
        this.players.clear();
        for(Gamer gamer : this.game.getGamers()){
            try {
                this.players.add(this.getPlayer(gamer,cp));
            } catch (ModelErrorException e) {
                this.shutdown("Could not find player in model while updating their order");
            }
        }
    }
}
