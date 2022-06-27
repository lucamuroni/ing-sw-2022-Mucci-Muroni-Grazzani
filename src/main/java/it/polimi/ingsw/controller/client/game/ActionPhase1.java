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
            try {
                try {
                    for (Gamer gamer : this.game.getGamers()) {
                        this.network.getDashboard(this.game);
                    }
                    if (location>0)
                        this.network.getIslandStatus(this.game);
                } catch (MalformedMessageException e) {
                    for (Gamer gamer : this.game.getGamers()) {
                        this.network.getDashboard(this.game);
                    }
                    if (location>0)
                        this.network.getIslandStatus(this.game);
                }
            } catch (MalformedMessageException | ClientDisconnectedException e) {
                this.controller.handleError();
            } catch (AssetErrorException e) {
                this.controller.handleError("Doesn't found dashboard");
            }
        }
    }

    @Override
    public GamePhase next() {
        return new Idle(this.controller);
    }
}
