package it.polimi.ingsw.controller.client.game;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.networkHandler.Network;
import it.polimi.ingsw.controller.networking.Phase;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.messageParts.MessageFragment;
import it.polimi.ingsw.model.expert.CharacterCard;
import it.polimi.ingsw.view.ViewHandler;
import it.polimi.ingsw.view.asset.exception.AssetErrorException;
import it.polimi.ingsw.view.asset.game.Game;
import it.polimi.ingsw.view.asset.game.Gamer;

import java.util.Objects;

/**
 * @author Luca Muroni
 * @author Davide Grazzani
 * This class implements the phase where a player isn't playing and is waiting from the server the permission to do so
 */
public class Idle implements GamePhase{
    private final Network network;
    private final ClientController controller;
    private final ViewHandler view;
    private final Game game;
    private GamePhase nextPhase;
    private boolean isGameStarted;

    /**
     * Constructor of the class
     * @param controller is the controller of client side
     */
    public Idle(ClientController controller) {
        this.controller = controller;
        this.game = this.controller.getGame();
        this.network = this.controller.getNetwork();
        this.view = this.controller.getViewHandler();
        this.isGameStarted = true;
    }

    /**
     * This is the main method that handles Idle
     */
    @Override
    public void handle() {
        String s;
        if(isGameStarted){
            this.view.goToIdle();
        }
        boolean stop = false;
        while (!stop) {
            MessageFragment context = null;
            try {
                try {
                    context = MessageFragment.getEnum(this.network.getContext());
                } catch (MalformedMessageException e) {
                    context = MessageFragment.getEnum(this.network.getContext());
                }
            } catch (MalformedMessageException | ClientDisconnectedException e) {
                this.controller.handleError("Could not await for context");
            } catch (AssetErrorException e) {
                this.controller.handleError("Doesn't found context");
            }
            switch (Objects.requireNonNull(context)) {
                case CONTEXT_CARD:
                    try {
                        try {
                            this.network.getChosenAssistantCard(this.game);
                        } catch (MalformedMessageException e) {
                            this.network.getChosenAssistantCard(this.game);
                        }
                    } catch (MalformedMessageException | ClientDisconnectedException e) {
                        this.controller.handleError();
                    } catch (AssetErrorException e) {
                        this.controller.handleError("Doesn't found card");
                    }
                    break;
                case CONTEXT_FIGURE:
                    try {
                        try {
                            this.network.getChosenAssistantCardDeck(this.game);
                        } catch (MalformedMessageException e) {
                            this.network.getChosenAssistantCardDeck(this.game);
                        }
                    } catch (MalformedMessageException | ClientDisconnectedException e) {
                        this.controller.handleError();
                    } catch (AssetErrorException e) {
                        this.controller.handleError("Doesn't found deck's figure");
                    }
                    break;
                case CONTEXT_ISLAND:
                    try {
                        try {
                            this.network.getIslandStatus(this.game);
                        } catch (MalformedMessageException e) {
                            this.network.getIslandStatus(this.game);
                        }
                    } catch (MalformedMessageException | ClientDisconnectedException e) {
                        this.controller.handleError();
                    } catch (AssetErrorException e) {
                        this.controller.handleError("Doesn't found island");
                    }
                    break;
                case CONTEXT_DASHBOARD:
                    try {
                        try {
                            this.network.getDashboard(this.game);
                        } catch (MalformedMessageException e) {
                            this.network.getDashboard(this.game);
                        }
                    } catch (MalformedMessageException | ClientDisconnectedException e) {
                        this.controller.handleError();
                    } catch (AssetErrorException e) {
                        this.controller.handleError("Doesn't found dashboard");
                    }
                    break;
                case CONTEXT_CLOUD:
                    try {
                        try {
                            this.network.getCloudStatus(this.game);
                        } catch (MalformedMessageException e) {
                            this.network.getCloudStatus(this.game);
                        }
                    } catch (MalformedMessageException | ClientDisconnectedException e) {
                        this.controller.handleError();
                    }  catch (AssetErrorException e) {
                        this.controller.handleError("Doesn't found cloud");
                    }
                    break;
                case CONTEXT_MOTHER:
                    try {
                        try {
                            this.network.getMotherNaturePlace(this.game);
                        } catch (MalformedMessageException e) {
                            this.network.getMotherNaturePlace(this.game);
                        }
                    } catch (MalformedMessageException | ClientDisconnectedException e) {
                        this.controller.handleError();
                    } catch (AssetErrorException e) {
                        this.controller.handleError("Doesn't found island of mother nature");
                    }
                    break;
                case CONTEXT_PHASE:
                    Phase phase = null;
                    try {
                        try {
                            phase = this.network.getPhase();
                        } catch (MalformedMessageException e) {
                            phase = this.network.getPhase();
                        }
                    } catch (MalformedMessageException | ClientDisconnectedException e) {
                        this.controller.handleError();
                    } catch (AssetErrorException e) {
                        this.controller.handleError("Doesn't found phase");
                    }
                    switch (Objects.requireNonNull(phase)) {
                        case DECK_PHASE -> nextPhase = new SelectFigurePhase(this.controller);
                        case PLANNING_PHASE -> {
                            PlanningPhase planningPhase = new PlanningPhase(this.controller);
                            if (!isGameStarted) {
                                planningPhase.setInitView(true);
                            }
                            nextPhase = planningPhase;
                        }
                        case ACTION_PHASE_1 -> nextPhase = new ActionPhase1(this.controller);
                        case MOTHER_NATURE_PHASE -> nextPhase = new MotherNaturePhase(this.controller);
                        case ACTION_PHASE_3 -> nextPhase = new ActionPhase3(this.controller);
                        case END_GAME_PHASE -> nextPhase = new EndGame(this.controller);
                        case CHARACTER_PHASE -> nextPhase = new ExpertPhase(this.controller);
                    }
                    stop = true;
                    break;
                case CONTEXT_USERNAME:
                    try {
                        try {
                            this.network.getCurrentPlayer(this.game);
                        } catch (MalformedMessageException e) {
                            this.network.getCurrentPlayer(this.game);
                        }
                    } catch (MalformedMessageException | ClientDisconnectedException e) {
                        this.controller.handleError();
                    } catch (AssetErrorException e) {
                        this.controller.handleError("Doesn't found player name");
                    }
                    s = "The player "+this.game.getCurrentPlayer()+" has started his turn";
                    this.view.popUp(s);
                    break;
                case CONTEXT_MERGE:
                    try {
                        try {
                            this.network.getMergedIslands(this.view);
                        } catch (MalformedMessageException e) {
                            this.network.getMergedIslands(this.view);
                        }
                    } catch (MalformedMessageException | ClientDisconnectedException e) {
                        this.controller.handleError();
                    } catch (AssetErrorException e) {
                        this.controller.handleError("Doesn't found island");
                    }
                    break;
                case CONTEXT_CHARACTER:
                    try {
                        try {
                            this.network.getChosenCharacterCard(this.game);
                        } catch (MalformedMessageException e) {
                            this.network.getChosenCharacterCard(this.game);
                        }
                    } catch (MalformedMessageException | ClientDisconnectedException e) {
                        this.controller.handleError();
                    } catch (AssetErrorException e) {
                        this.controller.handleError("Error while getting character card");
                    }
                    CharacterCard card = null;
                    for(Gamer gamer : this.game.getGamers()){
                        if(gamer.getUsername().equals(this.game.getCurrentPlayer())){
                            card = gamer.getCurrentExpertCardSelection();
                        }
                    }
                    s = "The player "+this.game.getCurrentPlayer()+" has played "+card.getName();
                    this.view.popUp(s);
                    break;
            }
            if(isGameStarted && context != MessageFragment.CONTEXT_PHASE){
                this.view.idleShow();
            }
        }
    }

    /**
     * This method changes the phase to the next one
     * @return the next GamePhase
     */
    @Override
    public GamePhase next() {
        return nextPhase;
    }

    /**
     * Method called by Start and SelectFigurePhase to set that the game isn't started yet
     * @param value is false when the game isn't started yet
     */
    public void isGameStarted (boolean value){
        this.isGameStarted = value;
    }
}
