package it.polimi.ingsw.controller.server.game;

import it.polimi.ingsw.controller.networking.Phase;
import it.polimi.ingsw.controller.networking.Player;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.server.virtualView.View;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.gamer.Gamer;

import java.util.ArrayList;

import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.CONTEXT_PHASE;

/**
 * @author Luca Muroni
 * @author Davide Grazzani
 * This class represents the winning phase, and the GameController goes into this phase only if a winningCondition has been checked
 */
public class VictoryPhase implements GamePhase{
    private final Game game;
    private final GameController controller;
    private final View view;
    private final ArrayList<String> names;

    /**
     * Constructor of the class
     * @param game represents the current game
     * @param controller represents the controller linked with this game
     */
    public VictoryPhase(Game game, GameController controller) {
        this.game = game;
        this.controller = controller;
        this.view = this.controller.getView();
        this.names = new ArrayList<>();
    }

    /**
     * This is the main and only method that handles the VictoryPhase
     */
    public void handle() {
        if(this.names.size() == 0){
            ArrayList<Gamer> winners = new ArrayList<>(this.game.checkWinner());
            if(!winners.isEmpty()){
                for (Gamer gamer : winners) {
                    names.add(gamer.getUsername());
                }
            }
        }
        for (Player player : this.controller.getPlayers()) {
            this.view.setCurrentPlayer(player);
            try {
                try{
                    this.view.sendContext(CONTEXT_PHASE.getFragment());
                    this.view.sendNewPhase(Phase.END_GAME_PHASE);
                }catch (MalformedMessageException | FlowErrorException e){
                    this.view.sendContext(CONTEXT_PHASE.getFragment());
                    this.view.sendNewPhase(Phase.END_GAME_PHASE);
                }
            }catch (MalformedMessageException | FlowErrorException  | ClientDisconnectedException e) {
                this.controller.handlePlayerError(player,"Error while sending END_GAME_PHASE");
            }
            try {
                try {
                    this.view.sendWinner(names);
                } catch (MalformedMessageException | FlowErrorException e) {
                    this.view.sendWinner(names);
                }
            } catch (MalformedMessageException | ClientDisconnectedException | FlowErrorException e){
                this.controller.handlePlayerError(player,"Error while sending winner");
            }
        }
    }

    /**
     * This method changes the phase to the next one, but this is the last phase, so basically there isn't a call to this
     * method from someone
     * @return null
     */
    public GamePhase next() {
        return null;
    }

    /**
     * Method used when a client disconnect from the game
     */
    public void setError(){
        this.names.add("Error");
    }
}
