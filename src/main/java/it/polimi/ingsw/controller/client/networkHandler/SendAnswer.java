package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;

import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.ANSWER;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.OK;

/**
 * @author Luca Muroni
 * This class is used to notify the server if the player wants to play a characterCard or not
 */
public class SendAnswer {
    private final MessageHandler messageHandler;
    private final boolean answer;

    /**
     * Constructor of the class
     * @param messageHandler is the handler of messages
     * @param answer is the answer of the player
     */
    public SendAnswer(MessageHandler messageHandler, boolean answer) {
        this.messageHandler = messageHandler;
        this.answer = answer;
    }

    /**
     * Method that handles the exchange of messages
     * @throws ClientDisconnectedException when the player disconnects from the game
     * @throws FlowErrorException when there is an error in the synchronization
     * @throws MalformedMessageException when a received message isn't correct
     */
    public void handle() throws ClientDisconnectedException, FlowErrorException, MalformedMessageException {
        int topic = this.messageHandler.getNewUniqueTopicID();
        Message message = new Message(ANSWER.getFragment(), String.valueOf(answer), topic);
        this.messageHandler.write(message);
        this.messageHandler.writeOutAndWait();
        this.messageHandler.assertOnEquals(OK.getFragment(), ANSWER.getFragment());
    }
}
