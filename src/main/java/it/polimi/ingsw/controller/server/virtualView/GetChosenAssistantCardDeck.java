package it.polimi.ingsw.controller.server.virtualView;

import it.polimi.ingsw.controller.networking.ConnectionTimings;
import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.controller.networking.AssistantCardDeckFigures;

import java.util.ArrayList;
import static it.polimi.ingsw.controller.networking.MessageFragment.ASSISTANT_CARD_DECK;
import static it.polimi.ingsw.controller.networking.MessageFragment.STOP;

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
        //ArrayList<Message> messages = new ArrayList<Message>();
        int topicId = this.messageHandler.getNewUniqueTopicID();
        for (AssistantCardDeckFigures assistantCardDeckFigures: this.decks) {
            Message message = new Message(ASSISTANT_CARD_DECK.getFragment(), assistantCardDeckFigures.name(), topicId);
            this.messageHandler.write(message);
        }
        this.messageHandler.write(new Message(STOP.getFragment(), "", topicId));
        this.messageHandler.read(ConnectionTimings.PLAYER_MOVE.getTiming());
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
