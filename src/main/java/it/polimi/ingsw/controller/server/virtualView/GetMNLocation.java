package it.polimi.ingsw.controller.server.virtualView;


import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.model.Island;

import java.util.ArrayList;

import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.*;

/**
 * @author Luca Muroni
 * Class that implements the message to get the current motherNature position
 */
public class GetMNLocation {
    ArrayList<Island> islands;
    MessageHandler messageHandler;

    /**
     * Constructor of the class
     * @param islands represents the available islands
     * @param messageHandler is the handler of messages
     */
    public GetMNLocation(ArrayList<Island> islands, MessageHandler messageHandler) {
        this.islands = islands;
        this.messageHandler = messageHandler;
    }

    /**
     * Method that handles the exchange of messages
     * @return the chosen island
     * @throws ClientDisconnectedException when the player disconnects from the game
     * @throws MalformedMessageException when a received message isn't correct
     */
    public Island handle() throws ClientDisconnectedException, MalformedMessageException {
        int topicId = this.messageHandler.getNewUniqueTopicID();
        int size = this.islands.size();
        Message message = new Message(PAYLOAD_SIZE.getFragment(), String.valueOf(size), topicId);
        this.messageHandler.write(message);
        this.messageHandler.writeOut();
        for (Island island : this.islands) {
            message = new Message(ISLAND_ID.getFragment(), island.getId().toString(), topicId);
            this.messageHandler.write(message);
            this.messageHandler.writeOut();
        }
        this.messageHandler.read();
        if (!(this.messageHandler.getMessagesUniqueTopic() == topicId)) {
            throw new MalformedMessageException();
        }
        int islandId = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(MN_LOCATION.getFragment()));
        Island result = null;
        for (Island island : this.islands) {
            if (island.getId().equals(islandId)) {
                result = island;
            }
        }
        return result;
    }
}