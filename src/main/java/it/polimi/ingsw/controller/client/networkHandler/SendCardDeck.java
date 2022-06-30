package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.AssistantCardDeckFigures;
import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.ASSISTANT_CARD_DECK;

/**
 * @author Sara Mucci
 * Class that implements the message to send the chosen card deck
 */
public class SendCardDeck {
    MessageHandler messageHandler;
    AssistantCardDeckFigures assistantCardDeck;

    /**
     * Constructor of the class
     * @param assistantCardDeck represents the chosen card deck
     * @param messageHandler is the handler of messages
     */
    public SendCardDeck(AssistantCardDeckFigures assistantCardDeck, MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
        this.assistantCardDeck = assistantCardDeck;
    }

    /**
     * Method that handles the exchange of messages
     * @throws MalformedMessageException when a received message isn't correct
     */
    public void handle() throws MalformedMessageException {
        int topicId = this.messageHandler.getMessagesUniqueTopic();
        Message message = new Message(ASSISTANT_CARD_DECK.getFragment(), assistantCardDeck.name(), topicId);
        this.messageHandler.write(message);
        this.messageHandler.writeOut();
    }
}
