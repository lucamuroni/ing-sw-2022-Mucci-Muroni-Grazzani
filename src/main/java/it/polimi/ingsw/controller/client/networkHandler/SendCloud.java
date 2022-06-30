package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.view.asset.game.Cloud;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.CLOUD_ID;

/**
 * @author Sara Mucci
 * Class that implements the message to send the chosen cloud
 */
public class SendCloud {
    Cloud cloud;
    MessageHandler messageHandler;

    /**
     * Constructor of the class
     * @param messageHandler is the handler of messages
     * @param cloud represents the chosen cloud
     */
    public SendCloud(Cloud cloud, MessageHandler messageHandler) {
        this.cloud = cloud;
        this.messageHandler = messageHandler;
    }

    /**
     * MMethod that handles the exchange of messages
     * @throws FlowErrorException when there is an error in the synchronization
     * @throws MalformedMessageException when a received message isn't correct
     */
    public void handle() throws FlowErrorException, MalformedMessageException {
        int topicId = this.messageHandler.getMessagesUniqueTopic();
        Message message = new Message(CLOUD_ID.getFragment(), Integer.toString(cloud.getId()), topicId);
        this.messageHandler.write(message);
        this.messageHandler.writeOut();
    }
}