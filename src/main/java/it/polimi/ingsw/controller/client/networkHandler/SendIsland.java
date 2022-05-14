package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.model.Island;
import java.util.ArrayList;

/**
 * @author Sara Mucci
 * Class that implements the message to send the new mother nature's location
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

    public void handle() {
        ArrayList<Message> messages = new ArrayList<Message>();
        int topicId = this.messageHandler.getNewUniqueTopicID();
    }
}
