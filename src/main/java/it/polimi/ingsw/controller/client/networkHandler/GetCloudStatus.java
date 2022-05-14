package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import java.util.ArrayList;

/**
 * @author Sara Mucci
 * Class that updates the clouds status in the view
 */
public class GetCloudStatus {
    MessageHandler messageHandler;

    public GetCloudStatus(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    public void handle() {
        ArrayList<Message> messages = new ArrayList<Message>();
        int topicId = this.messageHandler.getNewUniqueTopicID();
    }
}
