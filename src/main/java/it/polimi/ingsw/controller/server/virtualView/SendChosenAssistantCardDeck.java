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
 * @author Luca Muroni
 * This class is used to send to a player the assistantCardDeck chosen by the current player
 */
public class SendChosenAssistantCardDeck {
    AssistantCardDeckFigures deck;
    Integer token;
    MessageHandler messageHandler;
    Gamer gamer;

    /**
     * Constructor of the class
     * @param deck represents the chosen deck
     * @param token represents the token associated with the current player
     * @param messageHandler is the handler of messages
     * @param gamer represents the currentGamer
     */
    public SendChosenAssistantCardDeck(AssistantCardDeckFigures deck, Integer token, MessageHandler messageHandler, Gamer gamer) {
        this.deck = deck;
        this.messageHandler = messageHandler;
        this.token = token;
        this.gamer = gamer;
    }

    /**
     * Method that handles the exchange of messages
     * @throws ClientDisconnectedException when the player disconnects from the game
     * @throws FlowErrorException when there is an error in the synchronization
     * @throws MalformedMessageException when a received message isn't correct
     */
    public void handle() throws ClientDisconnectedException, FlowErrorException, MalformedMessageException {
        int topicId = this.messageHandler.getNewUniqueTopicID();
        ArrayList<Message> messages = new ArrayList<>();
        messages.add(new Message(OWNER.getFragment(), String.valueOf(this.gamer.getToken()), topicId));
        messages.add(new Message(ASSISTANT_CARD_DECK.getFragment(), this.deck.name(), topicId));
        this.messageHandler.write(messages);
        this.messageHandler.writeOutAndWait();
        this.messageHandler.assertOnEquals(OK.getFragment(), ASSISTANT_CARD_DECK.getFragment());
    }
}