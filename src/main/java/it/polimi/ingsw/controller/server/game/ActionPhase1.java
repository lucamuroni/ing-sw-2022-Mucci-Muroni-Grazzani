package it.polimi.ingsw.controller.server.game;

import it.polimi.ingsw.controller.networking.GameType;
import it.polimi.ingsw.controller.networking.Phase;
import it.polimi.ingsw.controller.networking.Player;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.server.game.exceptions.ModelErrorException;
import it.polimi.ingsw.controller.server.virtualView.View;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.game.ExpertGame;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.gamer.Gamer;
import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.model.pawn.Student;

import java.util.ArrayList;

import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.*;

/**
 * This class implements the second phase of the game, which is the ActionPhase1, where the current player moves 3/4 students
 * from his waitingRoom to an island or his hall
 */
public class ActionPhase1 implements GamePhase{

    private final Game game;
    private final GameController controller;
    private final int numOfMovements;
    private final View view;

    /**
     * Constructor of the class
     * @param game represents the current game
     * @param controller represents the controller linked with this game
     */
    public ActionPhase1(Game game, GameController controller){
        this.game = game;
        this.controller = controller;
        if(this.game.getGamers().size() == 2){
            this.numOfMovements = 3;
        }else {
            this.numOfMovements = 4;
        }
        this.view = this.controller.getView();
    }

    /**
     * This is the main method that handles ActionPhase1
     */
    @Override
    public void handle() {
        try {
            this.view.setCurrentPlayer(this.controller.getPlayer(this.game.getCurrentPlayer()));
        } catch (ModelErrorException e) {
            this.controller.shutdown("Error founded in model : shutting down this game");
        }
        try {
            try{
                this.view.sendContext(CONTEXT_PHASE.getFragment());
                this.view.sendNewPhase(Phase.ACTION_PHASE_1);
            }catch (MalformedMessageException | FlowErrorException e){
                this.view.sendContext(CONTEXT_PHASE.getFragment());
                this.view.sendNewPhase(Phase.ACTION_PHASE_1);
            }
        }catch (MalformedMessageException | FlowErrorException | ClientDisconnectedException e) {
            try {
                this.controller.handlePlayerError(this.controller.getPlayer(this.game.getCurrentPlayer()),"Error while sending ACTION PHASE 1");
            } catch (ModelErrorException i) {
                this.controller.shutdown("Error founded in model : shutting down this game");
            }
        }
        ArrayList<Player> players = new ArrayList<>(this.controller.getPlayers());
        try {
            players.remove(this.controller.getPlayer(this.game.getCurrentPlayer()));
        } catch (ModelErrorException e) {
            this.controller.shutdown("Error founded in model : shutting down this game");
        }
        for (Player player : players) {
            this.view.setCurrentPlayer(player);
            try {
                try {
                    this.view.sendContext(CONTEXT_USERNAME.getFragment());
                    this.view.sendActiveUsername(this.controller.getPlayer(this.game.getCurrentPlayer()));
                } catch (MalformedMessageException | FlowErrorException e) {
                    this.view.sendContext(CONTEXT_USERNAME.getFragment());
                    this.view.sendActiveUsername(this.controller.getPlayer(this.game.getCurrentPlayer()));
                }
            } catch (MalformedMessageException | FlowErrorException | ClientDisconnectedException e) {
                this.controller.handlePlayerError(player,"Error while uploading current player to other gamers");
            } catch (ModelErrorException e) {
                this.controller.shutdown("Error founded in model : shutting down this game");
            }
        }
        try {
            for (int cont = 0; cont < this.numOfMovements; cont++) {
                int place = this.moveStudentToLocation(this.controller.getPlayer(this.game.getCurrentPlayer()));
                this.view.setCurrentPlayer(this.controller.getPlayer(this.game.getCurrentPlayer()));
                try {
                    try {
                        for (Gamer gamer : this.game.getGamers()) {
                            this.view.updateDashboards(gamer, this.game);
                        }
                        Island isl = null;
                        if (place>0) {
                            for (Island island : this.game.getIslands()) {
                                if (island.getId() == place) {
                                    isl = island;
                                    break;
                                }
                            }
                            this.view.updateIslandStatus(isl);
                        }
                    } catch (MalformedMessageException | FlowErrorException e) {
                        for (Gamer gamer : this.game.getGamers()) {
                            this.view.updateDashboards(gamer, this.game);
                        }
                        Island isl = null;
                        if (place>0){
                            for (Island island : this.game.getIslands()) {
                                if (island.getId() == place) {
                                    isl = island;
                                    break;
                                }
                            }
                            this.view.updateIslandStatus(isl);
                        }
                    }
                } catch (MalformedMessageException | ClientDisconnectedException  | FlowErrorException e){
                    this.controller.handlePlayerError(this.controller.getPlayer(this.game.getCurrentPlayer()),"Error while uploading dashboards");
                }
                for (Player pl : players) {
                    this.view.setCurrentPlayer(pl);
                    try {
                        try {
                            this.sendInfo(place);
                        } catch (MalformedMessageException | FlowErrorException e) {
                            this.sendInfo(place);
                        }
                    } catch (MalformedMessageException | ClientDisconnectedException | FlowErrorException e){
                        this.controller.handlePlayerError(pl,"Error while updating islands and dashboards");
                    }
                }
            }
        } catch (ModelErrorException e) {
            this.controller.shutdown("Error founded in model : shutting down this game");
            e.printStackTrace();
        }
    }

    /**
     * This method handles the movement of student chosen by a player, and it is called in handle()
     * @param player represents the currentPlayer that is playing
     */
    private int moveStudentToLocation(Player player) {
        this.view.setCurrentPlayer(player);
        int place = 0;
        PawnColor color = null;
        try{
            try{
                color = this.view.getMovedStudentColor();
                place = this.view.getMovedStudentLocation();
            }catch (MalformedMessageException e){
                color = this.view.getMovedStudentColor();
                place = this.view.getMovedStudentLocation();
            }
        }catch (MalformedMessageException | ClientDisconnectedException e){
            this.controller.handlePlayerError(player,"Error while getting the location of  moved the student");
        }
        modelHandler(place, color, player);
        return place;
    }

    /**
     * This method modifies the model moving the student to the correct place
     * @param place is the location where the student must be moved to
     * @param color is the color of the student
     */
    private void modelHandler(int place, PawnColor color, Player player){
        Student stud = this.game.getCurrentPlayer().getDashboard().getWaitingRoom().stream().filter(x -> x.getColor().equals(color)).findFirst().get();
        if (place == 0) {
            this.game.getCurrentPlayer().getDashboard().moveStudent(stud);
            try {
                this.game.changeProfessorOwner(stud.getColor());
            }catch (Exception e) {
                this.controller.shutdown("Error founded in model : shutting down this game");
            }
            if(controller.getGameType()==GameType.EXPERT){
                ExpertGame expertGame = (ExpertGame) game;
                int coins = expertGame.getCurrentPlayer().getDashboard().getCoins();
                this.sendCoins(player,coins);
            }
        }
        else {
            Island isl = null;
            for (Island island : this.game.getIslands()) {
                if (island.getId() == place) {
                    isl = island;
                    break;
                }
            }
            this.game.getCurrentPlayer().getDashboard().moveStudent(stud, isl);
        }
    }

    /**
     * Method that is called by handle() only if the game is an expert one
     * It handles the sending of the coins possessed by a player
     * @param player is the currenPlayer that is playing
     * @param coins is the number of coins that the player owns
     */
    private void sendCoins(Player player,int coins) {
        try {
            try {
                this.view.sendCoins(coins);
            } catch (MalformedMessageException e) {
                this.view.sendCoins(coins);
            }
        } catch (MalformedMessageException | FlowErrorException | ClientDisconnectedException e) {
            this.controller.handlePlayerError(player, "Error while sending coins");
        }
    }

    /**
     * Method called by handle() to send all the infos about the action done by the currentPlayer to all others players
     * @param place represents the location where the student was moved on (0: hall, 1-12: an island)
     * @throws FlowErrorException if there is an error while synchronizing
     * @throws MalformedMessageException if the message arrived is incorrect
     * @throws ClientDisconnectedException if the player disconnected from the game
     */
    private void sendInfo(int place) throws FlowErrorException, MalformedMessageException, ClientDisconnectedException {
        if (place > 0) {
            this.view.sendContext(CONTEXT_ISLAND.getFragment());
            Island isl = null;
            for (Island island : this.game.getIslands()) {
                if (island.getId() == place) {
                    isl = island;
                    break;
                }
            }
            this.view.updateIslandStatus(isl);
        }
        for (Gamer gamer : this.game.getGamers()) {
            this.view.sendContext(CONTEXT_DASHBOARD.getFragment());
            this.view.updateDashboards(gamer, this.game);
        }
    }

    /**
     * This method changes the phase to the next one
     * @return the next GamePhase
     */
    @Override
    public GamePhase next() {
        if(controller.getGameType()== GameType.EXPERT){
            ExpertGame expertGame = (ExpertGame) this.game;
            if(!expertGame.isCharacterCardBeenPlayed()){
                return new CharacterCardPhase((ExpertGame) this.game,this.controller,new MotherNaturePhase(this.game, this.controller));
            }
        }
        return new MotherNaturePhase(this.game, this.controller);
    }
}
