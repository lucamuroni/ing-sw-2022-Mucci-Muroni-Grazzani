package it.polimi.ingsw.controller.client.game;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.networkHandler.Network;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;

import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.view.ViewHandler;
import it.polimi.ingsw.view.asset.exception.AssetErrorException;
import it.polimi.ingsw.view.asset.game.Game;

public class PlanningPhase implements GamePhase {
    private final Game game;
    private final ViewHandler view;
    private final Network network;
    private final ClientController controller;
    private boolean initView;

    public PlanningPhase(ClientController controller) {
        this.controller = controller;
        this.game = controller.getGame();
        this.view = controller.getViewHandler();
        this.network = this.controller.getNetwork();
        this.setInitView(false);
    }

    @Override
    public void handle() {
        if(this.initView){
            this.view.init();
        }
        try {
            try {
                this.network.getPossibleCards(this.game);
            } catch (MalformedMessageException e) {
                this.network.getPossibleCards(this.game);
            }
        } catch (MalformedMessageException | ClientDisconnectedException e) {
            this.controller.handleError();
        } catch (AssetErrorException e) {
            this.controller.handleError("Doesn't found card");
        }
        AssistantCard chosenCard = this.view.selectCard(this.game.getSelf().getCards());
        try {
            try {
                this.network.sendCard(chosenCard);
            } catch (MalformedMessageException e) {
                this.network.sendCard(chosenCard);
            }
        } catch (MalformedMessageException | FlowErrorException e) {
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


    @Override
    public GamePhase next() {
        return new Idle(this.controller);
    }

    public void setInitView(boolean bool){
        this.initView = bool;
    }
}
