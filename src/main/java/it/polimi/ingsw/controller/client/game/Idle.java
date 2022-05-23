package it.polimi.ingsw.controller.client.game;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.networkHandler.Network;
import it.polimi.ingsw.controller.networking.Phase;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.view.ViewHandler;
import it.polimi.ingsw.view.asset.game.Game;

public class Idle implements GamePhase{
    private Phase name;
    private Network network;
    private ClientController controller;
    private ViewHandler view;
    private Game game;

    public Idle(Game game, ClientController controller, ViewHandler view) {
        this.game = game;
        this.controller = controller;
        this.network = this.controller.getNetwork();
        this.view = view;
    }

    @Override
    public void handle() {
        try {
            try {
                this.network.getPhase();
            } catch (MalformedMessageException | TimeHasEndedException e) {
                this.network.getPhase();
            }
        } catch (MalformedMessageException | TimeHasEndedException | ClientDisconnectedException e) {
            //TODO: bisogna aggiungere metodo per gestire errore come nel server
        }

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
