package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.view.asset.exception.AssetErrorException;
import it.polimi.ingsw.view.asset.game.Game;

import java.util.ArrayList;

import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.ASSISTANT_CARD;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.PAYLOAD_SIZE;

/**
 * @author Luca Muroni
 * @author Sara Mucci
 * Class that implements the message to get the possible assistant cards
 */
public class GetPossibleCards {
    MessageHandler messageHandler;
    ArrayList<AssistantCard> cards;
    private final Game game;

    /**
     * Constructor of the class
     * @param messageHandler is the handler of messages
     * @param game is the current game
     */
    public GetPossibleCards(MessageHandler messageHandler, Game game) {
        this.messageHandler = messageHandler;
        this.game = game;
        this.cards = new ArrayList<>();
    }

    /**
     * Method that handles the exchange of messages
     * @throws AssetErrorException when an object of the game isn't found in the asset
     * @throws MalformedMessageException when a received message isn't correct
     * @throws ClientDisconnectedException when the player disconnects from the game
     */
    public void handle() throws AssetErrorException, MalformedMessageException, ClientDisconnectedException {
        this.messageHandler.read();
        int num = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(PAYLOAD_SIZE.getFragment()));
        for (int i = 0; i<num; i++) {
            this.messageHandler.read();
            String string = this.messageHandler.getMessagePayloadFromStream(ASSISTANT_CARD.getFragment());
            boolean check = false;
            for (AssistantCard assistantCard: AssistantCard.values()) {
                if (string.equals(assistantCard.getName())) {
                    cards.add(assistantCard);
                    check = true;
                }
            }
            if (!check)
                throw new AssetErrorException();
        }
        game.getSelf().updateCards(cards);
    }
}
