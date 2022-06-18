package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.view.ViewHandler;
import it.polimi.ingsw.view.asset.exception.AssetErrorException;
import it.polimi.ingsw.view.asset.game.Game;
import it.polimi.ingsw.view.asset.game.Island;

import java.util.ArrayList;

import static it.polimi.ingsw.controller.networking.messageParts.ConnectionTimings.PLAYER_MOVE;
import static it.polimi.ingsw.controller.networking.messageParts.ConnectionTimings.UPDATE;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.*;

public class GetMergedIslands {
    private MessageHandler messageHandler;
    private ArrayList<Island> mergedIslands;
    private ViewHandler viewHandler;

    public GetMergedIslands(MessageHandler messageHandler, ViewHandler viewHandler) {
        this.messageHandler = messageHandler;
        this.mergedIslands = new ArrayList<>();
        this.viewHandler = viewHandler;
    }

    public void handle() throws ClientDisconnectedException, MalformedMessageException, AssetErrorException {
        this.messageHandler.read();
        int islandId1 = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(MERGED_ISLAND_1.getFragment()));
        int islandId2 = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(MERGED_ISLAND_2.getFragment()));
        Message message = new Message(OK.getFragment(), ISLAND.getFragment(), this.messageHandler.getMessagesUniqueTopic());
        this.messageHandler.write(message);
        this.messageHandler.writeOut();
        viewHandler.setMergedIsland(islandId1,islandId2);
    }
}
