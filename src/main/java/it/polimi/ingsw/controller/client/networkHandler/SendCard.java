package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.controller.networking.messageParts.ConnectionTimings;
import it.polimi.ingsw.model.AssistantCard;
import java.util.ArrayList;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.ASSISTANT_CARD;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.OK;

/**
 * @author Sara Mucci
 * Class that implements the message to send the chosen card
 */
public class SendCard {
    AssistantCard card;
    MessageHandler messageHandler;


    /**
     * Class constructor
     * @param messageHandler represents the messageHandler used for the message
     */
    public SendCard(AssistantCard card, MessageHandler messageHandler) {
        this.card = card;
        this.messageHandler = messageHandler;
    }

    /**
     * Method that handles the messages to send the chosen assistant card
     * @throws MalformedMessageException launched if the message isn't created the correct way
     * @throws FlowErrorException launched when the client sends an unexpected response
     * @throws TimeHasEndedException launched when the available time for the response has ended
     */
    public void handle() throws MalformedMessageException, FlowErrorException, TimeHasEndedException {
        ArrayList<Message> messages = new ArrayList<Message>();
        int topicId = this.messageHandler.getNewUniqueTopicID();
        messages.add(new Message(ASSISTANT_CARD.getFragment(), card.getName(), topicId));
        this.messageHandler.write(messages);
        messages.clear();
        this.messageHandler.writeOutAndWait(ConnectionTimings.RESPONSE.getTiming());
        this.messageHandler.assertOnEquals(OK.getFragment(), ASSISTANT_CARD.getFragment());
    }
}
