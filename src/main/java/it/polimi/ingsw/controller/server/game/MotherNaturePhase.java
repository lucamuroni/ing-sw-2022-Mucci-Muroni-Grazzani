package it.polimi.ingsw.controller.server.game;

import it.polimi.ingsw.controller.networking.Phase;
import it.polimi.ingsw.controller.networking.Player;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.server.game.exceptions.ModelErrorException;
import it.polimi.ingsw.controller.server.virtualView.View;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.game.Game;

import java.util.ArrayList;

import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.CONTEXT_MOTHER;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.CONTEXT_PHASE;

/**
 * This class implements the first part of the third phase of the game, which is the MotherNaturePhase, and in particular this part
 * handles the movement of MotherNature
 */
public class MotherNaturePhase implements GamePhase{
    private final Game game;
    private final GameController controller;
    private final View view;

    /**
     * Constructor of the class
     * @param game represents the current game
     * @param controller represents the controller linked with this game
     */
    public MotherNaturePhase(Game game, GameController controller){
        this.game = game;
        this.controller = controller;
        this.view = this.controller.getView();
    }

    /**
     * This is the main method that handles the MotherNaturePhase
     */
    @Override
    public void handle() {
        Player current = null;
        try {
            current = this.controller.getPlayer(this.game.getCurrentPlayer());
        } catch (ModelErrorException e) {
            this.controller.shutdown("Error while getting current player");
        }
        this.view.setCurrentPlayer(current);
        try {
            try {
                this.view.sendContext(CONTEXT_PHASE.getFragment());
                this.view.sendNewPhase(Phase.MOTHER_NATURE_PHASE);
            } catch (MalformedMessageException e) {
                this.view.sendContext(CONTEXT_PHASE.getFragment());
                this.view.sendNewPhase(Phase.MOTHER_NATURE_PHASE);
            }
        } catch (MalformedMessageException | FlowErrorException | ClientDisconnectedException e) {
            this.controller.handlePlayerError(current,"Error while sending mother nature phase");
        }
        this.moveMotherNature(current);
        ArrayList<Player> players = new ArrayList<>(this.controller.getPlayers());
        players.remove(current);
        for (Player pl : players) {
            this.view.setCurrentPlayer(pl);
            try {
                try {
                    this.view.sendContext(CONTEXT_MOTHER.getFragment());
                    this.view.updateMotherNaturePlace(this.game.getMotherNature().getPlace());
                } catch (MalformedMessageException | FlowErrorException e) {
                    this.view.sendContext(CONTEXT_MOTHER.getFragment());
                    this.view.updateMotherNaturePlace(this.game.getMotherNature().getPlace());
                }
            } catch (MalformedMessageException | ClientDisconnectedException | FlowErrorException e){
                this.controller.handlePlayerError(pl,"Error while updating mother nature place");
            }
        }
    }

    /**
     * This method handles the movement of MN, and it is called in handle()
     * @param player represents the currentPlayer that is playing
     */
    private void moveMotherNature(Player player) {
        this.view.setCurrentPlayer(player);
        Island place = null;
        ArrayList<Island> possibleChoices = this.game.getMotherNatureDestination();
        try {
            try {
                place = this.view.getMNLocation(possibleChoices);
            } catch (MalformedMessageException e) {
                place = this.view.getMNLocation(possibleChoices);
            }
        } catch (MalformedMessageException | ClientDisconnectedException e) {
            this.controller.handlePlayerError(player,"Error while getting mother nature location");
        }
        this.game.moveMotherNature(place);
    }

    /**
     * This method changes the phase to the next one
     * @return the next GamePhase
     */
    @Override
    public GamePhase next() {
        return new ConquerIslandPhase(this.game, this.controller);
    }
}
