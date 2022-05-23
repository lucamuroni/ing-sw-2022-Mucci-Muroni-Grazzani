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
            } catch (MalformedMessageException | TimeHasEndedException e) {
                this.network.getPossibleCards(this.game);
            }
        } catch (MalformedMessageException | TimeHasEndedException | ClientDisconnectedException e) {
            //TODO: metodo per gestire errore
        }
        AssistantCard chosenCard;
        //TODO: qui lascio già i try in modo tale da poter gestire la view
        try {
            try {
                chosenCard = this.view.selectCard(this.game.getSelf().getCards());
            } catch () {
                chosenCard = this.view.selectCard(this.game.getSelf().getCards());
            }
        } catch () {

        }

        try {
            try {
                this.network.sendCard(chosenCard);
            } catch (MalformedMessageException | TimeHasEndedException | FlowErrorException e) {
                this.network.sendCard(chosenCard);
            }
        } catch (MalformedMessageException | TimeHasEndedException | FlowErrorException | ClientDisconnectedException e) {
            //TODO: metodo per gestire errore
        }
    }

    public void updateCard() {
        try {
            try {
                this.network.getChosenAssistantCard(this.game);
            } catch (MalformedMessageException | TimeHasEndedException e) {
                this.network.getChosenAssistantCard(this.game);
            }
        } catch (MalformedMessageException | TimeHasEndedException | ClientDisconnectedException e) {
            //TODO: metodo per gestire errore
        }
    }

    public void updateClouds() {

            try {
                try {
                    this.network.getCloudStatus(this.game);
                } catch (MalformedMessageException | TimeHasEndedException e) {
                    this.network.getCloudStatus(this.game);
                }
            } catch (MalformedMessageException | TimeHasEndedException | ClientDisconnectedException e) {
                //TODO: metodo per gestire errore
            }

    }

    @Override
    public Phase getNamePhase() {
        return name;
    }

    @Override
    public GamePhase next() {
        return new Idle(this.game, this.controller, this.view);
    }
}
