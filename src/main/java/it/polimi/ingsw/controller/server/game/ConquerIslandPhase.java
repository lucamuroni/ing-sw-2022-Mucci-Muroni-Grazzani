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
        try {
            this.view.phaseChanghe("ActionPhase1");
        } catch () {}
        TowerColor color = this.conquerIsland();
        ArrayList<Player> players = new ArrayList<>(this.controller.getPlayers());
        for (Player pl : players) {
            this.view.setCurrentPlayer(pl);
            try {
                try {
                    this.view.sendTowerColor(color);
                } catch (MalformedMessageException | TimeHasEndedException | FlowErrorException e) {
                    this.view.sendTowerColor(color);
                }
            } catch (MalformedMessageException | ClientDisconnectedException | TimeHasEndedException | FlowErrorException e){
                this.controller.handlePlayerError(pl);
            }
        }
    }

    /**
     * Method called by handle() that calculates the new owner of the island on which MotherNature is
     * @return the color of the towers of the new owner
     */
    private TowerColor conquerIsland() {
        Optional<Gamer> conqueror = this.game.checkIslandOwner();
        if (conqueror.isPresent()) {
            Gamer gamer = conqueror.get();
            return gamer.getTowerColor();
        }
        return null;
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
