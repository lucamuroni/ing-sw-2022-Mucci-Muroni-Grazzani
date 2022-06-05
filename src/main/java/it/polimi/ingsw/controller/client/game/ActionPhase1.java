package it.polimi.ingsw.controller.client.game;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.networkHandler.Network;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.model.pawn.Student;
import it.polimi.ingsw.view.ViewHandler;
import it.polimi.ingsw.view.asset.game.Game;

public class ActionPhase1 implements GamePhase{
    private final Game game;
    private final ViewHandler view;
    private final Network network;
    private final ClientController controller;
    private final int numOfMoves;

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
    @Override
    public void handle() {
        for (int i = 0; i<numOfMoves; i++) {
            Student stud = this.view.chooseStudentToMove();
            int location = this.view.choosePlace();
            this.view.goToIdle();
            try {
                try {
                    this.network.sendColor(stud.getColor());
                    this.network.sendLocation(location);
                } catch (MalformedMessageException e) {
                    this.network.sendColor(stud.getColor());
                    this.network.sendLocation(location);
                }
            } catch (MalformedMessageException | FlowErrorException | TimeHasEndedException |
                     ClientDisconnectedException e) {
                this.controller.handleError("Could not sent move to server");
            }
            try {
                try {
                    this.network.getDashboard(this.game);
                } catch (MalformedMessageException e) {
                    this.network.getDashboard(this.game);
                }
            } catch (MalformedMessageException | TimeHasEndedException | ClientDisconnectedException e) {
                this.controller.handleError();
            }
        }
    }

    @Override
    public GamePhase next() {
        return new MotherNaturePhase(this.controller);
    }
}
