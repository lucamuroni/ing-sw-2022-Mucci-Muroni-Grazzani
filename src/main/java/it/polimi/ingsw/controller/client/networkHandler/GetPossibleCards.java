package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.model.AssistantCard;
import java.util.ArrayList;
import static it.polimi.ingsw.controller.networking.messageParts.ConnectionTimings.PLAYER_MOVE;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.ASSISTANT_CARD;

/**
 * @author Sara Mucci
 * Class that implements the message to get the possible assistant cards
 */
public class GetPossibleCards {
    MessageHandler messageHandler;
    ArrayList<AssistantCard> cards;

    /**
     * Class constructor
     * @param messageHandler represents the messageHandler used for the message
     */
    public GetPossibleCards(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
        this.cards = new ArrayList<>();
    }

    /**
     * Method that handles the messages to get the available assistant cards
     * @return the arraylist of available assistant cards
     * @throws TimeHasEndedException launched when the available time for the response has ended
     * @throws ClientDisconnectedException launched if the client disconnects from the game
     * @throws MalformedMessageException launched if the message isn't created the correct way
     */
    public ArrayList<AssistantCard> handle() throws TimeHasEndedException, ClientDisconnectedException, MalformedMessageException {
        this.messageHandler.read(PLAYER_MOVE.getTiming());
        int num = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(PAYLOAD_SIZE.getFragment()));
        for (int i = 0; i<num; i++) {
            this.messageHandler.read(PLAYER_MOVE.getTiming());
            String string = this.messageHandler.getMessagePayloadFromStream(ASSISTANT_CARD.getFragment());
            for (AssistantCard assistantCard: AssistantCard.values()) {
                if (string.equals(assistantCard.getName())) {
                    cards.add(assistantCard);
                }
            }
        }
        return cards;
    }
}
