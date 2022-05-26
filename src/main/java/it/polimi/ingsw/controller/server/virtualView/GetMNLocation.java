package it.polimi.ingsw.controller.server.virtualView;

import it.polimi.ingsw.controller.networking.messageParts.ConnectionTimings;
import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.model.Island;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.*;
import java.util.ArrayList;

/**
 * @author Luca Muroni
 * Class that implements the message to get the current mother nature position
 */
public class GetMNLocation {
    ArrayList<Island> islands;
    MessageHandler messageHandler;

    /**
     * Class constructor
     * @param islands represents the available islands
     * @param messageHandler represents the messageHandler used for the message
     */
    public GetMNLocation(ArrayList<Island> islands, MessageHandler messageHandler) {
        this.islands = islands;
        this.messageHandler = messageHandler;
    }

    /**
     * Method that handles the message exchange
     * @return the chosen island
     * @throws MalformedMessageException launched if the message isn't created in the correct way
     * @throws TimeHasEndedException launched when the available time for the response has ended
     * @throws ClientDisconnectedException launched if the client disconnects from the game
     */
    public Island handle() throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException {
        int topicId = this.messageHandler.getNewUniqueTopicID();
        int size = this.islands.size();
        Message message = new Message(PAYLOAD_SIZE.getFragment(), String.valueOf(size),topicId);
        this.messageHandler.write(message);
        this.messageHandler.writeOut();
        for (Island island : this.islands) {
            message = new Message(ISLAND_ID.getFragment(), island.getId().toString(), topicId);
            this.messageHandler.write(message);
            this.messageHandler.writeOut();
        }
        this.messageHandler.read(ConnectionTimings.PLAYER_MOVE.getTiming());
        if (!(this.messageHandler.getMessagesUniqueTopic() == topicId)) {
            throw new MalformedMessageException();
        }
        Island result = null;
        int islandId = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(MN_LOCATION.getFragment()));
        for (Island island : this.islands) {
            if (island.getId() == islandId)
                result = island;
        }
        return result;
    }
}
