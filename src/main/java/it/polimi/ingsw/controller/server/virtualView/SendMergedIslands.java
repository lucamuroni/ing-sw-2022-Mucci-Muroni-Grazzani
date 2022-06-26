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
        ArrayList<Message> messages = new ArrayList<>();
        messages.add(new Message(MERGED_ISLAND_1.getFragment(),String.valueOf(this.mergedIslands.get(0).getId()),topicId));
        messages.add(new Message(MERGED_ISLAND_2.getFragment(),String.valueOf(this.mergedIslands.get(1).getId()),topicId));
        this.messageHandler.write(messages);
        this.messageHandler.writeOutAndWait();
        this.messageHandler.assertOnEquals(OK.getFragment(), ISLAND.getFragment());
    }
}
