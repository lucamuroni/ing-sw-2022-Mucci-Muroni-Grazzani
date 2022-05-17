package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import static it.polimi.ingsw.controller.networking.messageParts.ConnectionTimings.PLAYER_MOVE;

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
     */
    public String handle() throws TimeHasEndedException, ClientDisconnectedException {
        this.messageHandler.read(PLAYER_MOVE.getTiming());
        String string = this.messageHandler.getMessagePayloadFromStream(PHASE.getFragment());
        return string;
    }
}
