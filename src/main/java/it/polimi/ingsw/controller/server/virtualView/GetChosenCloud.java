package it.polimi.ingsw.controller.server.virtualView;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.model.Cloud;
import java.util.ArrayList;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.*;


/**
 * @author Luca Muroni
 * @author Sara Mucci
 * Class that implements the message to get the cloud chosen by the current player
 */
public class GetChosenCloud {
    ArrayList<Cloud> clouds;
    MessageHandler messageHandler;

    /**
     * Constructor of the class
     * @param clouds represents the available clouds
     * @param messageHandler is the handler of messages
     */
    public GetChosenCloud(ArrayList<Cloud> clouds, MessageHandler messageHandler) {
        this.clouds = clouds;
        this.messageHandler = messageHandler;
    }

    /**
     * Method that handles the exchange of messages
     * @return the cloud chosen by the player
     * @throws ClientDisconnectedException when the player disconnects from the game
     * @throws MalformedMessageException when a received message isn't correct
     */
    public Cloud handle() throws MalformedMessageException, ClientDisconnectedException {
        int topicId = this.messageHandler.getNewUniqueTopicID();
        int size = this.clouds.size();
        Message message = new Message(PAYLOAD_SIZE.getFragment(), String.valueOf(size),topicId);
        this.messageHandler.write(message);
        this.messageHandler.writeOut();
        for (Cloud cloud: clouds) {
            message = new Message(CLOUD_ID.getFragment(), cloud.getID().toString(), topicId);
            this.messageHandler.write(message);
            this.messageHandler.writeOut();
        }
        this.messageHandler.read();
        if (!(this.messageHandler.getMessagesUniqueTopic() == topicId)) {
            throw new MalformedMessageException();
        }
        Cloud result = null;
        int cloudID = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(CLOUD_ID.getFragment()));
        for (Cloud cloud : this.clouds) {
            if (cloud.getID() == cloudID)
                result = cloud;
        }
        if (result == null)
            throw new MalformedMessageException();
        return result;
    }
}