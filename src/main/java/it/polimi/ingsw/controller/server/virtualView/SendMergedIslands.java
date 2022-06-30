package it.polimi.ingsw.controller.server.virtualView;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.model.Island;

import java.util.ArrayList;

import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.*;

/**
 * @author Luca Muroni
 * This class is used to send to a player two island that have merged
 */
public class SendMergedIslands {
    private final MessageHandler messageHandler;
    private final ArrayList<Island> mergedIslands;

    /**
     * Constructor of the class
     * @param messageHandler is the handler of messages
     * @param mergedIslands is the arrayList containing the island to send
     */
    public SendMergedIslands(MessageHandler messageHandler, ArrayList<Island> mergedIslands) {
        this.messageHandler = messageHandler;
        this.mergedIslands = new ArrayList<>(mergedIslands);
    }

    /**
     * Method that handles the exchange of messages
     * @throws ClientDisconnectedException when the player disconnects from the game
     * @throws FlowErrorException when there is an error in the synchronization
     * @throws MalformedMessageException when a received message isn't correct
     */
    public void handle() throws ClientDisconnectedException, FlowErrorException, MalformedMessageException {
        int topicId = this.messageHandler.getNewUniqueTopicID();
        ArrayList<Message> messages = new ArrayList<>();
        messages.add(new Message(MERGED_ISLAND_1.getFragment(),String.valueOf(this.mergedIslands.get(0).getId()),topicId));
        messages.add(new Message(MERGED_ISLAND_2.getFragment(),String.valueOf(this.mergedIslands.get(1).getId()),topicId));
        this.messageHandler.write(messages);
        this.messageHandler.writeOutAndWait();
        this.messageHandler.assertOnEquals(OK.getFragment(), ISLAND.getFragment());
    }
}