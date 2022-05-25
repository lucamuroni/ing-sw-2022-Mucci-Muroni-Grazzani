package it.polimi.ingsw.controller.server.game;

import it.polimi.ingsw.controller.networking.Phase;
import it.polimi.ingsw.controller.networking.Player;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.controller.server.game.exceptions.GenericErrorException;
import it.polimi.ingsw.controller.server.game.exceptions.ModelErrorException;
import it.polimi.ingsw.controller.server.game.gameController.GameController;
import it.polimi.ingsw.controller.server.virtualView.View;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.model.pawn.Student;

import java.util.ArrayList;
import java.util.Random;

import static it.polimi.ingsw.controller.networking.MessageFragment.CONTEXT_ACTION1;
import static it.polimi.ingsw.controller.networking.MessageFragment.CONTEXT_PLANNING;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.CONTEXT_ACTION1;

/**
 * This class implements the second phase of the game, which is the ActionPhase1, where the current player moves 3/4 students
 * from his waitingRoom to an island or his hall
 */
public class ActionPhase1 implements GamePhase{
    //TODO: Bisogna risolvere questo problema: se il game è in modalità esperta, non si possono gestire le monete
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
     *This is the main method that handles the ActionPhase1
     */
    @Override
    public void handle() {
        this.game.setTurnNumber();
        try {
            this.view.setCurrentPlayer(this.controller.getPlayer(this.game.getCurrentPlayer()));
        } catch (ModelErrorException e) {
            this.controller.shutdown();
        }
        try {
            try{
                this.view.sendNewPhase(Phase.ACTION_PHASE_1);
            }catch (MalformedMessageException | FlowErrorException | TimeHasEndedException e){
                this.view.sendNewPhase(Phase.ACTION_PHASE_1);
            }
        }catch (MalformedMessageException | FlowErrorException | TimeHasEndedException | ClientDisconnectedException e) {
            try {
                this.controller.handlePlayerError(this.controller.getPlayer(this.game.getCurrentPlayer()));
            } catch (ModelErrorException i) {
                this.controller.shutdown();
            }
        }
        try {
            for (int cont = 0; cont < this.numOfMovements; cont++) {
                int place = this.moveStudentToLocation(this.controller.getPlayer(this.game.getCurrentPlayer()));
                this.view.setCurrentPlayer(this.controller.getPlayer(this.game.getCurrentPlayer()));
                try {
                    try {
                        this.view.updateDashboards(this.game.getCurrentPlayer(), this.game);
                    } catch (MalformedMessageException | TimeHasEndedException | FlowErrorException e) {
                        this.view.updateDashboards(this.game.getCurrentPlayer(), this.game);
                    }
                } catch (MalformedMessageException | ClientDisconnectedException | TimeHasEndedException | FlowErrorException e){
                    this.controller.handlePlayerError(this.controller.getPlayer(this.game.getCurrentPlayer()));
                }
                ArrayList<Player> players = new ArrayList<>(this.controller.getPlayers());
                players.remove(this.controller.getPlayer(this.game.getCurrentPlayer()));
                for (Player pl : players) {
                    this.view.setCurrentPlayer(pl);
                    try {
                        try {
                            this.view.sendContext(CONTEXT_ACTION1.getFragment());
                            if (place > 0) {
                                this.view.updateIslandStatus(this.game.getIslands().get(place-1));
                            }
                            this.view.updateDashboards(this.game.getCurrentPlayer(), this.game);
                        } catch (MalformedMessageException | TimeHasEndedException | FlowErrorException e) {
                            this.view.sendContext(CONTEXT_ACTION1.getFragment());
                            if (place > 0) {
                                this.view.updateIslandStatus(this.game.getIslands().get(place-1));
                            }
                            this.view.updateDashboards(this.game.getCurrentPlayer(), this.game);
                        }
                    } catch (MalformedMessageException | ClientDisconnectedException | TimeHasEndedException | FlowErrorException e){
                        this.controller.handlePlayerError(pl);
                    }
                }
            }
        } catch (ModelErrorException e) {
            this.controller.shutdown();
            e.printStackTrace();
        }
    }

    /**
     * This method handles the movement of the student chosen by the player, and it is called in handle()
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
            this.controller.handlePlayerError(player);
        }catch (TimeHasEndedException e){
            color = this.randomColorPicker();
            place = this.randomPlacePicker();
            modelHandler(place,color);
        }
        modelHandler(place,color);
        return place;
    }

    /**
     * This method modifies the model moving the student to the correct place
     * @param place is the location where the student must be moved to
     * @param color is the color of the student
     */
    private void modelHandler(int place, PawnColor color){
        Student stud = this.game.getCurrentPlayer().getDashboard().getWaitingRoom().stream().filter(x -> x.getColor().equals(color)).findFirst().get();
        if (place == 0) {
            this.game.getCurrentPlayer().getDashboard().moveStudent(stud);
            try {
                this.game.changeProfessorOwner(stud.getColor());
            }catch (Exception e) {
                this.controller.shutdown();
            }
        }
        else {
            Island isl = this.game.getIslands().get(place-1);
            this.game.getCurrentPlayer().getDashboard().moveStudent(stud, isl);
        }
    }

    /**
     * This method is called by moveStudentToLocation() when the player doesn't reply in time. It chooses a random color
     * for the student moved
     * @return a random color
     */
    private PawnColor randomColorPicker(){
        Random random = new Random();
        int rand = random.nextInt(0, PawnColor.values().length);
        return PawnColor.values()[rand];
    }

    /**
     * This method is called by moveStudentToLocation() when the player doesn't reply in time. It chooses a random place
     * for the student moved
     * @return a random place (it is a number between 0 and 12: 0 = hall, 1-12 = island)
     */
    private int randomPlacePicker() {
        Random random = new Random();
        return random.nextInt(0, this.game.getIslands().size());
    }

    /**
     * This method changes the phase to the next one
     * @return the next GamePhase
     */
    @Override
    public GamePhase next() {
        return new MotherNaturePhase(this.game, this.controller);
    }
}
