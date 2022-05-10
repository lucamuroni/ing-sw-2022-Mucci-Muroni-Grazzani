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

public class ActionPhase1 implements GamePhase{
    private final Game game;
    private final GameController controller;
    private Gamer currentPlayer;
    private final int numOfMovements;
    private final View view;
    private Player player = null;

        //TODO :ricodarsi di aggiornare il currentPlayer in gameCOntroller
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
    //mouve gli studenti dove ha voglia
    //calcolo professori
    @Override
    public void handle() {
        try {
            this.view.phaseChanghe("ActionPhase1");
        } catch () {}
        try {
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
        } catch (ModelErrorException e) {
            this.controller.shutdown();
            e.printStackTrace();
            return;
        }
    }

    @Override
    public GamePhase next() {
        return new MotherNaturePhase(this.game,this.controller);
    }

    private void moveStudentToLocation(Player player){
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
            this.controller.handlePlayerError(this.player);
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
            Island isl = this.game.getIslands().get(place);
            this.game.getCurrentPlayer().getDashboard().moveStudent(stud, isl);
        }
    }

    private void updateCurrentPlayer() throws ModelErrorException {
        this.currentPlayer = this.game.getCurrentPlayer();
        this.player = this.controller.getPlayer(this.currentPlayer);
        this.view.setCurrentPlayer(this.player);
    }

    private PawnColor randomColorPicker(){
        Random random = new Random();
        int rand = random.nextInt(0, PawnColor.values().length);
        return PawnColor.values()[rand];
    }

    private int randomPlacePicker() {
        Random random = new Random();
        int rand = random.nextInt(0, this.game.getIslands().size());
        return rand;
    }
}
