package it.polimi.ingsw.controller.server.virtualView;

import it.polimi.ingsw.controller.networking.ConnectionTimings;
import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.model.Island;

import java.util.ArrayList;

import static it.polimi.ingsw.controller.networking.MessageFragment.ISLAND_ID;
import static it.polimi.ingsw.controller.networking.MessageFragment.STOP;

public class GetMNLocation {
    private ArrayList<Island> islands;
    private MessageHandler messageHandler;

    public GetMNLocation(ArrayList<Island> islands, MessageHandler messageHandler) {
        this.islands = islands;
        this.messageHandler = messageHandler;
    }

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
        int islandId = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(ISLAND_ID.getFragment()));
        Island result = null;
        for (Island island : this.islands) {
            if (island.getId().equals(islandId)) {
                result = island;
            }
        }
        return result;
    }
}
