package it.polimi.ingsw.controller.server.virtualView;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.Phase;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.*;


/**
 * @author Sara Mucci
 * Class that implements the message to send the new phase to the current player
 */
public class SendNewPhase {
    MessageHandler messageHandler;
    Phase phase;

    /**
     * Constructor of the game
     * @param phase represents the phase to send
     * @param messageHandler is the handler of messages
     */
    public SendNewPhase(Phase phase, MessageHandler messageHandler) {
        this.phase = phase;
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
        Message message = new Message(PHASE.getFragment(), phase.toString(), topicId);
        this.messageHandler.write(message);
        this.messageHandler.writeOutAndWait();
        this.messageHandler.assertOnEquals(OK.getFragment(), PHASE.getFragment());
    }
}