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

import java.util.Objects;

public class Idle implements GamePhase{
    private final Network network;
    private final ClientController controller;
    private final ViewHandler view;
    private final Game game;
    private GamePhase nextPhase;
    private boolean stop;

    public Idle(ClientController controller) {
        this.game = controller.getGame();
        this.controller = controller;
        this.network = this.controller.getNetwork();
        this.view = this.controller.getViewHandler();
    }

    @Override
    public void handle() {
        this.view.goToIdle();
        this.stop = false;
        while (!stop) {
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
            switch (Objects.requireNonNull(context)) {
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
                    break;
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
                case CONTEXT_PHASE:
                    Phase phase = null;
                    try {
                        try {
                            phase = this.network.getPhase();
                        } catch (MalformedMessageException | TimeHasEndedException e) {
                            phase = this.network.getPhase();
                        }
                    } catch (MalformedMessageException | TimeHasEndedException | ClientDisconnectedException e) {
                        this.controller.handleError();
                    }
                    switch (Objects.requireNonNull(phase)) {
                        case PLANNING_PHASE -> nextPhase = new PlanningPhase(this.controller);
                        case ACTION_PHASE_1 -> nextPhase = new ActionPhase1(this.controller);
                        case MOTHER_NATURE_PHASE -> nextPhase = new MotherNaturePhase(this.controller);
                        case ACTION_PHASE_3 -> nextPhase = new ActionPhase3(this.controller);
                        case END_GAME_PHASE -> nextPhase = new EndGame(this.controller);
                    }
                    this.stop = true;
                    break;
                case CONTEXT_USERNAME:
                    try {
                        try {
                            this.network.getCurrentPlayer(this.game);
                        } catch (MalformedMessageException e) {
                            this.network.getCurrentPlayer(this.game);
                        }
                    } catch (MalformedMessageException | TimeHasEndedException | ClientDisconnectedException e) {
                        this.controller.handleError();
                    }
                    break;
            }
        }
    }

    @Override
    public GamePhase next() {
        return nextPhase;
    }
}
