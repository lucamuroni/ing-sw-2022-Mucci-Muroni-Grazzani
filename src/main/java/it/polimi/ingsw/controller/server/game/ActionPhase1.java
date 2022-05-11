package it.polimi.ingsw.controller.server.game;

import it.polimi.ingsw.controller.networking.Player;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.controller.server.game.exceptions.GenericErrorException;
import it.polimi.ingsw.controller.server.game.exceptions.ModelErrorException;
import it.polimi.ingsw.controller.server.game.gameController.GameController;
import it.polimi.ingsw.controller.server.virtualView.View;
import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.gamer.Gamer;
import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.model.pawn.Student;

import java.util.ArrayList;
import java.util.Random;

/**
 * This class implements the second phase of the game, which is the ActionPhase1, where the current player moves 3/4 students
 * from his waitingRoom to an island or his hall
 */
public class ActionPhase1 implements GamePhase{
    private final Game game;
    private final GameController controller;
    private Gamer currentPlayer;
    private final int numOfMovements;
    private final View view;
    //private Player player = null;

    //TODO :ricodarsi di aggiornare il currentPlayer in gameCOntroller

    /**
     * Constructor of the class
     * @param game represents the current game
     * @param controller represents the controller linked with this game
     */
    public ActionPhase1(Game game, GameController controller){
        this.game = game;
        this.controller = controller;
        this.currentPlayer = this.game.getCurrentPlayer();
        if(this.game.getGamers().size() == 2){
            this.numOfMovements = 3;
        }else {
            this.numOfMovements = 4;
        }
        this.view = this.controller.getView();
    }

    /**
     *This is the main method that handles this phase
     */
    @Override
    public void handle() {
        try {
            this.view.phaseChanghe("ActionPhase1");
        } catch () {}
        try {
            //this.updateCurrentPlayer();
            for (int cont = 0; cont < this.numOfMovements; cont++) {
                this.moveStudentToLocation(this.controller.getPlayer(this.game.getCurrentPlayer()));
                ArrayList<Player> players = new ArrayList<>(this.controller.getPlayers());
                players.remove(this.controller.getPlayer(this.game.getCurrentPlayer()));
                for (Player pl : players) {
                    this.view.setCurrentPlayer(pl);
                    try {
                        try {
                            this.view.updateDashboards(this.game.getGamers());
                        } catch (MalformedMessageException | TimeHasEndedException | FlowErrorException e) {
                            this.view.updateDashboards(this.game.getGamers());
                        }
                    } catch (MalformedMessageException | ClientDisconnectedException | TimeHasEndedException | FlowErrorException e){
                        this.controller.handlePlayerError(pl);
                    }
                }
            }
        } catch (ModelErrorException e) {
            this.controller.shutdown();
            e.printStackTrace();
            return;
        } catch (GenericErrorException e) {
            e.printStackTrace();
            return;
        }
    }

    /**
     * This method changes the phase to the next one
     * @return the next GamePhase
     */
    @Override
    public GamePhase next() {
        return new MotherNaturePhase(this.game,this.controller);
    }

    /**
     * This method handles the movement of the student choose by the player and is called in handle()
     * @param player represents the currentPlayer that is playing
     * @throws GenericErrorException when the message from the client is malformed twice or the player disconnects from the game
     */
    private void moveStudentToLocation(Player player) throws GenericErrorException {
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
            }catch (TimeHasEndedException e){
                color = this.randomColorPicker();
                place = this.randomPlacePicker();
            }
        }catch (MalformedMessageException | ClientDisconnectedException e){
            this.controller.handlePlayerError(player);
            throw new GenericErrorException();
        }catch (TimeHasEndedException e){
            color = this.randomColorPicker();
            place = this.randomPlacePicker();
        }
        PawnColor finalColor = color;
        Student stud = this.game.getCurrentPlayer().getDashboard().getWaitingRoom().stream().filter(x -> x.getColor().equals(finalColor)).findFirst().get();
        if (place == 0) {
            this.game.getCurrentPlayer().getDashboard().moveStudent(stud);
            try {
                this.game.changeProfessorOwner(stud.getColor());
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            Island isl = this.game.getIslands().get(place-1);
            this.game.getCurrentPlayer().getDashboard().moveStudent(stud, isl);
        }
    }

    //TODO: metodo inutile
    /**
     * This method is called in handle() to adjourn the currentPlayer
     * @throws ModelErrorException
     */
    private void updateCurrentPlayer() throws ModelErrorException {
        this.currentPlayer = this.game.getCurrentPlayer();
        this.player = this.controller.getPlayer(this.currentPlayer);
        this.view.setCurrentPlayer(this.player);
    }

    /**
     * Method called by moveStudentToLocation() when the player doesn't reply in time and that choose a random color
     * for the student moved
     * @return a random color
     */
    private PawnColor randomColorPicker(){
        Random random = new Random();
        int rand = random.nextInt(0, PawnColor.values().length);
        return PawnColor.values()[rand];
    }

    /**
     * Method called by moveStudentToLocation() when the player doesn't reply in time and that choose a random location
     * for the student moved
     * @return a random place
     */
    private int randomPlacePicker() {
        Random random = new Random();
        int rand = random.nextInt(0, this.game.getIslands().size());
        return rand;
    }
}
