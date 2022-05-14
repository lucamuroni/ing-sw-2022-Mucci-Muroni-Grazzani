package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.model.AssistantCard;
import java.util.ArrayList;

/**
 * @author Sara Mucci
 * Class that implements the message to send the chosen card
 */
public class SendCard {
    AssistantCard card;
    MessageHandler messageHandler;

    /**
     * Class constructor
     * @param card represents the chosen card
     * @param messageHandler represents the messageHandler used for the message
     */
    public SendCard(AssistantCard card, MessageHandler messageHandler) {
        this.card = card;
        this.messageHandler = messageHandler;
    }

    public void handle() {
        ArrayList<Message> messages = new ArrayList<Message>();
        int topicId = this.messageHandler.getNewUniqueTopicID();

    }
}
