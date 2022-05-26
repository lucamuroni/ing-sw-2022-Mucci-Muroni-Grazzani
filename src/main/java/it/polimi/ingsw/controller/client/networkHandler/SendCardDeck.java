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
     * Class constructor
     * @param assistantCardDeck represents the chosen card deck
     * @param messageHandler represents the messageHandler used for the message
     */
    public SendCardDeck(AssistantCardDeckFigures assistantCardDeck, MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
        this.assistantCardDeck = assistantCardDeck;
    }

    /**
     * Method that handles the messages to send the chosen deck
     * @throws MalformedMessageException launched if the message isn't created in the correct way
     */
    public void handle() throws MalformedMessageException {
        int topicId = this.messageHandler.getMessagesUniqueTopic();
        Message message = new Message(ASSISTANT_CARD_DECK.getFragment(), assistantCardDeck.name(), topicId);
        this.messageHandler.write(message);
        this.messageHandler.writeOut();
    }
}
