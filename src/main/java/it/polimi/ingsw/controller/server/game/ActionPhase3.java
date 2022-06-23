package it.polimi.ingsw.controller.server.game;

import it.polimi.ingsw.controller.networking.GameType;
import it.polimi.ingsw.controller.networking.Phase;
import it.polimi.ingsw.controller.networking.Player;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.server.game.exceptions.ModelErrorException;
import it.polimi.ingsw.controller.server.virtualView.View;
import it.polimi.ingsw.model.Cloud;
import it.polimi.ingsw.model.game.ExpertGame;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.gamer.Gamer;

import java.util.ArrayList;
import java.util.Random;

import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.*;

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
     * This is the main method that handles the ActionPhase3
     */
    @Override
    public void handle() {
        this.game.setTurnNumber();
        try {
            this.view.setCurrentPlayer(this.controller.getPlayer(this.game.getCurrentPlayer()));
        } catch (ModelErrorException e) {
            this.controller.shutdown("Error founded in model : shutting down this game");
        }
        if (!this.calculateChoices().isEmpty()) {
            try {
                try{
                    this.view.sendContext(CONTEXT_PHASE.getFragment());
                    this.view.sendNewPhase(Phase.ACTION_PHASE_3);
                }catch(MalformedMessageException | FlowErrorException  e){
                    this.view.sendContext(CONTEXT_PHASE.getFragment());
                    this.view.sendNewPhase(Phase.ACTION_PHASE_3);
                }
            }catch (MalformedMessageException | FlowErrorException | ClientDisconnectedException e) {
                try {
                    this.controller.handlePlayerError(this.controller.getPlayer(this.game.getCurrentPlayer()),"Error while sending ACTION PHASE 3");
                } catch (ModelErrorException i) {
                    this.controller.shutdown("Error founded in model : shutting down this game");
                }
            }
            try {
                this.choseCloud(this.controller.getPlayer(this.game.getCurrentPlayer()));
                ArrayList<Player> players = new ArrayList<>(this.controller.getPlayers());
                //players.remove(this.controller.getPlayer(this.game.getCurrentPlayer()));
                for (Player pl : players) {
                    this.view.setCurrentPlayer(pl);
                    try {
                        try {
                            for (Cloud cloud : this.game.getClouds()) {
                                this.view.sendContext(CONTEXT_CLOUD.getFragment());
                                this.view.updateCloudsStatus(cloud);
                            }
                            for (Gamer gamer : this.game.getGamers()) {
                                this.view.sendContext(CONTEXT_DASHBOARD.getFragment());
                                this.view.updateDashboards(gamer, this.game);
                            }
                        } catch (MalformedMessageException  | FlowErrorException e) {
                            for (Cloud cloud : this.game.getClouds()) {
                                this.view.sendContext(CONTEXT_CLOUD.getFragment());
                                this.view.updateCloudsStatus(cloud);
                            }
                            for (Gamer gamer : this.game.getGamers()) {
                                this.view.sendContext(CONTEXT_DASHBOARD.getFragment());
                                this.view.updateDashboards(gamer, this.game);
                            }
                        }
                    } catch (MalformedMessageException | ClientDisconnectedException | FlowErrorException e){
                        this.controller.handlePlayerError(pl,"Error while updating clouds and dashboards");
                    }
                }
            } catch (ModelErrorException e) {
                this.controller.shutdown("Error founded in model : shutting down this game");
                e.printStackTrace();
            }
        }
    }

    /**
     * This method handles the pull of the cloud chosen by the player, and it is called in handle()
     * @param player represents the currentPlayer
     */
    private void choseCloud(Player player) {
        this.view.setCurrentPlayer(player);
        Gamer currentPlayer = this.game.getCurrentPlayer();
        Cloud chosenCloud = null;
        ArrayList<Cloud> possibleChoices = new ArrayList<>(this.calculateChoices());
        try {
            try {
                chosenCloud = this.view.getChosenCloud(possibleChoices);
            } catch (MalformedMessageException e) {
                chosenCloud = this.view.getChosenCloud(possibleChoices);
            }
        } catch (MalformedMessageException | ClientDisconnectedException e) {
            this.controller.handlePlayerError(player, "Error while getting the chosen cloud");
        }
        currentPlayer.selectCloud(chosenCloud);
        //currentPlayer.getDashboard().addStudentsWaitingRoom(chosenCloud.pullStudent());
    }

    private ArrayList<Cloud> calculateChoices() {
        ArrayList<Cloud> clouds = new ArrayList<>();
        for (Cloud cloud : this.game.getClouds()) {
            if (!cloud.isEmpty()) {
                clouds.add(cloud);
            }
        }
        return clouds;
    }

    /**
     * This method is called by chooseCloud() and it picks a random cloud when the player doesn't reply in time
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
        boolean mustEndGame = false;
        if(this.game.getBag().isEmpty()){
            mustEndGame = true;
        }else{
            for(Gamer gamer : this.game.getGamers()){
                if (gamer.getDeck().getCardList().isEmpty()) {
                    mustEndGame = true;
                    break;
                }
            }
        }
        if(mustEndGame){
            return new VictoryPhase(this.game,this.controller);
        }
        if(this.game.getTurnNumber()%this.game.getGamers().size()==0){
            for(Gamer gamer : this.game.getGamers()){
                gamer.getDeck().setPastSelection();
            }
            this.game.updatePlayersOrder();
            this.controller.updatePlayersOrder();
            if(controller.getGameType()== GameType.EXPERT){
                return new CharacterCardPhase((ExpertGame) this.game,this.controller,new PlanningPhase(this.game, this.controller));
            }
            return new PlanningPhase(this.game, this.controller);
        }else{
            if(controller.getGameType()== GameType.EXPERT){
                return new CharacterCardPhase((ExpertGame) this.game,this.controller,new ActionPhase1(this.game, this.controller));
            }
            return new ActionPhase1(this.game, this.controller);
        }
    }
}
