package it.polimi.ingsw.controller.client.game;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.networkHandler.Network;
import it.polimi.ingsw.controller.networking.Phase;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.view.ViewHandler;
import it.polimi.ingsw.view.asset.game.Cloud;
import it.polimi.ingsw.view.asset.game.Game;

import java.lang.reflect.Array;

public class PlanningPhase implements GamePhase {
    private final Phase name = Phase.PLANNINGPHASE;
    private final Game game;
    private final ViewHandler view;
    private final Network network;
    private final ClientController controller;

    public PlanningPhase(Game game, ClientController controller, ViewHandler view) {
        this.game = game;
        this.view = view;
        this.controller = controller;
        this.network = this.controller.getNetwork();
    }

    @Override
    public void handle() {
        for (Cloud cloud : this.game.getClouds()) {
            this.updateClouds();
        }
        try {
            try {
                this.network.getPossibleCards(this.game);
            } catch (MalformedMessageException e) {
                this.network.getPossibleCards(this.game);
            }
        } catch (MalformedMessageException | TimeHasEndedException | ClientDisconnectedException e) {
            this.controller.handleError();
        }
        AssistantCard chosenCard = this.view.selectCard(this.game.getSelf().getCards());
        try {
            try {
                this.network.sendCard(chosenCard);
            } catch (MalformedMessageException e) {
                this.network.sendCard(chosenCard);
            }
        } catch (MalformedMessageException | TimeHasEndedException | FlowErrorException e) {
            this.controller.handleError();
        }
    }

    //Metodo che si userà in Idle
    /*
    public void updateCard() {
        try {
            try {
                this.network.getChosenAssistantCard(this.game);
            } catch (MalformedMessageException  e) {
                this.network.getChosenAssistantCard(this.game);
            }
        } catch (MalformedMessageException | TimeHasEndedException | ClientDisconnectedException e) {
            this.controller.handleError();
        }
    }*/

    private void updateClouds() {
        try {
            try {
                this.network.getCloudStatus(this.game);
            } catch (MalformedMessageException | TimeHasEndedException e) {
                this.network.getCloudStatus(this.game);
            }
        } catch (MalformedMessageException | TimeHasEndedException | ClientDisconnectedException e) {
            this.controller.handleError();
        }

    }

    @Override
    public Phase getPhase() {
        return name;
    }

    @Override
    public GamePhase next() {
        return new Idle(this.game, this.controller, this.view);
    }
}
