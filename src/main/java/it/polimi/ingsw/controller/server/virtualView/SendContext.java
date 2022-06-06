package it.polimi.ingsw.controller.server.virtualView;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.controller.networking.messageParts.ConnectionTimings;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.*;


/**
 * @author Luca Muroni
 * Class that implements the messages to send the context to a client
 * A context is sent to the players not currently playing to inform them of the phase in which the current player is
 */
public class SendContext {
    String context;
    MessageHandler messageHandler;

    /**
     * Class constructor
     * @param context represents the current phase in which the current player is
     * @param messageHandler represents the message handler used for the message
     */
    public SendContext(String context, MessageHandler messageHandler) {
        this.context = context;
        this.messageHandler = messageHandler;
    }

    /**
     * Method that handles the messages exchange
     * @throws MalformedMessageException launched if the message isn't created in the correct way
     * @throws TimeHasEndedException launched when the available time for the response has ended
     * @throws ClientDisconnectedException launched if the client disconnects from the game
     * @throws FlowErrorException launched when the client sends an unexpected response
     */
    public void handle() throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException, FlowErrorException {
        int topicId = this.messageHandler.getNewUniqueTopicID();
        Message message = new Message(CONTEXT.getFragment(), context, topicId);
        this.messageHandler.write(message);
        this.messageHandler.writeOutAndWait(ConnectionTimings.RESPONSE.getTiming());
        this.messageHandler.assertOnEquals(OK.getFragment(), CONTEXT.getFragment());
    }
}
