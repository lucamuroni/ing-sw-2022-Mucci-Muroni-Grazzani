package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
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
    private Game game;

    public GetMergedIslands(MessageHandler messageHandler, Game game) {
        this.messageHandler = messageHandler;
        this.mergedIslands = new ArrayList<>();
        this.game = game;
    }

    public void handle() throws TimeHasEndedException, ClientDisconnectedException, MalformedMessageException, AssetErrorException {
        this.messageHandler.read(UPDATE.getTiming());
        int num = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(PAYLOAD_SIZE.getFragment()));
        for (int i = 0; i<num; i++) {
            this.messageHandler.read(UPDATE.getTiming());
            int id = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(ISLAND_ID.getFragment()));
            boolean check = false;
            for (Island island : this.game.getIslands()) {
                if (island.getId() == id) {
                    this.mergedIslands.add(island);
                    check = true;
                }
            }
            if (!check)
                throw new AssetErrorException();
        }
        Message message = new Message(OK.getFragment(), ISLAND.getFragment(), this.messageHandler.getMessagesUniqueTopic());
        this.messageHandler.write(message);
        this.messageHandler.writeOut();
        //TODO: fare il merge
    }
}
