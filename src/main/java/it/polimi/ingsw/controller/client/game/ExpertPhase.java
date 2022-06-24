package it.polimi.ingsw.controller.client.game;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.networkHandler.Network;
import it.polimi.ingsw.view.ViewHandler;
import it.polimi.ingsw.view.asset.game.Game;

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
        //TODO view.askToPlayExpertCard;
        //TODO invio della risposta
        //TODO se si
        //aspetto le carte che posso giocare
        //view.choseCharacterCard
        //network.sendCharacterCard
        //TODO se no esci
    }

    @Override
    public GamePhase next() {
        return null;
    }
}
