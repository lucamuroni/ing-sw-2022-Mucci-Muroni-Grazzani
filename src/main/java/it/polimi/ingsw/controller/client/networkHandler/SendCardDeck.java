package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.messageParts.ConnectionTimings;
import it.polimi.ingsw.controller.server.game.AssistantCardDeckFigures;
import java.util.ArrayList;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.OK;

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

    public void handle() throws MalformedMessageException {
        ArrayList<Message> messages = new ArrayList<Message>();
        int topicId = this.messageHandler.getMessagesUniqueTopic(); //TODO: è giusto prendere il topicId del messaggio che manda il server?
        messages.add(new Message(ASSISTANT_CARD_DECK.getFragment(), assistantCardDeck.name(), topicId));
        this.messageHandler.write(messages);
        messages.clear();
        this.messageHandler.writeOutAndWait(ConnectionTimings.RESPONSE.getTimind());
        this.messageHandler.assertOnEquals(OK.getFragment(), ASSISTANT_CARD_DECK.getFragment());
        //TODO: nel messaggio del server non si deve inviare un messaggio OK per conferma della ricezione?
    }
}
