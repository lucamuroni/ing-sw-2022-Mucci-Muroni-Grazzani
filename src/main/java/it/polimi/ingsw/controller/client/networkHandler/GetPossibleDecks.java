package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.controller.server.game.AssistantCardDeckFigures;
import java.util.ArrayList;
import static it.polimi.ingsw.controller.networking.messageParts.ConnectionTimings.PLAYER_MOVE;

/**
 * @author Sara Mucci
 * Class that implements the messages to get the available deck figures
 */
public class GetPossibleDecks {
    MessageHandler messageHandler;
    Boolean stop = false;
    ArrayList<AssistantCardDeckFigures> decks;

    /**
     * Class constructor
     * @param messageHandler represents the messageHandler used for the message
     */
    public GetPossibleDecks(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    /**
     * Method that handles the messages to get the possible deck figures
     * @return the possible decks
     * @throws TimeHasEndedException launched when the available time for the response has ended
     * @throws ClientDisconnectedException launched if the client disconnects from the game
     * @throws MalformedMessageException launched if the message isn't created the correct way
     */
    public ArrayList<AssistantCardDeckFigures> handle() throws TimeHasEndedException, ClientDisconnectedException, MalformedMessageException {
        while (!stop) {
            this.messageHandler.read(PLAYER_MOVE.getTiming());
            String string = this.messageHandler.getMessagePayloadFromStream(ASSISTANT_CARD_DECK.getFragment());
            if (string.equals("stop")) {
                stop = true;
            }
            else {
                for (AssistantCardDeckFigures assistantCardDeck: AssistantCardDeckFigures.values()) {
                    if (string.equals(assistantCardDeck.name())) {
                        decks.add(assistantCardDeck);
                    }
                }
            }
        }
        return decks;
    }
}
