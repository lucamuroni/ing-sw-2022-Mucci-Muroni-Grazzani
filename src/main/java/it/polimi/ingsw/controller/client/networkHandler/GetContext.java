package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.messageParts.MessageFragment;
import it.polimi.ingsw.view.asset.exception.AssetErrorException;

import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.CONTEXT;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.OK;

/**
 * @author Luca Muroni
 * This class is used to get the "place" where the player must go: a phase (Idle, ActionPhase1, ...) or a view update
 * (e.g. stay in Idle)
 */
public class GetContext {
    MessageHandler messageHandler;

    /**
     * Constructor of the class
     * @param messageHandler is the handler of messages
     */
    public GetContext(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    /**
     * Method that handles the exchange of messages
     * @return the context where to go
     * @throws AssetErrorException when an object of the game isn't found in the asset
     * @throws MalformedMessageException when a received message isn't correct
     * @throws ClientDisconnectedException when the player disconnects from the game
     */
    public String handle() throws AssetErrorException, MalformedMessageException, ClientDisconnectedException {
        this.messageHandler.read();
        String context = this.messageHandler.getMessagePayloadFromStream(CONTEXT.getFragment());
        boolean check = false;
        for (MessageFragment fragment : MessageFragment.values()) {
            if (fragment.getFragment().equals(context)) {
                check = true;
                break;
            }
        }
        if (!check)
            throw new AssetErrorException();
        int topicId = this.messageHandler.getMessagesUniqueTopic();
        Message message = new Message(CONTEXT.getFragment(), OK.getFragment(), topicId);
        this.messageHandler.write(message);
        this.messageHandler.writeOut();
        return context;
    }
}