package it.polimi.ingsw.controller.server.virtualView;

import it.polimi.ingsw.controller.networking.ConnectionTimings;
import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.model.AssistantCard;
import java.util.ArrayList;
import static it.polimi.ingsw.controller.networking.MessageFragment.ASSISTANT_CARD;
import static it.polimi.ingsw.controller.networking.MessageFragment.STOP;

/**
 * @author Luca Muroni
 * Class that implements the message to get the assistant card chosen by the current player
 */
class GetChosenAssistantCard {
    ArrayList<AssistantCard> cards;
    MessageHandler messageHandler;

    /**
     * Class Constructor
     * @param cards represents the arraylist of available cards
     * @param messageHandler represents the messageHandler used for the message
     */
    public GetChosenAssistantCard(ArrayList<AssistantCard> cards, MessageHandler messageHandler){
        this.cards = cards;
        this.messageHandler = messageHandler;
    }

    /**
     * Method that handles the message exchange
     * @return the chosen assistant card
     * @throws MalformedMessageException launched if the message isn't created in the correct way
     * @throws TimeHasEndedException launched when the available time for the response has ended
     * @throws ClientDisconnectedException launched if the client disconnects from the game
     */
    public AssistantCard handle() throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException {
        int topicId = this.messageHandler.getNewUniqueTopicID();
        for (AssistantCard card : this.cards){
            Message message = new Message(ASSISTANT_CARD.getFragment(), card.getName(), topicId);
            this.messageHandler.write(message);
        }
        this.messageHandler.write(new Message(STOP.getFragment(), "", topicId));
        this.messageHandler.read(ConnectionTimings.PLAYER_MOVE.getTiming());
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