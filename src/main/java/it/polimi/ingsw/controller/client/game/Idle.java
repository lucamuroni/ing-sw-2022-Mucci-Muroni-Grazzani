package it.polimi.ingsw.controller.client.game;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.networkHandler.Network;
import it.polimi.ingsw.controller.networking.Phase;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.controller.networking.messageParts.MessageFragment;
import it.polimi.ingsw.view.ViewHandler;
import it.polimi.ingsw.view.asset.game.Game;

import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.CONTEXT_PLANNING;

public class Idle implements GamePhase{
    private Phase name;
    private Network network;
    private ClientController controller;
    p
    private ViewHandler view;
    private Game game;
    private String context;

    public Idle(Game game, ClientController controller, ViewHandler view) {
        this.game = game;
        this.controller = controller;
        this.network = this.controller.getNetwork();
        this.view = view;
    }

    @Override
    public void handle() {
        this.view.goToIdle();
        MessageFragment context = null;
        try {
            try {
                context = MessageFragment.getEnum(this.network.getContext());
            } catch (MalformedMessageException e) {
                context = MessageFragment.getEnum(this.network.getContext());
            }
        } catch (MalformedMessageException | TimeHasEndedException | ClientDisconnectedException e) {
            this.controller.handleError();
        }

        switch (context) {
            case CONTEXT_CARD:
                try {
                    try {
                        this.network.getChosenAssistantCard(this.game);
                    } catch (MalformedMessageException e) {
                        this.network.getChosenAssistantCard(this.game);
                    }
                } catch (MalformedMessageException | TimeHasEndedException | ClientDisconnectedException e) {
                    this.controller.handleError();
                }
                //this.view.updateFigure();
                break;
            case CONTEXT_FIGURE:
                try {
                    try {
                        this.network.getChosenAssistantCardDeck(this.game);
                    } catch (MalformedMessageException e) {
                        this.network.getChosenAssistantCardDeck(this.game);
                    }
                } catch (MalformedMessageException | TimeHasEndedException | ClientDisconnectedException e) {
                    this.controller.handleError();
                }
            case CONTEXT_ISLAND:
                try {
                    try {
                        this.network.getIslandStatus(this.game);
                    } catch (MalformedMessageException e) {
                        this.network.getIslandStatus(this.game);
                    }
                } catch (MalformedMessageException | TimeHasEndedException | ClientDisconnectedException e) {
                    this.controller.handleError();
                }
                break;
            case CONTEXT_DASHBOARD:
                try {
                    try {
                        this.network.getDashboard(this.game);
                    } catch (MalformedMessageException e) {
                        this.network.getDashboard(this.game);
                    }
                } catch (MalformedMessageException | TimeHasEndedException | ClientDisconnectedException e) {
                    this.controller.handleError();
                }
                break;
            case CONTEXT_CLOUD:
                try {
                    try {
                        this.network.getCloudStatus(this.game);
                    } catch (MalformedMessageException e) {
                        this.network.getCloudStatus(this.game);
                    }
                } catch (MalformedMessageException | TimeHasEndedException | ClientDisconnectedException e) {
                    this.controller.handleError();
                }
                break;
            case CONTEXT_MOTHER:
                try {
                    try {
                        this.network.getMotherNaturePlace(this.game);
                    } catch (MalformedMessageException e) {
                        this.network.getMotherNaturePlace(this.game);
                    }
                } catch (MalformedMessageException | TimeHasEndedException | ClientDisconnectedException e) {
                    this.controller.handleError();
                }
                break;
        }


        try {
            try {
                this.network.getPhase();
            } catch (MalformedMessageException | TimeHasEndedException e) {
                this.network.getPhase();
            }
        } catch (MalformedMessageException | TimeHasEndedException | ClientDisconnectedException e) {
            this.controller.handleError();
        }
        //TODO: Idle sarà un case-switch: ogni volta si legger con una read() un messaggio getContext() (nello switch), il quale dice in che fase si trova
        //      il giocatore corrente, poi con un'altra read legge le informazioni che deve ricevere
    }

    private void update() {

    }

    @Override
    public Phase getPhase() {
        return name;
    }

    @Override
    public GamePhase next() {
        return null;
    }
}
