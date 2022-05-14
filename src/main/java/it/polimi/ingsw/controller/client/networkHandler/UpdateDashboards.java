package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import java.util.ArrayList;

/**
 * @author Sara Mucci
 * Class that updates the dashboards in the view
 */
public class UpdateDashboards {
    MessageHandler messageHandler;

    public UpdateDashboards(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    public void handle() {
        ArrayList<Message> messages = new ArrayList<Message>();
        int topicId = this.messageHandler.getNewUniqueTopicID();
    }
}
