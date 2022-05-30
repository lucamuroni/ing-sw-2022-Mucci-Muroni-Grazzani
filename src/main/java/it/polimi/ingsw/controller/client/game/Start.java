package it.polimi.ingsw.controller.client.game;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.networkHandler.Network;
import it.polimi.ingsw.controller.networking.AssistantCardDeckFigures;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.view.ViewHandler;
import it.polimi.ingsw.view.asset.game.Game;

import java.util.ArrayList;

public class Start implements GamePhase {
    private final Game game;
    private final ViewHandler view;
    private final Network network;
    private final ClientController controller;

    public Start(ClientController controller) {
        this.game = controller.getGame();
        this.view = controller.getViewHandler();
        this.controller = controller;
        this.network = this.controller.getNetwork();
    }

    @Override
    public void handle() {
        this.updateMNPlace();
        this.updateIslandStatus();
        for (int i = 0; i<this.game.getGamers().size(); i++) {
            this.updateDashboards();
        }
        ArrayList<AssistantCardDeckFigures> figures = new ArrayList<>();
        try {
            try {
                figures.addAll(this.network.getPossibleDecks());
            } catch (MalformedMessageException e) {
                figures.addAll(this.network.getPossibleDecks());
            }
        } catch (MalformedMessageException | TimeHasEndedException | ClientDisconnectedException e) {
            this.controller.handleError();
        }
        AssistantCardDeckFigures figure = this.view.chooseFigure(figures);
        try {
            try {
                this.network.sendAssistantCardDeck(figure);
            } catch (MalformedMessageException e) {
                this.network.sendAssistantCardDeck(figure);
            }
        } catch (MalformedMessageException e) {
            this.controller.handleError();
        }
    }

    private void updateMNPlace() {
        try {
            try {
                this.network.getMotherNaturePlace(this.game);
            } catch (MalformedMessageException e) {
                this.network.getMotherNaturePlace(this.game);
            }
        } catch (MalformedMessageException | TimeHasEndedException | ClientDisconnectedException e) {
            this.controller.handleError();
        }
    }

    private void updateIslandStatus() {
        try {
            try {
                this.network.getIslandStatus(this.game);
            } catch (MalformedMessageException e) {
                this.network.getIslandStatus(this.game);
            }
        } catch (MalformedMessageException | TimeHasEndedException | ClientDisconnectedException e) {
            this.controller.handleError();
        }
    }

    private void updateDashboards() {
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

    @Override
    public GamePhase next() {
        return new Idle(this.controller);
    }

}
