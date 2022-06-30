package it.polimi.ingsw.controller.client.game;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.networkHandler.Network;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.model.expert.CharacterCard;
import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.view.ViewHandler;
import it.polimi.ingsw.view.asset.exception.AssetErrorException;
import it.polimi.ingsw.view.asset.game.Game;
import it.polimi.ingsw.view.asset.game.Island;

import java.util.ArrayList;

/**
 * @author Luca Muroni
 * @author Davide Grazzani
 * This class implements the expert phase of an expertGame, where the currentPlayer can choose to play a card and, if the
 * answer is true, which one to play
 */
public class ExpertPhase implements GamePhase{
    private final Game game;
    private final ViewHandler view;
    private final Network network;
    private final ClientController controller;

    /**
     * Constructor of the class
     * @param controller is the controller of client side
     */
    public ExpertPhase(ClientController controller){
        this.controller = controller;
        this.network = controller.getNetwork();
        this.view = controller.getViewHandler();
        this.game = controller.getGame();
    }

    /**
     * This is the main method that handles the ExpertPhase
     */
    @Override
    public void handle() {
        boolean answer = this.view.askToPlayExpertCard();
        try {
            try {
                this.network.sendAnswer(answer);
            } catch (MalformedMessageException e) {
                this.network.sendAnswer(answer);
            }
        } catch (MalformedMessageException | FlowErrorException | ClientDisconnectedException e) {
            this.controller.handleError();
        }
        if (answer) {
            ArrayList<CharacterCard> cards = new ArrayList<>();
            try {
                try {
                    cards.addAll(this.network.getPossibleCharacters(this.game));
                } catch (MalformedMessageException e) {
                    cards.addAll(this.network.getPossibleCharacters(this.game));
                }
            } catch (MalformedMessageException | ClientDisconnectedException e) {
                this.controller.handleError();
            } catch (AssetErrorException e) {
                this.controller.handleError("Doesn't found character cards");
            }
            CharacterCard card = this.view.choseCharacterCard(cards);
            try {
                try {
                    this.network.sendCharacterCard(card);
                } catch (MalformedMessageException e) {
                    this.network.sendCharacterCard(card);
                }
            } catch (MalformedMessageException e) {
                this.controller.handleError();
            }
            if(this.game.getSelf().getCurrentExpertCardSelection() == null){
                return;
            }
            switch (this.game.getSelf().getCurrentExpertCardSelection()) {
                case AMBASSADOR -> {
                    Island island = this.view.chooseIsland(this.game.getIslands(), true);
                    int ind = island.getId();
                    try {
                        try {
                            this.network.sendLocation(ind);
                        } catch (MalformedMessageException e) {
                            this.network.sendLocation(ind);
                        }
                    } catch (MalformedMessageException | FlowErrorException | ClientDisconnectedException e) {
                        this.controller.handleError();
                    }
                }
                case BARD -> {
                    ArrayList<PawnColor> students = this.view.choseStudentsToMove();
                    try {
                        try {
                            this.network.sendChosenColors(students);
                        } catch (MalformedMessageException e) {
                            this.network.sendChosenColors(students);
                        }
                    } catch (MalformedMessageException | FlowErrorException | ClientDisconnectedException e) {
                        this.controller.handleError();
                    }
                }
                case MERCHANT, THIEF -> {
                    PawnColor color = this.view.chooseColor(this.game.getSelf().getCurrentExpertCardSelection().getName());
                    try {
                        try {
                            this.network.sendColor(color);
                        } catch (MalformedMessageException e) {
                            this.network.sendColor(color);
                        }
                    } catch (MalformedMessageException | FlowErrorException | ClientDisconnectedException e) {
                        this.controller.handleError();
                    }
                }
            }
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
