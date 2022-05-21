package it.polimi.ingsw.controller.server.virtualView;

import it.polimi.ingsw.controller.networking.ConnectionTimings;
import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.model.Island;
import java.util.ArrayList;

import static it.polimi.ingsw.controller.networking.MessageFragment.*;

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
        for (Island island : this.islands) {
            Message message = new Message(ISLAND_ID.getFragment(), island.getId().toString(), topicId);
            this.messageHandler.write(message);
        }
        this.messageHandler.write(new Message(STOP.getFragment(), "", topicId));
        this.messageHandler.read(ConnectionTimings.PLAYER_MOVE.getTiming());
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
