package it.polimi.ingsw.controller.server.game;

import it.polimi.ingsw.controller.networking.Player;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.controller.server.game.gameController.GameController;
import it.polimi.ingsw.controller.server.virtualView.View;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.gamer.Gamer;
import it.polimi.ingsw.model.pawn.TowerColor;

import java.util.ArrayList;
import java.util.Optional;

/**
 * This class implements the second part of the third phase of the game, which is the MotherNaturePhase, and in particular this part
 * handles the conquest of an island
 */
public class ConquerIslandPhase implements GamePhase{
    private final Game game;
    private final GameController controller;
    private final View view;

    /**
     * Constructor of the class
     * @param game represents the current game
     * @param controller represents the controller link with this game
     */
    public ConquerIslandPhase(Game game, GameController controller){
        this.game = game;
        this.controller = controller;
        this.view = this.controller.getView();
    }

    /**
     * This is the main method that handles this phase
     */
    @Override
    public void handle() {
        this.game.checkIslandOwner(this.game.getMotherNature().getPlace());
        ArrayList<Player> players = new ArrayList<>(this.controller.getPlayers());
        for (Player pl : players) {
            this.view.setCurrentPlayer(pl);
            try {
                try {
                    this.view.updateIslandStatus(this.game.getMotherNature().getPlace());
                } catch (MalformedMessageException | TimeHasEndedException | FlowErrorException e) {
                    this.view.updateIslandStatus(this.game.getMotherNature().getPlace());
                }
            } catch (MalformedMessageException | ClientDisconnectedException | TimeHasEndedException | FlowErrorException e){
                this.controller.handlePlayerError(pl);
            }
        }
    }

    /**
     * This method changes the phase to the next one
     * @return the next GamePhase
     */
    @Override
    public GamePhase next() {
        return new VictoryPhase(this.game, this.controller);
    }
}
