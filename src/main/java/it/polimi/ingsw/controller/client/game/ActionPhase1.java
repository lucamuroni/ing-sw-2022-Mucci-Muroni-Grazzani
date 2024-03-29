package it.polimi.ingsw.controller.client.game;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.networkHandler.Network;
import it.polimi.ingsw.controller.networking.GameType;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.model.pawn.Student;
import it.polimi.ingsw.view.ViewHandler;
import it.polimi.ingsw.view.asset.exception.AssetErrorException;
import it.polimi.ingsw.view.asset.game.Game;
import it.polimi.ingsw.view.asset.game.Gamer;

/**
 * @author Luca Muroni
 * This class implements the second phase of the game, which is the ActionPhase1, where the current player moves 3/4 students
 * from his waitingRoom to an island or his hall
 */
public class ActionPhase1 implements GamePhase{
    private final Game game;
    private final ViewHandler view;
    private final Network network;
    private final ClientController controller;
    private final int numOfMoves;

    /**
     * Constructor of the class
     * @param controller is the controller of client side
     */
    public ActionPhase1(ClientController controller) {
        this.game = controller.getGame();
        this.view = controller.getViewHandler();
        this.controller = controller;
        this.network = this.controller.getNetwork();
        if (this.game.getGamers().size() == 2) {
            this.numOfMoves = 3;
        } else {
            this.numOfMoves = 4;
        }
    }

    /**
     * This is the main method that handles ActionPhase1
     */
    @Override
    public void handle() {
        Student stud = this.view.chooseStudentToMove();
        int location = this.view.choosePlace();
        try {
            try {
                this.network.sendColor(stud.getColor());
                this.network.sendLocation(location);
            } catch (MalformedMessageException e) {
                this.network.sendColor(stud.getColor());
                this.network.sendLocation(location);
            }
        } catch (MalformedMessageException | FlowErrorException  |
                 ClientDisconnectedException e) {
            this.controller.handleError("Could not sent move to server");
        }
        if (this.game.getGameType().equals(GameType.EXPERT.getName()) && location == 0) {
            try {
                try {
                    this.network.getCoins(game);
                } catch (MalformedMessageException e) {
                    this.network.getCoins(game);
                }
            } catch (MalformedMessageException | ClientDisconnectedException e) {
                this.controller.handleError();
            }
        }
    }

    /**
     * This method changes the phase to the next one
     * @return the next GamePhase
     */
    @Override
    public GamePhase next() {
        return new Idle(this.controller);
    }
}
