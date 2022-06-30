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

/**
 * @author Luca Muroni
 * This class implements the phase before the start of the game, which is DeckPhase, where the currentPlayer chooses a
 * deck of AssistantCards
 */
public class SelectFigurePhase implements GamePhase{
    private final ViewHandler view;
    private final Network network;
    private final ClientController controller;

    /**
     * Constructor of the class
     * @param controller is the controller of client side
     */
    public SelectFigurePhase(ClientController controller) {
        this.controller = controller;
        this.view = controller.getViewHandler();
        this.network = this.controller.getNetwork();
    }

    /**
     * This is the main method that handles the SelectFigurePhase
     */
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

    /**
     * This method changes the phase to the next one
     * @return the next GamePhase
     */
    @Override
    public GamePhase next() {
        Idle phase = new Idle(this.controller);
        phase.isGameStarted(false);
        return phase;
    }
}
