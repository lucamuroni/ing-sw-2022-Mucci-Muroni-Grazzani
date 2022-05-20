package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.model.AssistantCard;
import java.util.ArrayList;
import static it.polimi.ingsw.controller.networking.messageParts.ConnectionTimings.PLAYER_MOVE;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.ASSISTANT_CARD;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.OK;

/**
 * @author Sara Mucci
 * Class that implements the message to get the chosen assistant card the current player chooses (to update the view)
 */
public class GetChosenAssistantCard {
    MessageHandler messageHandler;
    AssistantCard assistantCardToGet;

    /**
     * Class constructor
     * @param messageHandler represents the messageHandler used for the message
     */
    public GetChosenAssistantCard(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    /**
     * Method that handles the messages to get the chosen assistant card
     * @return the chosen assistant card
     * @throws TimeHasEndedException launched when the available time for the response has ended
     * @throws ClientDisconnectedException launched if the client disconnects from the game
     * @throws MalformedMessageException launched if the message isn't created in the correct way
     */
    public AssistantCard handle() throws TimeHasEndedException, ClientDisconnectedException, MalformedMessageException {
        ArrayList<Message> messages = new ArrayList<Message>();
        this.messageHandler.read(PLAYER_MOVE.getTiming());
        String name = this.messageHandler.getMessagePayloadFromStream(ASSISTANT_CARD.getFragment());
        for (AssistantCard assistantCard: AssistantCard.values()) {
            if (name.equals(assistantCard.getName())) {
                assistantCardToGet = assistantCard;
            }
        }
        int topicId = this.messageHandler.getMessagesUniqueTopic();
        messages.add(new Message(OK.getFragment(), "", topicId));
        this.messageHandler.write(messages);
        return assistantCardToGet;
    }
}
