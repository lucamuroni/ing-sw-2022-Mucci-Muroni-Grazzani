package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.model.AssistantCard;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.ASSISTANT_CARD;

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
     * @param card represents the chosen card to send
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
        int topicId = this.messageHandler.getMessagesUniqueTopic();
        Message message = new Message(ASSISTANT_CARD.getFragment(), card.getName(), topicId);
        this.messageHandler.write(message);
        this.messageHandler.writeOut();
    }
}
