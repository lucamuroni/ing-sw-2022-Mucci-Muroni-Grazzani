package it.polimi.ingsw.controller.server.virtualView;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.messageParts.ConnectionTimings;
import it.polimi.ingsw.model.Island;

import java.util.ArrayList;

import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.*;

public class SendMergedIslands {
    private MessageHandler messageHandler;
    private ArrayList<Island> mergedIslands;

    public SendMergedIslands(MessageHandler messageHandler, ArrayList<Island> mergedIslands) {
        this.messageHandler = messageHandler;
        this.mergedIslands = new ArrayList<>(mergedIslands);
    }

    public void handle() throws MalformedMessageException, ClientDisconnectedException, FlowErrorException {
        int topicId = this.messageHandler.getNewUniqueTopicID();
        int size = this.mergedIslands.size();
        Message message = new Message(PAYLOAD_SIZE.getFragment(), String.valueOf(size), topicId);
        this.messageHandler.write(message);
        this.messageHandler.writeOut();
        for (Island island : this.mergedIslands) {
            message = new Message(ISLAND_ID.getFragment(), String.valueOf(island.getId()), topicId);
            this.messageHandler.write(message);
            this.messageHandler.writeOut();
        }
        this.messageHandler.read();
        if (!(this.messageHandler.getMessagesUniqueTopic() == topicId)) {
            throw new MalformedMessageException();
        }
        this.messageHandler.assertOnEquals(OK.getFragment(), ISLAND.getFragment());
    }
}
