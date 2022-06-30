package it.polimi.ingsw.controller.server.virtualView;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.AssistantCardDeckFigures;

import java.util.ArrayList;

import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.*;


/**
 * @author Sara Mucci
 * @author Luca Muroni
 * Class that implements the message to get the assistantCardDeck chosen by the current player
 */
public class GetChosenAssistantCardDeck {
    ArrayList<AssistantCardDeckFigures> decks;
    MessageHandler messageHandler;

    /**
     * Constructor of the class
     * @param cardDeck represents the available decks
     * @param messageHandler is the handler of messages
     */
    public GetChosenAssistantCardDeck(ArrayList<AssistantCardDeckFigures> cardDeck, MessageHandler messageHandler) {
        this.decks = cardDeck;
        this.messageHandler = messageHandler;
    }

    /**
     * Method that handles the exchange of messages
     * @return the deck chosen by the player
     * @throws ClientDisconnectedException when the player disconnects from the game
     * @throws MalformedMessageException when a received message isn't correct
     */
    public AssistantCardDeckFigures handle() throws MalformedMessageException, ClientDisconnectedException {
        int topicId = this.messageHandler.getNewUniqueTopicID();
        int size = this.decks.size();
        Message message = new Message(PAYLOAD_SIZE.getFragment(), String.valueOf(size), topicId);
        this.messageHandler.write(message);
        this.messageHandler.writeOut();
        for (AssistantCardDeckFigures assistantCardDeckFigures: this.decks) {
            message = new Message(ASSISTANT_CARD_DECK.getFragment(), assistantCardDeckFigures.name(), topicId);
            this.messageHandler.write(message);
            this.messageHandler.writeOut();
        }
        this.messageHandler.read();
        if (!(this.messageHandler.getMessagesUniqueTopic() == topicId)) {
            throw  new MalformedMessageException();
        }
        String deckName;
        AssistantCardDeckFigures result = null;
        deckName = this.messageHandler.getMessagePayloadFromStream(ASSISTANT_CARD_DECK.getFragment());
        for (AssistantCardDeckFigures assistantCardDeckFigures: this.decks) {
            if (assistantCardDeckFigures.name().equals(deckName))
                result = assistantCardDeckFigures;
        }
        return result;
    }
}