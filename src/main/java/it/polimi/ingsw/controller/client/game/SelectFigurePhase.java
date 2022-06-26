package it.polimi.ingsw.controller.client.game;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.networkHandler.Network;
import it.polimi.ingsw.controller.networking.AssistantCardDeckFigures;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.view.ViewHandler;
import it.polimi.ingsw.view.asset.exception.AssetErrorException;
import it.polimi.ingsw.view.asset.game.Game;

import java.util.ArrayList;

public class SelectFigurePhase implements GamePhase{
    private final ViewHandler view;
    private final Network network;
    private final ClientController controller;

    public SelectFigurePhase(ClientController controller) {
        this.controller = controller;
        this.view = controller.getViewHandler();
        this.network = this.controller.getNetwork();
    }
    @Override
    public void handle() {
        ArrayList<AssistantCardDeckFigures> figures = new ArrayList<>();
        try {
            try {
                figures.addAll(this.network.getPossibleDecks());
            } catch (MalformedMessageException e) {
                figures.addAll(this.network.getPossibleDecks());
            }
        } catch (MalformedMessageException  | ClientDisconnectedException e) {
            this.controller.handleError();
        } catch (AssetErrorException e) {
            this.controller.handleError("Doesn't found deck figure");
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

    @Override
    public GamePhase next() {
        Idle phase = new Idle(this.controller);
        phase.isGameStarted(false);
        return phase;
    }
}
