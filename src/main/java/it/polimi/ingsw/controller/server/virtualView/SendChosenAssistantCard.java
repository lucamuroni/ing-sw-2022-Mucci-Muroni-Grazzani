package it.polimi.ingsw.controller.server.virtualView;

import it.polimi.ingsw.controller.networking.messageParts.ConnectionTimings;
import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.model.AssistantCard;
import java.util.ArrayList;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.*;


/**
 * @author Sara Mucci
 * Class that implements the message to send to a player the assistant card the current player choses
 */
public class SendChosenAssistantCard {
    AssistantCard card;
    Integer token;
    MessageHandler messageHandler;

    /**
     * Class constructor
     * @param card represents the chosen assistant card
     * @param token represents the token associated to the current player
     * @param messageHandler represents the messageHandler used for the message
     */
    public SendChosenAssistantCard(AssistantCard card, Integer token, MessageHandler messageHandler) {
        this.card = card;
        this.token = token;
        this.messageHandler = messageHandler;
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
        ArrayList<Message> messages = new ArrayList<>();
        messages.add(new Message(OWNER.getFragment(), String.valueOf(token), topicId));
        messages.add(new Message(ASSISTANT_CARD.getFragment(), this.card.getName(), topicId));
        this.messageHandler.write(messages);
        this.messageHandler.writeOutAndWait(ConnectionTimings.RESPONSE.getTiming());
        this.messageHandler.assertOnEquals(OK.getFragment(), ASSISTANT_CARD.getFragment());
    }
}
