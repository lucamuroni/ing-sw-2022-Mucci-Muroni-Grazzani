package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.view.asset.game.Island;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.*;

/**
 * @author Sara Mucci
 * Class that implements the message to send the mother nature's new  location
 */
public class SendIsland {
    Island island;
    MessageHandler messageHandler;

    /**
     * Class constructor
     * @param island represents the new mother nature's location
     * @param messageHandler represents the messageHandler used for the message
     */
    public SendIsland(Island island, MessageHandler messageHandler) {
        this.island = island;
        this.messageHandler = messageHandler;
    }

    /**
     * Method that handles the messages to send the chosen island
     * @throws MalformedMessageException launched if the message isn't created the correct way
     * @throws FlowErrorException launched when the client sends an unexpected response
     */
    public void handle() throws MalformedMessageException, FlowErrorException {
        int topicId = this.messageHandler.getMessagesUniqueTopic();
        Message message = new Message(MN_LOCATION.getFragment(), Integer.toString(island.getId()), topicId);
        this.messageHandler.write(message);
        this.messageHandler.writeOut();
    }
}

