package it.polimi.ingsw.controller.server.virtualView;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;

import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.*;


/**
 * @author Luca Muroni
 * Class that implements the messages to send the context to a client
 * A context is sent to the players not currently playing to inform them of the phase in which the current player is or
 * to send them information obout some game's objects
 */
public class SendContext {
    String context;
    MessageHandler messageHandler;

    /**
     * Class constructor
     * @param context represents the current phase in which the current player is
     * @param messageHandler is the handler of messages
     */
    public SendContext(String context, MessageHandler messageHandler) {
        this.context = context;
        this.messageHandler = messageHandler;
    }

    /**
     * Method that handles the exchange of messages
     * @throws ClientDisconnectedException when the player disconnects from the game
     * @throws FlowErrorException when there is an error in the synchronization
     * @throws MalformedMessageException when a received message isn't correct
     */
    public void handle() throws ClientDisconnectedException, FlowErrorException, MalformedMessageException {
        int topicId = this.messageHandler.getNewUniqueTopicID();
        Message message = new Message(CONTEXT.getFragment(), context, topicId);
        this.messageHandler.write(message);
        this.messageHandler.writeOutAndWait();
        this.messageHandler.assertOnEquals(OK.getFragment(), CONTEXT.getFragment());
    }
}