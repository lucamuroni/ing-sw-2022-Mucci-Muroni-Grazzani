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
import it.polimi.ingsw.model.Cloud;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.gamer.Gamer;

import java.util.ArrayList;
import java.util.Random;

/**
 * This class implements the fourth phase of the game, which is the ActionPhase3, where the currentPlayer chooses a cloud
 */
public class ActionPhase3 implements GamePhase{
    private final Game game;
    private final GameController controller;
    private final View view;

    /**
     * Constructor of the class
     * @param game represents the current game
     * @param controller represents the controller linked with this game
     */
    public ActionPhase3(Game game, GameController controller){
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
            try{
                this.view.sendNewPhase(Phase.ACTION_PHASE_3);
            }catch(MalformedMessageException | FlowErrorException | TimeHasEndedException e){
                this.view.sendNewPhase(Phase.ACTION_PHASE_3);
            }
        }catch (MalformedMessageException | FlowErrorException | TimeHasEndedException | ClientDisconnectedException e) {
            try {
                this.controller.handlePlayerError(this.controller.getPlayer(this.game.getCurrentPlayer()));
            } catch (ModelErrorException i) {
                this.controller.shutdown();
            }
        }
        try {
            this.choseCloud(this.controller.getPlayer(this.game.getCurrentPlayer()));
            ArrayList<Player> players = new ArrayList<>(this.controller.getPlayers());
            players.remove(this.controller.getPlayer(this.game.getCurrentPlayer()));
            for (Player pl : players) {
                this.view.setCurrentPlayer(pl);
                try {
                    try {
                        this.view.updateCloudsStatus(this.game.getClouds());
                        this.view.updateDashboards(this.game.getGamers(), this.game);
                    } catch (MalformedMessageException | TimeHasEndedException | FlowErrorException e) {
                        this.view.updateCloudsStatus(this.game.getClouds());
                        this.view.updateDashboards(this.game.getGamers(), this.game);
                    }
                } catch (MalformedMessageException | ClientDisconnectedException | TimeHasEndedException | FlowErrorException e){
                    this.controller.handlePlayerError(pl);
                }
            }
        } catch (ModelErrorException e) {
            this.controller.shutdown();
            e.printStackTrace();
        }
    }

    /**
     * This method handles the pull of the cloud chosen by the player, and it is called in handle()
     * @param player represents the currentPlayer
     * @throws ModelErrorException
     */
    private void choseCloud(Player player) throws ModelErrorException {
        this.view.setCurrentPlayer(player);
        Cloud chosenCloud = null;
        ArrayList<Cloud> possibleChoices = new ArrayList<>();
        for (Cloud cloud : this.game.getClouds()) {
            if (!cloud.isEmpty()) {
                possibleChoices.add(cloud);
            }
        }
        try {
            try {
                chosenCloud = this.view.getChosenCloud(possibleChoices);
            } catch (MalformedMessageException e) {
                chosenCloud = this.view.getChosenCloud(possibleChoices);
            } catch (TimeHasEndedException e) {
                chosenCloud = this.getRandomCloud(possibleChoices);
            }
        } catch (MalformedMessageException | ClientDisconnectedException e) {
            this.controller.handlePlayerError(player);
        } catch (TimeHasEndedException e) {
            chosenCloud = this.getRandomCloud(possibleChoices);
        }
        Gamer currentPlayer = this.game.getCurrentPlayer();
        currentPlayer.getDashboard().addStudentsWaitingRoom(chosenCloud.pullStudent());
    }

    /**
     * Method called by chooseCloud() that picks a random cloud when the player doesn't reply in time
     * @param clouds is the ArrayList of possible choices
     * @return a random cloud
     */
    private Cloud getRandomCloud(ArrayList<Cloud> clouds) {
        Random random = new Random();
        int rand = random.nextInt(0, clouds.size());
        return clouds.get(rand);
    }

    /**
     * This method changes the phase to the next one
     * @return the next GamePhase
     */
    @Override
    public GamePhase next() {
        return new PlanningPhase(this.game, this.controller);
    }
}
