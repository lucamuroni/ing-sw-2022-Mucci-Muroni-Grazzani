package it.polimi.ingsw.controller.server.virtualView;

import it.polimi.ingsw.controller.networking.ConnectionTimings;
import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.controller.server.game.AssistantCardDeckFigures;
import it.polimi.ingsw.model.AssistantCardDeck;
import java.util.ArrayList;

import static it.polimi.ingsw.controller.networking.MessageFragment.ASSISTANT_CARD_DECK;

public class GetChosenAssistantCardDeck {
    ArrayList<AssistantCardDeckFigures> decks;
    MessageHandler messageHandler;
    public GetChosenAssistantCardDeck(ArrayList<AssistantCardDeckFigures> cardDeck, MessageHandler messageHandler) {
        this.decks = cardDeck;
        this.messageHandler = messageHandler;
    }
    public AssistantCardDeckFigures handle() throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException {
        ArrayList<Message> messages = new ArrayList<Message>();
        int topicId = this.messageHandler.getNewUniqueTopicID();
        for (AssistantCardDeckFigures assistantCardDeckFigures: this.decks) {
            messages.add(new Message(ASSISTANT_CARD_DECK.getFragment(), assistantCardDeckFigures.name(), topicId));
        }
        this.messageHandler.write(messages);
        messages.clear();
        this.messageHandler.writeOutAndWait(ConnectionTimings.PLAYER_MOVE.getTiming());
        String deckName;
        AssistantCardDeckFigures result = null;
        deckName = this.messageHandler.getMessagePayloadFromStream(ASSISTANT_CARD_DECK.getFragment());
        for (AssistantCardDeckFigures assistantCardDeckFigures: this.decks) {
            if (assistantCardDeckFigures.equals(deckName))
                result = assistantCardDeckFigures;
        }
        return result;
    }
}
