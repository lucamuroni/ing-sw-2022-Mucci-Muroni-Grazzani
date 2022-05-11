package it.polimi.ingsw.controller.server.game;

import it.polimi.ingsw.controller.server.game.gameController.GameController;
import it.polimi.ingsw.controller.server.virtualView.View;
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
        ArrayList<String> names = new ArrayList<>();
        names.addAll(this.checkTowers());
        if (names.isEmpty()) {
            names.addAll()
        }

    }

    private ArrayList<String> checkTowers() {
        ArrayList<String> winner = new ArrayList<>();
        for (Gamer gamer : this.game.getGamers()) {
            if (game.checkWinner1()) {
                winner.add(gamer.getUsername());
                return winner;
            }
        }
        return winner;
    }

    private ArrayList<String> checkNumTowers() {
        ArrayList<String> winner = new ArrayList<>();
        
    }

    public GamePhase next() {
        return new ActionPhase3(this.game, this.controller);
    }
}
