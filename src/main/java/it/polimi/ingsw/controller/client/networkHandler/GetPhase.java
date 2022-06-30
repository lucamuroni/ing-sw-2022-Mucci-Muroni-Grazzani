package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.Phase;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.view.asset.exception.AssetErrorException;

import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.OK;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.PHASE;

/**
 * @author Luca Muroni
 * This class is used to get the new phase that the player must play
 */
public class GetPhase {
    MessageHandler messageHandler;

    /**
     * Constructor of the class
     * @param messageHandler is the handler of messages
     */
    public GetPhase(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    /**
     * Method that handles the exchange of messages
     * @throws AssetErrorException when an object of the game isn't found in the asset
     * @throws MalformedMessageException when a received message isn't correct
     * @throws ClientDisconnectedException when the player disconnects from the game
     */
    public Phase handle() throws AssetErrorException, MalformedMessageException, ClientDisconnectedException {
        this.messageHandler.read();
        String result = this.messageHandler.getMessagePayloadFromStream(PHASE.getFragment());
        Phase phase = null;
        for (Phase ph : Phase.values()) {
            if (ph.toString().equals(result)) {
                phase = ph;
            }
        }
        if (phase == null)
            throw new AssetErrorException();
        int topicId = this.messageHandler.getMessagesUniqueTopic();
        Message message = new Message(PHASE.getFragment(), OK.getFragment(), topicId);
        this.messageHandler.write(message);
        this.messageHandler.writeOut();
        return phase;
    }
}