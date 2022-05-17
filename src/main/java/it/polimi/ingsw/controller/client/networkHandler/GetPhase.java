package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;

import java.util.ArrayList;

import static it.polimi.ingsw.controller.networking.messageParts.ConnectionTimings.PLAYER_MOVE;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.OK;

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

    /**
     * MEthod that handles the messages to get the new phase
     * @return the new phase
     * @throws TimeHasEndedException launched when the available time for the response has ended
     * @throws ClientDisconnectedException launched if the client disconnects from the game
     * @throws MalformedMessageException launched if the message isn't created in the correct way
     */
    public String handle() throws TimeHasEndedException, ClientDisconnectedException, MalformedMessageException {
        ArrayList<Message> messages = new ArrayList<Message>();
        this.messageHandler.read(PLAYER_MOVE.getTiming());
        String string = this.messageHandler.getMessagePayloadFromStream(PHASE.getFragment());
        int topicId = this.messageHandler.getMessagesUniquePaylod();
        messages.add(new Message(OK.getFragment(), "", topicId));
        this.messageHandler.write(messages);
        return string;
    }
}
