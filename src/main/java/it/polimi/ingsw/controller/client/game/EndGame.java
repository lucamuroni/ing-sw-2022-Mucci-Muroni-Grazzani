package it.polimi.ingsw.controller.client.game;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.networkHandler.Network;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.view.ViewHandler;
import it.polimi.ingsw.view.asset.game.Game;
import it.polimi.ingsw.view.asset.game.Gamer;
import it.polimi.ingsw.view.asset.game.Results;

import java.util.ArrayList;

public class EndGame implements GamePhase{
    private final Game game;
    private final ViewHandler view;
    private final Network network;
    private final ClientController controller;
    public EndGame(ClientController controller) {
        this.game = controller.getGame();
        this.view = controller.getViewHandler();
        this.controller = controller;
        this.network = this.controller.getNetwork();
    }

    @Override
    public void handle() {
        ArrayList<Gamer> winner = new ArrayList<>();
        try {
            try {
                winner.addAll(this.network.getWinner(game));
            } catch (MalformedMessageException e) {
                winner.addAll(this.network.getWinner(game));
            }
        } catch (MalformedMessageException | TimeHasEndedException | ClientDisconnectedException e) {
            this.controller.handleError();
        }
        if (winner.size() == 1) {
            if (winner.get(0).getId() == this.game.getSelf().getId()) {
                this.view.showEndGamePage(Results.WIN);
            } else {
                this.view.showEndGamePage(Results.LOSS);
            }

        } else {
            for (Gamer gamer : winner) {
                if (gamer.getId() == this.game.getSelf().getId()) {
                    this.view.showEndGamePage(Results.TIE);
                }
            }
        }
    }

    @Override
    public GamePhase next() {
        return null;
    }

}
