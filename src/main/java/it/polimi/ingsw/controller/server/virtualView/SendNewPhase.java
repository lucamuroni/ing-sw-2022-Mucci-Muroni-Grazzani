package it.polimi.ingsw.controller.server.virtualView;

import it.polimi.ingsw.controller.networking.ConnectionTimings;
import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import java.util.ArrayList;
import static it.polimi.ingsw.controller.networking.MessageFragment.OK;
import static it.polimi.ingsw.controller.networking.MessageFragment.PHASE;

/**
 * @author Sara Mucci
 * Class that implements the message to send the new phase to the current player
 */
public class SendNewPhase {
    MessageHandler messageHandler;
    Phase phase;

    /**
     * Class constructor
     * @param phase represents the phase to send
     * @param messageHandler represents the messageHandler used for the message
     */
    public SendNewPhase(Phase phase, MessageHandler messageHandler) {
        this.phase = phase;
        this.messageHandler = messageHandler;
    }

    /**
     * Method that handles the message exchange
     * @throws MalformedMessageException launched if the message isn't created in the correct way
     * @throws TimeHasEndedException launched when the available time for the response has ended
     * @throws ClientDisconnectedException launched if the client disconnects from the game
     * @throws FlowErrorException launched when the client sends an unexpected response
     */
    public void handle() throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException, FlowErrorException {
        int topicId = this.messageHandler.getNewUniqueTopicID();
        Message message = new Message(PHASE.getFragment(), phase.name(), topicId);
        this.messageHandler.write(message);
        this.messageHandler.writeOutAndWait(ConnectionTimings.RESPONSE.getTiming());
        this.messageHandler.assertOnEquals(OK.getFragment(), PHASE.getFragment());
    }
}
