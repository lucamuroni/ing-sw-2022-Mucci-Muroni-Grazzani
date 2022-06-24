package it.polimi.ingsw.controller.client.game;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.networkHandler.Network;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.model.expert.CharacterCard;
import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.model.pawn.Student;
import it.polimi.ingsw.view.ViewHandler;
import it.polimi.ingsw.view.asset.exception.AssetErrorException;
import it.polimi.ingsw.view.asset.game.Game;
import it.polimi.ingsw.view.asset.game.Island;

import java.util.ArrayList;


public class ExpertPhase implements GamePhase{

    private final Game game;
    private final ViewHandler view;
    private final Network network;
    private final ClientController controller;

    public ExpertPhase(ClientController controller){
        this.controller = controller;
        this.network = controller.getNetwork();
        this.view = controller.getViewHandler();
        this.game = controller.getGame();
    }
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
            ArrayList<CharacterCard> cards = null;
            try {
                try {
                     cards = this.network.getPossibleCharacters(this.game);
                } catch (MalformedMessageException e) {
                     cards = this.network.getPossibleCharacters(this.game);
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
            switch (this.game.getSelf().getCurrentExpertCardSelection()) {
                case AMBASSADOR -> {
                    Island island = this.view.chooseIsland(this.game.getIslands());
                    //int ind = island.getId();
                    int ind = this.game.getIslands().indexOf(island) + 1;
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
                    Student student = this.view.chooseStudentToMove();
                    PawnColor color = student.getColor();
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

    @Override
    public GamePhase next() {
        return new Idle(this.controller);
    }
}
