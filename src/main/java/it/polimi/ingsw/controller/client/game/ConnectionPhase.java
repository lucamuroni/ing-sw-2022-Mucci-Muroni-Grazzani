package it.polimi.ingsw.controller.client.game;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.networkHandler.Network;
import it.polimi.ingsw.controller.networking.Phase;
import it.polimi.ingsw.view.ViewHandler;
import it.polimi.ingsw.view.asset.game.Game;
import it.polimi.ingsw.view.asset.game.Gamer;

public class ConnectionPhase implements GamePhase{
    private Game game;
    private Gamer gamer;
    private ClientController controller;
    private ViewHandler view;
    private Network network;

    public ConnectionPhase(ClientController controller,ViewHandler viewHandler){
        this.controller = controller;
        this.view = viewHandler;
        this.network = this.controller.getNetwork();
    }

    @Override
    public void handle() {
        this.view.displayLoginPage();
        this.network.getConnection();
        this.view.getPlayerInfo();
        this.network.broadcastPlayerInfo();
    }

    @Override
    public GamePhase next() {
        return new Start(this.game,this.controller,this.view);
    }

}
