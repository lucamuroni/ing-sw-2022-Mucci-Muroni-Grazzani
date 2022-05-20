package it.polimi.ingsw.controller.server.virtualView;

import it.polimi.ingsw.controller.networking.ConnectionTimings;
import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.controller.networking.AssistantCardDeckFigures;

import static it.polimi.ingsw.controller.networking.MessageFragment.ASSISTANT_CARD_DECK;
import static it.polimi.ingsw.controller.networking.MessageFragment.OK;

/**
 * @author Sara Mucci
 * Class that implements the message to send to a player the assistant card deck the current player choses
 */
public class SendChosenAssistantCardDeck {
    AssistantCardDeckFigures deck;
    Integer token;
    MessageHandler messageHandler;

    /**
     * Class constructor
     * @param deck represents the chosen deck
     * @param token represents the token associated to the current player
     * @param messageHandler represents the messageHandles used for the message
     */
    public SendChosenAssistantCardDeck(AssistantCardDeckFigures deck, Integer token, MessageHandler messageHandler) {
        this.deck = deck;
        this.messageHandler = messageHandler;
        this.token = token;
    }

    /**
     * Method that handles the message exchange
     * @throws MalformedMessageException launched if the message isn't created in the correct way
     * @throws TimeHasEndedException launched when the available time for the response has ended
     * @throws ClientDisconnectedException launched if the client disconnects from the game
     * @throws FlowErrorException launched when the client sends an unexpected response
     */
    public void handle() throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException, FlowErrorException {
        int topicId = this.messageHandler.getNewUniqueTopicID();
        Message message = new Message(ASSISTANT_CARD_DECK.getFragment(), this.deck.name(), topicId);
        this.messageHandler.write(message);
        this.messageHandler.writeOutAndWait(ConnectionTimings.RESPONSE.getTiming());
        if (!(this.messageHandler.getMessagesUniqueTopic() == topicId)) {
            throw new MalformedMessageException();
        }
        this.messageHandler.assertOnEquals(OK.getFragment(), ASSISTANT_CARD_DECK.getFragment());
    }
}
