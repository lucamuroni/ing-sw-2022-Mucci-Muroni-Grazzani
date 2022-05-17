package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.messageParts.ConnectionTimings;
import it.polimi.ingsw.model.Cloud;
import java.util.ArrayList;

import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.CLOUD;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.OK;

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

    public void handle() throws MalformedMessageException, FlowErrorException {
        ArrayList<Message> messages = new ArrayList<Message>();
        int topicId = this.messageHandler.getNewUniqueTopicID();
        messages.add(new Message(CLOUD.getFragment(), cloud.getId(), topicId));
        this.messageHandler.write(messages);
        messages.clear();
        this.messageHandler.writeOutAndWait(ConnectionTimings.RESPONSE.getTiming());
        this.messageHandler.assertOnEquals(OK.getFragment(), CLOUD.getFragment());
    }
}
