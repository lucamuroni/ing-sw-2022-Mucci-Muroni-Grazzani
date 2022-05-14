package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.Player;
import java.util.ArrayList;

/**
 * @author Sara Mucci
 * Class that implements the message to get the new owner of the island mother nature is on
 */
public class GetNewOwner {
    Player player;
    MessageHandler messageHandler;

    /**
     * Class constructor
     * @param messageHandler represents the messageHandler used for the message
     */
    public GetNewOwner(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    public Player handle() {
        ArrayList<Message> messages = new ArrayList<Message>();
        int topicId = this.messageHandler.getNewUniqueTopicID();
    }
}
