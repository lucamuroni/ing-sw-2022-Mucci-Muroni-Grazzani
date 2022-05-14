package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.model.Cloud;
import java.util.ArrayList;

/**
 * @author Sara Mucci
 * Class that implements the message to send the chosen cloud
 */
public class SendCloud {
    Cloud cloud;
    MessageHandler messageHandler;

    /**
     * Class constructor
     * @param cloud represents the chosen cloud
     * @param messageHandler represents the messageHandler used for the message
     */
    public SendCloud(Cloud cloud, MessageHandler messageHandler) {
        this.cloud = cloud;
        this.messageHandler = messageHandler;
    }

    public void handle() {
        ArrayList<Message> messages = new ArrayList<Message>();
        int topicId = this.messageHandler.getNewUniqueTopicID();
    }
}
