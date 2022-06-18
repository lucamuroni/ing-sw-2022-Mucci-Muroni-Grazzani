package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.AssistantCardDeckFigures;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.view.asset.exception.AssetErrorException;

import java.util.ArrayList;
import static it.polimi.ingsw.controller.networking.messageParts.ConnectionTimings.PLAYER_MOVE;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.ASSISTANT_CARD_DECK;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.PAYLOAD_SIZE;

/**
 * @author Luca Muroni
 * @author Sara Mucci
 * Class that implements the messages to get the available deck figures
 */
public class GetPossibleDecks {
    MessageHandler messageHandler;
    ArrayList<AssistantCardDeckFigures> decks;

    /**
     * Class constructor
     * @param messageHandler represents the messageHandler used for the message
     */
    public GetPossibleDecks(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
        this.decks = new ArrayList<>();
    }

    /**
     * Method that handles the messages to get the possible deck figures
     * @return the possible decks
     * @throws ClientDisconnectedException launched if the client disconnects from the game
     * @throws MalformedMessageException launched if the message isn't created the correct way
     */
    public ArrayList<AssistantCardDeckFigures> handle() throws ClientDisconnectedException, MalformedMessageException, AssetErrorException {
        this.messageHandler.read();
        int num = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(PAYLOAD_SIZE.getFragment()));
        for (int i = 0; i<num; i++) {
            this.messageHandler.read();
            String string = this.messageHandler.getMessagePayloadFromStream(ASSISTANT_CARD_DECK.getFragment());
            boolean check = false;
            for (AssistantCardDeckFigures assistantCardDeck: AssistantCardDeckFigures.values()) {
                if (string.equals(assistantCardDeck.name())) {
                    decks.add(assistantCardDeck);
                    check = true;
                }
            }
            if (!check)
                throw new AssetErrorException();
        }
        return decks;
    }
}
