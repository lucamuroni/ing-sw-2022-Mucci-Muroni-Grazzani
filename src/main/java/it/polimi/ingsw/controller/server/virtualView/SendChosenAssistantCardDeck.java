package it.polimi.ingsw.controller.server.virtualView;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.AssistantCardDeckFigures;
import it.polimi.ingsw.model.gamer.Gamer;
import java.util.ArrayList;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.*;


/**
 * @author Sara Mucci
 * Class that implements the message to send to a player the assistant card deck the current player choses
 */
public class SendChosenAssistantCardDeck {
    AssistantCardDeckFigures deck;
    Integer token;
    MessageHandler messageHandler;
    Gamer gamer;

    /**
     * Class constructor
     * @param deck represents the chosen deck
     * @param token represents the token associated to the current player
     * @param messageHandler represents the messageHandles used for the message
     * @param gamer represents the currentGamer
     */
    public SendChosenAssistantCardDeck(AssistantCardDeckFigures deck, Integer token, MessageHandler messageHandler, Gamer gamer) {
        this.deck = deck;
        this.messageHandler = messageHandler;
        this.token = token;
        this.gamer = gamer;
    }

    /**
     * Method that handles the message exchange
     * @throws MalformedMessageException launched if the message isn't created in the correct way
     * @throws ClientDisconnectedException launched if the client disconnects from the game
     * @throws FlowErrorException launched when the client sends an unexpected response
     */
    public void handle() throws MalformedMessageException, ClientDisconnectedException, FlowErrorException {
        int topicId = this.messageHandler.getNewUniqueTopicID();
        ArrayList<Message> messages = new ArrayList<>();
        messages.add(new Message(OWNER.getFragment(), String.valueOf(this.gamer.getToken()), topicId));
        messages.add(new Message(ASSISTANT_CARD_DECK.getFragment(), this.deck.name(), topicId));
        this.messageHandler.write(messages);
        this.messageHandler.writeOutAndWait();
        this.messageHandler.assertOnEquals(OK.getFragment(), ASSISTANT_CARD_DECK.getFragment());
    }
}
