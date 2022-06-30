package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.model.AssistantCard;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.ASSISTANT_CARD;

/**
 * @author Sara Mucci
 * Class that implements the message to send the chosen assistantCard
 */
public class SendCard {
    AssistantCard card;
    MessageHandler messageHandler;

    /**
     * Constructor of the class
     * @param messageHandler is the handler of messages
     * @param card represents the chosen card to send
     */
    public SendCard(AssistantCard card, MessageHandler messageHandler) {
        this.card = card;
        this.messageHandler = messageHandler;
    }

    /**
     * Method that handles the exchange of messages
     * @throws FlowErrorException when there is an error in the synchronization
     * @throws MalformedMessageException when a received message isn't correct
     */
    public void handle() throws MalformedMessageException, FlowErrorException {
        int topicId = this.messageHandler.getMessagesUniqueTopic();
        Message message = new Message(ASSISTANT_CARD.getFragment(), card.getName(), topicId);
        this.messageHandler.write(message);
        this.messageHandler.writeOut();
    }
}