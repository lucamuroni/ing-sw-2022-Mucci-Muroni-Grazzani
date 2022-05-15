package it.polimi.ingsw.controller.server.virtualView;

import it.polimi.ingsw.controller.networking.messageParts.ConnectionTimings;
import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.model.Island;

import java.util.ArrayList;

import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.ISLAND_ID;

public class GetMNLocation {
    private ArrayList<Island> islands;
    private MessageHandler messageHandler;

    public GetMNLocation(ArrayList<Island> islands, MessageHandler messageHandler) {
        this.islands = islands;
        this.messageHandler = messageHandler;
    }

    public Island handle() throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException {
        ArrayList<Message> messages = new ArrayList<Message>();
        int topicId = this.messageHandler.getNewUniqueTopicID();
        for (Island island : this.islands) {
            messages.add(new Message(ISLAND_ID.getFragment(), island.getId().toString(), topicId));
        }
        this.messageHandler.write(messages);
        messages.clear();
        //TODO: modificare
        messages.addAll(this.messageHandler.writeOutAndWait(ConnectionTimings.PLAYER_MOVE.getTiming()));
        int islandId = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(ISLAND_ID.getFragment(), messages));
        Island result = null;
        for (Island island : this.islands) {
            if (island.getId().equals(islandId)) {
                result = island;
            }
        }
        return result;
    }
}
