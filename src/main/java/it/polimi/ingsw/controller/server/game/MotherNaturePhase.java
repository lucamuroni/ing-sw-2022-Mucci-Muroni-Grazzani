package it.polimi.ingsw.controller.server.game;

import it.polimi.ingsw.controller.networking.Player;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.server.game.exceptions.ModelErrorException;
import it.polimi.ingsw.controller.server.virtualView.View;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.game.Game;

import java.util.ArrayList;
import java.util.Random;

import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.CONTEXT_MOTHER;

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
        try {
            this.moveMotherNature(this.controller.getPlayer(this.game.getCurrentPlayer()));
            ArrayList<Player> players = new ArrayList<>(this.controller.getPlayers());
            players.remove(this.controller.getPlayer(this.game.getCurrentPlayer()));
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
        } catch (ModelErrorException e) {
            this.controller.shutdown("Error founded in model : shutting down this game");
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
        } //TODO controllare correttezza
        this.game.moveMotherNature(place);
    }

    /**
     * This method is called by moveMotherNature() and it picks a random island when the player doesn't reply in time
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
