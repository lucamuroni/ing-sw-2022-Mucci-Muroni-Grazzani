package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.controller.networking.messageParts.ConnectionTimings;
import it.polimi.ingsw.view.asset.game.Island;
import java.util.ArrayList;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.ISLAND_ID;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.OK;

/**
 * @author Sara Mucci
 * Class that implements the message to send the new mother nature's location
 */
public class SendIsland {
    Island island;
    MessageHandler messageHandler;

    /**
     * Class constructor
     * @param island represents the new mother nature's location
     * @param messageHandler represents the messageHandler used for the messageù
     */
    public SendIsland(Island island, MessageHandler messageHandler) {
        this.island = island;
        this.messageHandler = messageHandler;
    }

    /**
     * Method that handles the messages to send the chosen island
     * @throws MalformedMessageException launched if the message isn't created the correct way
     * @throws FlowErrorException launched when the client sends an unexpected response
     * @throws TimeHasEndedException launched when the available time for the response has ended
     */
    public void handle() throws MalformedMessageException, FlowErrorException, TimeHasEndedException {
        int topicId = this.messageHandler.getMessagesUniqueTopic();
        Message message = new Message(ISLAND_ID.getFragment(), Integer.toString(island.getId()), topicId);
        this.messageHandler.write(message);
        this.messageHandler.writeOut();
    }
}
