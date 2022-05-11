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
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.gamer.Gamer;

import java.util.ArrayList;
import java.util.Random;

/**
 * This class implements the first part of the third phase of the game, which is the ActionPhase2, and in particular this part
 * handles the movement of MotherNature
 */
public class MotherNaturePhase implements GamePhase{
    private final Game game;
    private final GameController controller;
    private Gamer currentPlayer;
    private final View view;

    /**
     * Constructor of the class
     * @param game represents the current game
     * @param controller represents the controller linked with this game
     */
    public MotherNaturePhase(Game game, GameController controller){
        this.game = game;
        this.controller = controller;
        this.currentPlayer = this.game.getCurrentPlayer();
        this.view = this.controller.getView();
    }

    /**
     * this is the main method that handles this phase
     */
    @Override
    public void handle() {
        try {
            this.view.phaseChanghe("ActionPhase1");
        } catch () {}
        try {
            this.moveMotherNature(this.controller.getPlayer(this.game.getCurrentPlayer()));
            ArrayList<Player> players = new ArrayList<>(this.controller.getPlayers());
            players.remove(this.controller.getPlayer(this.game.getCurrentPlayer()));
            for (Player pl : players) {
                this.view.setCurrentPlayer(pl);
                try {
                    try {
                        this.view.updateMotherNaturePlace(this.game.getMotherNature().getPlace());
                    } catch (MalformedMessageException | TimeHasEndedException | FlowErrorException e) {
                        this.view.updateMotherNaturePlace(this.game.getMotherNature().getPlace());
                    }
                } catch (MalformedMessageException | ClientDisconnectedException | TimeHasEndedException | FlowErrorException e){
                    this.controller.handlePlayerError(pl);
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
     * This method handles the movement of MN and is called in handle()
     * @param player represents the currentPlayer that is playing
     * @throws GenericErrorException when the message from the client is malformed twice or the player disconnects from the game
     */
    private void moveMotherNature(Player player) throws GenericErrorException {
        this.view.setCurrentPlayer(player);
        Island place = null;
        ArrayList<Island> possibleChoices = this.game.getMotherNatureDestination();
        try {
            try {
                place = this.view.getMNLocation(possibleChoices);
            } catch (MalformedMessageException e) {
                place = this.view.getMNLocation(possibleChoices);
            } catch (TimeHasEndedException e) {
                place = this.getRandomIsland(possibleChoices);
            }
        } catch (MalformedMessageException | ClientDisconnectedException e) {
            this.controller.handlePlayerError(player);
            throw new GenericErrorException();
        } catch (TimeHasEndedException e) {
            place = this.getRandomIsland(possibleChoices);
        }
        this.game.moveMotherNature(place);
    }

    /**
     * Method called by moveMotherNature() that picks a random island when the player doesn't reply in time
     * @param choices is the ArrayList of possible islands to choose from
     * @return a random island
     */
    private Island getRandomIsland(ArrayList<Island> choices) {
        Random random = new Random();
        int rand = random.nextInt(0, choices.size());
        return choices.get(rand);
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
