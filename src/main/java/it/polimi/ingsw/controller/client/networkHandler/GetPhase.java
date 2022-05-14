package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import java.util.ArrayList;

/**
 * @author Sara Mucci
 * Class that imlements the message to get the new phase
 */
public class GetPhase {
    Phase phase;
    MessageHandler messageHandler;

    /**
     * Class constructor
     * @param phase represents the new phase
     * @param messageHandler represents the messageHandler used for the message
     */
    public GetPhase(Phase phase, MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
        this.phase = phase;
    }

    public Phase handle() {
        ArrayList<Message> messages = new ArrayList<Message>();
        int topicId = this.messageHandler.getNewUniqueTopicID();

    }
}
