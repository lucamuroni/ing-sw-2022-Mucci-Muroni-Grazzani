package it.polimi.ingsw.controller.server.virtualView;

import it.polimi.ingsw.controller.networking.ConnectionTimings;
import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.controller.server.game.AssistantCardDeckFigures;
import java.util.ArrayList;
import static it.polimi.ingsw.controller.networking.MessageFragment.ASSISTANT_CARD_DECK;
import static it.polimi.ingsw.controller.networking.MessageFragment.OK;

public class SendChosenAssistantCardDeck {
    AssistantCardDeckFigures deck;
    Integer token;
    MessageHandler messageHandler;
    public SendChosenAssistantCardDeck(AssistantCardDeckFigures deck, Integer token, MessageHandler messageHandler) {
        this.deck = deck;
        this.messageHandler = messageHandler;
        this.token = token;
    }
    public void handle() throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException, FlowErrorException {
        ArrayList<Message> messages = new ArrayList<Message>();
        int topicId = this.messageHandler.getNewUniqueTopicID();
        messages.add(new Message(ASSISTANT_CARD_DECK.getFragment(), this.deck.name(), topicId));
        this.messageHandler.write(messages);
        messages.clear();
        this.messageHandler.writeOutAndWait(ConnectionTimings.RESPONSE.getTiming());
        this.messageHandler.assertOnEquals(OK.getFragment(), ASSISTANT_CARD_DECK.getFragment());
    }
}
