package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.messageParts.MessageFragment;

/**
 * @author Davide Grazzani
 * This class is used to get the lobby assigned to the player by the server
 */
public class AwaitForLobby {
    private final MessageHandler messageHandler;

    /**
     * Constructor of the class
     * @param messageHandler is the handler of messages
     */
    public AwaitForLobby(MessageHandler messageHandler){
        this.messageHandler = messageHandler;
    }

    /**
     * Method that handles the exchange of messages
     * @throws ClientDisconnectedException when the player disconnects from the game
     * @throws FlowErrorException when there is an error in the synchronization
     * @throws MalformedMessageException when a received message isn't correct
     */
    public void handle() throws ClientDisconnectedException, FlowErrorException, MalformedMessageException {
        this.messageHandler.read();
        this.messageHandler.assertOnEquals(MessageFragment.GREETINGS_STATUS_SUCCESFULL.getFragment(),MessageFragment.GREETINGS.getFragment());
        this.messageHandler.write(new Message(MessageFragment.GREETINGS.getFragment(), MessageFragment.OK.getFragment(), this.messageHandler.getMessagesUniqueTopic()));
        this.messageHandler.writeOut();
    }
}