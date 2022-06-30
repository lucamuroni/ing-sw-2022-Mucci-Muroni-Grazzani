package it.polimi.ingsw.controller.server.virtualView;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.model.AssistantCard;

import java.util.ArrayList;

import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.ASSISTANT_CARD;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.PAYLOAD_SIZE;


/**
 * @author Luca Muroni
 * Class that implements the message to get the assistant card chosen by the current player
 */
class GetChosenAssistantCard {
    ArrayList<AssistantCard> cards;
    MessageHandler messageHandler;

    /**
     * Constructor of the class
     * @param cards represents the arrayList of available cards
     * @param messageHandler is the handler of messages
     */
    public GetChosenAssistantCard(ArrayList<AssistantCard> cards, MessageHandler messageHandler){
        this.cards = cards;
        this.messageHandler = messageHandler;
    }

    /**
     * Method that handles the exchange of messages
     * @return the assistantCard chosen by the player
     * @throws ClientDisconnectedException when the player disconnects from the game
     * @throws MalformedMessageException when a received message isn't correct
     */
    public AssistantCard handle() throws MalformedMessageException, ClientDisconnectedException {
        int topicId = this.messageHandler.getNewUniqueTopicID();
        int size = this.cards.size();
        Message message = new Message(PAYLOAD_SIZE.getFragment(), String.valueOf(size),topicId);
        this.messageHandler.write(message);
        this.messageHandler.writeOut();
        for (AssistantCard card : this.cards){
            message = new Message(ASSISTANT_CARD.getFragment(), card.getName(), topicId);
            this.messageHandler.write(message);
            this.messageHandler.writeOut();
        }
        this.messageHandler.read();
        if (!(this.messageHandler.getMessagesUniqueTopic() == topicId)) {
            throw new MalformedMessageException();
        }
        String cardName;
        AssistantCard result = null;
        cardName = this.messageHandler.getMessagePayloadFromStream(ASSISTANT_CARD.getFragment());
        for (AssistantCard card : this.cards) {
            if (card.getName().equals(cardName))
                result = card;
        }
        return result;
    }
}