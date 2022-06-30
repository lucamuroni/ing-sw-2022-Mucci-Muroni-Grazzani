package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.view.asset.game.Island;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.*;

/**
 * @author Sara Mucci
 * Class that implements the message to send mother nature's new location
 */
public class SendIsland {
    Island island;
    MessageHandler messageHandler;

    /**
     * Constructor of the class
     * @param island represents the location of motherNature
     * @param messageHandler is the handler of messages
     */
    public SendIsland(Island island, MessageHandler messageHandler) {
        this.island = island;
        this.messageHandler = messageHandler;
    }

    /**
     * Method that handles the exchange of messages
     * @throws MalformedMessageException when a received message isn't correct
     * @throws FlowErrorException when there is an error in the synchronization
     */
    public void handle() throws MalformedMessageException, FlowErrorException {
        int topicId = this.messageHandler.getMessagesUniqueTopic();
        Message message = new Message(MN_LOCATION.getFragment(), Integer.toString(island.getId()), topicId);
        this.messageHandler.write(message);
        this.messageHandler.writeOut();
    }
}