package it.polimi.ingsw.controller.server.game;

import it.polimi.ingsw.controller.networking.AssistantCardDeckFigures;
import it.polimi.ingsw.controller.networking.Player;
import it.polimi.ingsw.controller.networking.exceptions.*;
import it.polimi.ingsw.controller.server.game.exceptions.ModelErrorException;
import it.polimi.ingsw.controller.server.game.gameController.GameController;
import it.polimi.ingsw.controller.server.virtualView.View;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.gamer.Gamer;
import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.model.pawn.Student;
import it.polimi.ingsw.model.pawn.TowerColor;

import java.util.ArrayList;
import java.util.Random;

/**
 * This class is the setup phase of the game, where all the info to start a game are initialized and sent to the players
 */
public class GameSetup implements GamePhase{
    private final int numTowers;
    private final int numStudents;
    private final ArrayList<TowerColor> colors;
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
        this.colors = new ArrayList<>();
        this.colors.add(TowerColor.WHITE);
        this.colors.add(TowerColor.BLACK);
        if(game.getGamers().size() == 2){
            this.numStudents = 7;
            this.numTowers = 8;
        }else{
            this.numStudents = 9;
            this.numTowers = 6;
            this.colors.add(TowerColor.GREY);
        }
    }

    /**
     * This is the main method that handles the GameSetup
     */
    public void handle(){
        for(Player player : this.controller.getPlayers()){
            this.updateMotherNaturePlace(player);
            this.updateIslandStatus(player);
        }
        this.initIslands(this.game);
        for(Player player : this.controller.getPlayers()){
            try {
                TowerColor color = this.randomColorPicker();
                player.getGamer(this.game.getGamers()).setTowerColor(color);
                player.getGamer(this.game.getGamers()).initGamer(this.game.getBag().pullStudents(this.numStudents),this.numTowers);
            } catch (ModelErrorException e) {
                System.out.println("Error founded in model : shutting down this game");
                this.controller.shutdown();
            }
        }
        for (Player player : this.controller.getPlayers()) {
            this.updateTowerColor(player);
            this.updateDashboards(player);
        }
        for(Player player : this.controller.getPlayers()){
            AssistantCardDeckFigures figure = this.getChosenAssistantCardDeck(player);
            ArrayList<Player> players = new ArrayList<>(this.controller.getPlayers());
            players.remove(player);
            Gamer currentPlayer = null;
            try {
                currentPlayer = player.getGamer(this.game.getGamers());
            } catch (ModelErrorException e) {
                this.controller.shutdown();
            }
            for(Player player1 : players){
                this.updateChosenCardDeck(player1, currentPlayer, figure);
            }
            players.clear();
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
                this.view.updateMotherNaturePlace(this.game.getMotherNature().getPlace());
            }catch (MalformedMessageException | FlowErrorException | TimeHasEndedException e){
                this.view.updateMotherNaturePlace(this.game.getMotherNature().getPlace());
            }
        }catch (FlowErrorException | MalformedMessageException | TimeHasEndedException | ClientDisconnectedException e){
            this.controller.handlePlayerError(player);
        }
    }

    /**
     * This method is called by handle() and it sends to a player the information about of all islands
     * @param player is the player whose view will be adjourned
     */
    private void updateIslandStatus(Player player){
        this.view.setCurrentPlayer(player);
        try{
            try{
                this.view.updateIslandStatus(this.game.getIslands());
            }catch (MalformedMessageException | FlowErrorException | TimeHasEndedException e){
                this.view.updateIslandStatus(this.game.getIslands());
            }
        }catch (FlowErrorException | MalformedMessageException | TimeHasEndedException | ClientDisconnectedException e){
            this.controller.handlePlayerError(player);
        }
    }

    private void updateTowerColor(Player player) {
        this.view.setCurrentPlayer(player);
        try {
            try {
                this.view.sendTowerColor(player.getGamer(this.game.getGamers()).getTowerColor());
            } catch (MalformedMessageException | FlowErrorException | TimeHasEndedException e) {
                this.view.sendTowerColor(player.getGamer(this.game.getGamers()).getTowerColor());
            }
        } catch (MalformedMessageException | FlowErrorException | TimeHasEndedException | ClientDisconnectedException e) {
            this.controller.handlePlayerError(player);
        } catch (ModelErrorException e) {
            this.controller.shutdown();
        }
    }

    /**
     * This method is called by handle() and it sends to a player the information about of all dashboards
     * @param player is the player whose view will be adjourned
     */
    private void updateDashboards(Player player){
        this.view.setCurrentPlayer(player);
        try{
            try{
                this.view.updateDashboards(this.game.getGamers(), this.game);
            }catch (MalformedMessageException | FlowErrorException | TimeHasEndedException e){
                this.view.updateDashboards(this.game.getGamers(), this.game);
            }
        }catch (FlowErrorException | MalformedMessageException | TimeHasEndedException | ClientDisconnectedException e){
            this.controller.handlePlayerError(player);
        }
    }

    /**
     * This method is called by handle() and it asks a player which AssistantCardDeckFigure he wants to take
     * @param player is the player currently playing
     * @return the figure chosen by the player
     */
    private AssistantCardDeckFigures getChosenAssistantCardDeck(Player player){
        this.view.setCurrentPlayer(player);
        AssistantCardDeckFigures card = null;
        try{
            try{
                card = this.view.getChosenAssistantCardDeck(this.controller.getCardDesks());
            }catch (MalformedMessageException e){
                card = this.view.getChosenAssistantCardDeck(this.controller.getCardDesks());
            }
        }catch (MalformedMessageException | ClientDisconnectedException e){
            this.controller.handlePlayerError(player);
        }catch (TimeHasEndedException e) {
            card = this.randomFigurePicker();
            this.controller.getCardDesks().remove(card);
            return card;
        }
        this.controller.getCardDesks().remove(card);
        return card;
    }

    /**
     * This method is called by handle() and it sends to a player the AssistantCardDeckFigure chosen by the currentPlayer
     * @param player is the player whose view will be adjourned
     * @param currentPlayer is the current player
     * @param figure is the figure chosen by the currentPlayer
     */
    private void updateChosenCardDeck(Player player, Gamer currentPlayer, AssistantCardDeckFigures figure){
        this.view.setCurrentPlayer(player);
        try{
            try{
                this.view.sendChosenAssistantCardDeck(figure, currentPlayer.getToken(), currentPlayer);
            }catch (MalformedMessageException | FlowErrorException | TimeHasEndedException e){
                this.view.sendChosenAssistantCardDeck(figure, currentPlayer.getToken(), currentPlayer);
            }
        }catch (FlowErrorException | MalformedMessageException | TimeHasEndedException | ClientDisconnectedException e){
            this.controller.handlePlayerError(player);
        }
    }

    /**
     * This method is called by getChosenAssistantCardDeck() when the player doesn't reply in time. It chooses a random
     * figure for the cards of the player
     * @return a random AssistantCardDeckFigure
     */
    private AssistantCardDeckFigures randomFigurePicker() {
        Random random = new Random();
        int rand = random.nextInt(0, AssistantCardDeckFigures.values().length);
        return AssistantCardDeckFigures.values()[rand];
    }

    /**
     * This method is called by handle() and it picks a random TowerColor for a player
     * @return a random color
     */
    private TowerColor randomColorPicker() {
        Random random = new Random();
        int rand = random.nextInt(0, this.colors.size());
        TowerColor result = this.colors.get(rand);
        this.colors.remove(rand);
        return result;
    }

    /**
     * This method changes the phase to the next one
     * @return the next GamePhase
     */
    public GamePhase next(){
        return new PlanningPhase(this.game,this.controller);
    }
}
