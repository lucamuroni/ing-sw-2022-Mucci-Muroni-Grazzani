package it.polimi.ingsw.controller.server.virtualView;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;

import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.ANSWER;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.OK;

/**
 * @author Luca Muroni
 * This class is used to know if the player wants to play a characterCard or not
 */
public class GetAnswer {
    private final MessageHandler messageHandler;

    /**
     * Constructor of the class
     * @param messageHandler is the handler of messages
     */
    public GetAnswer(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    /**
     * Method that handles the exchange of messages
     * @return the answer of the player
     * @throws ClientDisconnectedException when the player disconnects from the game
     * @throws MalformedMessageException when a received message isn't correct
     */
    public boolean handle() throws ClientDisconnectedException, MalformedMessageException {
        this.messageHandler.read();
        boolean result;
        String answer = this.messageHandler.getMessagePayloadFromStream(ANSWER.getFragment());
        result = answer.equals("true");
        int topicId = this.messageHandler.getMessagesUniqueTopic();
        Message message = new Message(ANSWER.getFragment(), OK.getFragment(), topicId);
        this.messageHandler.write(message);
        this.messageHandler.writeOut();
        return result;
    }
}