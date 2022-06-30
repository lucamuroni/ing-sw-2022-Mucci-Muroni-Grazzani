package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.AssistantCardDeckFigures;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.view.asset.exception.AssetErrorException;

import java.util.ArrayList;

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
     * Constructor of the class
     * @param messageHandler is the handler of messages
     */
    public GetPossibleDecks(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
        this.decks = new ArrayList<>();
    }

    /**
     * Method that handles the exchange of messages
     * @throws AssetErrorException when an object of the game isn't found in the asset
     * @throws MalformedMessageException when a received message isn't correct
     * @throws ClientDisconnectedException when the player disconnects from the game
     */
    public ArrayList<AssistantCardDeckFigures> handle() throws AssetErrorException, MalformedMessageException, ClientDisconnectedException {
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
