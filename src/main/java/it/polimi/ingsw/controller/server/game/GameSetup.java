package it.polimi.ingsw.controller.server.game;

import it.polimi.ingsw.controller.networking.GameType;
import it.polimi.ingsw.controller.networking.Player;
import it.polimi.ingsw.controller.networking.exceptions.*;
import it.polimi.ingsw.controller.server.game.exceptions.ModelErrorException;
import it.polimi.ingsw.controller.server.virtualView.View;
import it.polimi.ingsw.model.expert.CharacterCard;
import it.polimi.ingsw.model.game.ExpertGame;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.gamer.ExpertGamer;
import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.model.pawn.Student;

import java.util.ArrayList;
import java.util.Random;

/**
 * This class is the setup phase of the game, where all the info to start a game are initialized and sent to the players
 */
public class GameSetup implements GamePhase{
    private final int numTowers;
    private final int numStudents;
    private final Game game;
    private final GameController controller;
    private final View view;

    /**
     * Constructor of the class
     * @param controller represents the controller linked with this game
     * @param game represents the current game
     */
    public GameSetup(GameController controller, Game game){
        this.game = game;
        this.controller = controller;
        this.view = this.controller.getView();
        if(this.game.getGamers().size() == 2){
            this.numStudents = 7;
            this.numTowers = 8;
        }else{
            this.numStudents = 9;
            this.numTowers = 6;
        }
    }

    /**
     * This is the main method that handles the GameSetup
     */
    public void handle(){
        this.updateUsernames();
        this.initIslands(this.game);
        for(Player player : this.controller.getPlayers()){
            this.updateMotherNaturePlace(player);
            this.updateIslandStatus(player);
        }
        for(Player player : this.controller.getPlayers()){
            try {
                player.getGamer(this.game.getGamers()).initGamer(this.game.getBag().pullStudents(this.numStudents),this.numTowers);
            } catch (ModelErrorException e) {
                this.controller.shutdown("Error founded in model : shutting down this game");
            }
        }
        ArrayList<Player> pl = new ArrayList<>(this.controller.getPlayers());
        for (Player player : this.controller.getPlayers()) {
            this.view.setCurrentPlayer(player);
            if(this.controller.getGameType()== GameType.EXPERT){
                this.sendCharacterCards(player);
                this.sendCoins(player);
            }
            for (Player player1 : pl) {
                this.updateTowerColor(player1,player);
                this.updateDashboards(player1,player);
            }
        }
    }

    private void sendCharacterCards(Player player) {
        for (CharacterCard card : ((ExpertGame) this.game).getGameCards()) {
            System.out.println(card.getName());
            try {
                try {
                    this.view.sendCharacterCard(card);
                } catch (MalformedMessageException e) {
                    this.view.sendCharacterCard(card);
                }
            } catch (MalformedMessageException | FlowErrorException | ClientDisconnectedException e) {
                this.controller.handlePlayerError(player, "Error while updating  expert cards");
            }
        }
    }

    private void sendCoins(Player player) {
        ExpertGamer gamer = (ExpertGamer) this.game.getCurrentPlayer();
        try {
            try {
                this.view.sendCoins(gamer.getDashboard().getCoins());
            } catch (MalformedMessageException e) {
                this.view.sendCoins(gamer.getDashboard().getCoins());
            }
        } catch (MalformedMessageException | FlowErrorException | ClientDisconnectedException e) {
            this.controller.handlePlayerError(player, "Error while sending coins");
        }
    }

    /**
     * This method is called by handle() and it initializes all the islands
     * @param game represents the current game
     */
    private void initIslands(Game game){
        ArrayList<Student> students = new ArrayList<>();
        for(PawnColor color : PawnColor.values()){
            for(int i=0 ; i<2 ;i++){
                students.add(new Student(color));
            }
        }
        Random random = new Random();
        ArrayList<Student> copy = new ArrayList<>(students);
        students.clear();
        while (copy.size()>1){
            int i = random.nextInt(copy.size());
            students.add(copy.get(i));
            copy.remove(copy.get(i));
        }
        students.add(copy.get(0));
        game.initIsland(students);
    }

    /**
     * This method is called by handle() and it sends to a player the location of MotherNature
     * @param player is the player whose view will be adjourned
     */
    private void updateMotherNaturePlace(Player player) {
        this.view.setCurrentPlayer(player);
        try{
            try{
                //this.view.sendContext(CONTEXT_MOTHER.getFragment());
                this.view.updateMotherNaturePlace(this.game.getMotherNature().getPlace());
            }catch (MalformedMessageException | FlowErrorException e){
                //this.view.sendContext(CONTEXT_MOTHER.getFragment());
                this.view.updateMotherNaturePlace(this.game.getMotherNature().getPlace());
            }
        }catch (FlowErrorException | MalformedMessageException | ClientDisconnectedException e){
            e.printStackTrace();
            this.controller.handlePlayerError(player,"Error while updating Mother Nature place");
        }
    }

    /**
     * This method is called by handle() and it sends to a player the information about of all islands
     * @param player is the player whose view will be adjourned
     */
    private void updateIslandStatus(Player player){
        this.view.setCurrentPlayer(player);
        for (int i = 0; i<this.game.getIslands().size(); i++) {
            try{
                try{
                    //this.view.sendContext(CONTEXT_ISLAND.getFragment());
                    this.view.updateIslandStatus(this.game.getIslands().get(i));
                }catch (MalformedMessageException | FlowErrorException  e){
                    //this.view.sendContext(CONTEXT_ISLAND.getFragment());
                    this.view.updateIslandStatus(this.game.getIslands().get(i));
                }
            }catch (FlowErrorException | MalformedMessageException  | ClientDisconnectedException e){
                e.printStackTrace();
                this.controller.handlePlayerError(player,"Error while syncing islands");
            }
        }
    }

    private void updateTowerColor(Player player,Player currentPlayer) {
        try {
            try {
                //this.view.sendContext(CONTEXT_COLOR.getFragment());
                this.view.sendTowerColor(player.getGamer(this.game.getGamers()));
            } catch (MalformedMessageException | FlowErrorException e) {
                //this.view.sendContext(CONTEXT_COLOR.getFragment());
                this.view.sendTowerColor(player.getGamer(this.game.getGamers()));
            }
        } catch (MalformedMessageException | FlowErrorException  | ClientDisconnectedException e) {
            this.controller.handlePlayerError(currentPlayer,"Error while updating color");
        } catch (ModelErrorException e) {
            this.controller.shutdown("Error founded in model : shutting down this game");
        }
    }

    /**
     * This method is called by handle() and it sends to a player the information about of all dashboards
     * @param player is the player whose view will be adjourned
     */
    private void updateDashboards(Player player,Player currentPlayer){
        try{
            try{
                //this.view.sendContext(CONTEXT_DASHBOARD.getFragment());
                this.view.updateDashboards(player.getGamer(this.game.getGamers()), this.game);
            }catch (MalformedMessageException | FlowErrorException e){
                //this.view.sendContext(CONTEXT_DASHBOARD.getFragment());
                this.view.updateDashboards(player.getGamer(this.game.getGamers()), this.game);
            }
        }catch (FlowErrorException | MalformedMessageException | ClientDisconnectedException | ModelErrorException e){
            e.printStackTrace();
            this.controller.handlePlayerError(currentPlayer,"Error while updating dashboard");
        }
    }

    private void updateUsernames(){
        ArrayList<Player> players = new ArrayList<>(this.controller.getPlayers());
        for (Player player1 : players){
            this.view.setCurrentPlayer(player1);
            for(Player player2 : players){
                if(!player1.equals(player2)){
                    try {
                        try {
                            this.view.sendUsername(player2);
                        } catch (MalformedMessageException | FlowErrorException e) {
                            this.view.sendUsername(player2);
                        }
                    } catch (MalformedMessageException | FlowErrorException | ClientDisconnectedException e) {
                        e.printStackTrace();
                        this.controller.handlePlayerError(player1,"Error while uploading usernames");
                    }
                }
            }
        }
    }

    /**
     * This method changes the phase to the next one
     * @return the next GamePhase
     */
    public GamePhase next(){
        return new DeckPhase(this.game,this.controller);
    }
}
