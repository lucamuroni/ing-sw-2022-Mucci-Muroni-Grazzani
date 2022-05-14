package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.client.game.GamePhase;
import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import java.util.ArrayList;

/**
 * @author Sara Mucci
 * Class that imlements the message to get the new phase
 */
public class GetPhase {
    MessageHandler messageHandler;

    /**
     * Class constructor
     * @param messageHandler represents the messageHandler used for the message
     */
    public GetPhase(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    public GamePhase handle() {
        ArrayList<Message> messages = new ArrayList<Message>();
        int topicId = this.messageHandler.getNewUniqueTopicID();

    }
}
