package it.polimi.ingsw.controller.server.game;

import it.polimi.ingsw.controller.networking.Player;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.controller.server.game.exceptions.ModelErrorException;
import it.polimi.ingsw.controller.server.game.gameController.GameController;
import it.polimi.ingsw.controller.server.virtualView.View;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.gamer.Gamer;
import it.polimi.ingsw.model.pawn.PawnColor;

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

    }

    @Override
    public GamePhase next() {
        return new MotherNaturePhase(this.game,this.controller);
    }

    private void moveStudentToLocation(){
        Island place = null;
        PawnColor color;
        try{
            try{
                color = this.view.getMovedStudentColor();
                place = this.view.getMovedStudentLocation(this.game.getIslands());
            }catch (MalformedMessageException e){
                color = this.view.getMovedStudentColor();
            }catch (TimeHasEndedException e){
                color = this.randomColorPicker();
            }
        }catch (MalformedMessageException | ClientDisconnectedException e){
            this.controller.handlePlayerError(this.player);
        }catch (TimeHasEndedException e){
            color = this.randomColorPicker();
        }
    }

    private void updateCurrentPlayer() throws ModelErrorException {
        this.currentPlayer = this.game.getCurrentPlayer();
        this.player = this.controller.getPlayer(this.currentPlayer);
        this.view.setCurrentPlayer(this.player);
    }

    private PawnColor randomColorPicker(){
        //TODO : funzione che genera un colore casuale a partire dai colori disponibili nella hall del currentPlayer
    }
}
