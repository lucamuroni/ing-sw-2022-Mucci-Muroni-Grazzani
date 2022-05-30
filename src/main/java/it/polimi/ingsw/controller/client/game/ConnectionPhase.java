package it.polimi.ingsw.controller.client.game;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.networkHandler.Network;
import it.polimi.ingsw.controller.networking.Phase;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.view.ViewHandler;
import it.polimi.ingsw.view.asset.game.Game;
import it.polimi.ingsw.view.asset.game.Gamer;

public class ConnectionPhase implements GamePhase{
    private Game game;
    private Gamer gamer;
    private ClientController controller;
    private ViewHandler view;
    private Network network;

    public ConnectionPhase(ClientController controller){
        this.controller = controller;
        this.view = this.controller.getViewHandler();
        this.network = this.controller.getNetwork();
    }

    @Override
    public void handle() {
        this.view.displayLoginPage();
        try{
            try{
                this.network.getConnection(this.controller);
            }catch (MalformedMessageException e){
                this.network.getConnection(this.controller);
            }
        }catch (MalformedMessageException | FlowErrorException | TimeHasEndedException | ClientDisconnectedException e){
            this.controller.handleError();
        }
        
        //TODO controllo che posso avanzare dalla cli
        this.view.getPlayerInfo();
        this.network.broadcastPlayerInfo(this.controller.getGame());
    }

    @Override
    public GamePhase next() {
        return new Start(this.controller);
    }

}
