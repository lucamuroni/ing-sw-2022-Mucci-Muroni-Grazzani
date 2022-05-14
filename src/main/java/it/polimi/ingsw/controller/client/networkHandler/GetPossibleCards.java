package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.model.AssistantCard;
import java.util.ArrayList;

/**
 * @author Sara Mucci
 * Class that implements the message to get the possible assistant cards
 */
public class GetPossibleCards {
    MessageHandler messageHandler;

    /**
     * Class constructor
     * @param messageHandler represents the messageHandler used for the message
     */
    public GetPossibleCards(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    public ArrayList<AssistantCard> handle() {
        ArrayList<Message> messages = new ArrayList<Message>();
        int topicId = this.messageHandler.getNewUniqueTopicID();

    }
}
