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
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.gamer.Gamer;

import java.util.ArrayList;

/**
 * This class represents the winning phase, which is a phase that checks if there is a winner or a tie
 */
public class VictoryPhase {
    private final Game game;
    private final GameController controller;
    private final View view;
    private String previousPhase;

    /**
     * Constructor of the class
     * @param game represents the current game
     * @param controller represents the controller linked with this game
     */
    public VictoryPhase(Game game, GameController controller, String previousPhase) {
        this.game = game;
        this.controller = controller;
        this.view = this.controller.getView();
        this.previousPhase = previousPhase;
    }

    public void handle() {
        ArrayList<Gamer> winners = new ArrayList<>();
        winners.addAll(this.game.checkWinner());
        if (!winners.isEmpty()) {
            ArrayList<String> names = new ArrayList<>();
            for (Gamer gamer : winners) {
                names.add(gamer.getUsername());
            }
            for (Player player : this.controller.getPlayers()) {
                this.view.setCurrentPlayer(player);
                try {
                    try {
                        this.view.sendWinner(names);
                    } catch (MalformedMessageException | TimeHasEndedException | FlowErrorException e) {
                        this.view.sendWinner(names);
                    }
                } catch (MalformedMessageException | ClientDisconnectedException | TimeHasEndedException | FlowErrorException e){
                    this.controller.handlePlayerError(player);
                }
            }
        }
        else {
            this.next();
        }
    }

    public GamePhase next() {
        return new ActionPhase3(this.game, this.controller);
    }
}
