package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import java.util.ArrayList;

/**
 * @author Sara Mucci
 * Class that implements the message to send the new location of the moved student
 */
public class SendLocation {
    int location;
    MessageHandler messageHandler;

    /**
     * Class constructor
     * @param location represents the new student's location
     * @param messageHandler represents the messageHandles used for the message
     */
    public SendLocation(int location, MessageHandler messageHandler) {
        this.location = location;
        this.messageHandler = messageHandler;
    }

    public void handle() {
        ArrayList<Message> messages = new ArrayList<Message>();
        int topicId = this.messageHandler.getNewUniqueTopicID();
    }
}
