package it.polimi.ingsw.controller.server.virtualView;

import it.polimi.ingsw.controller.networking.messageParts.ConnectionTimings;
import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.controller.networking.AssistantCardDeckFigures;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.*;
import java.util.ArrayList;


/**
 * @author Sara Mucci
 * Class that implements the message to get the assistant card deck chosen by the current player
 */
public class GetChosenAssistantCardDeck {
    ArrayList<AssistantCardDeckFigures> decks;
    MessageHandler messageHandler;

    /**
     * Class constructor
     * @param cardDeck represents the available decks
     * @param messageHandler represents the messageHandler used for the message
     */
    public GetChosenAssistantCardDeck(ArrayList<AssistantCardDeckFigures> cardDeck, MessageHandler messageHandler) {
        this.decks = cardDeck;
        this.messageHandler = messageHandler;
    }

    /**
     * Method that handles the message exchange
     * @return the chosen assistant card deck
     * @throws MalformedMessageException launched if the message isn't created in the correct way
     * @throws TimeHasEndedException launched when the available time for the response has ended
     * @throws ClientDisconnectedException launched if the client disconnects from the game
     */
    public AssistantCardDeckFigures handle() throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException {
        int topicId = this.messageHandler.getNewUniqueTopicID();
        int size = this.decks.size();
        Message message = new Message(PAYLOAD_SIZE.getFragment(), String.valueOf(size),topicId);
        this.messageHandler.write(message);
        this.messageHandler.writeOut();
        for (AssistantCardDeckFigures assistantCardDeckFigures: this.decks) {
            message = new Message(ASSISTANT_CARD_DECK.getFragment(), assistantCardDeckFigures.name(), topicId);
            this.messageHandler.write(message);
            this.messageHandler.writeOut();
        }
        this.messageHandler.read(ConnectionTimings.PLAYER_MOVE.getTiming());
        if (!(this.messageHandler.getMessagesUniqueTopic() == topicId)) {
            throw new MalformedMessageException();
        }
        AssistantCardDeckFigures result = null;
        String deckName = this.messageHandler.getMessagePayloadFromStream(ASSISTANT_CARD_DECK.getFragment());
        for (AssistantCardDeckFigures assistantCardDeckFigures: this.decks) {
            if (assistantCardDeckFigures.name().equals(deckName))
                result = assistantCardDeckFigures;
        }
        return result;
    }
}
