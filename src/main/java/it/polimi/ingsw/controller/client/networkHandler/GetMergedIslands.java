package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.view.ViewHandler;
import it.polimi.ingsw.view.asset.exception.AssetErrorException;

import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.*;

/**
 * @author Luca Muroni
 * @author Davide Grazzani
 * This class is used to get the islands that have been merged in ConquerIslandPhase (server)
 */
public class GetMergedIslands {
    private final MessageHandler messageHandler;
    private final ViewHandler viewHandler;

    /**
     * Constructor of the class
     * @param messageHandler is the handler of messages
     * @param viewHandler is the view of the player
     */
    public GetMergedIslands(MessageHandler messageHandler, ViewHandler viewHandler) {
        this.messageHandler = messageHandler;
        this.viewHandler = viewHandler;
    }

    /**
     * Method that handles the exchange of messages
     * @throws AssetErrorException when an object of the game isn't found in the asset
     * @throws MalformedMessageException when a received message isn't correct
     * @throws ClientDisconnectedException when the player disconnects from the game
     */
    public void handle() throws AssetErrorException, MalformedMessageException, ClientDisconnectedException {
        this.messageHandler.read();
        int islandId1 = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(MERGED_ISLAND_1.getFragment()));
        int islandId2 = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(MERGED_ISLAND_2.getFragment()));
        Message message = new Message(ISLAND.getFragment(), OK.getFragment(), this.messageHandler.getMessagesUniqueTopic());
        this.messageHandler.write(message);
        this.messageHandler.writeOut();
        viewHandler.setMergedIsland(islandId1,islandId2);
    }
}