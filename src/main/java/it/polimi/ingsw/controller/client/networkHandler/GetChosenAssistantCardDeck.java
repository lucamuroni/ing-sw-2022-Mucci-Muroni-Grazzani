package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.AssistantCardDeckFigures;
import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.view.asset.exception.AssetErrorException;
import it.polimi.ingsw.view.asset.game.Game;
import it.polimi.ingsw.view.asset.game.Gamer;

import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.*;

/**
 * @author Luca Muroni
 * Class that implements the messages to get the choosen card deck the current player chooses (to update the view)
 */
public class GetChosenAssistantCardDeck {
    private final MessageHandler messageHandler;
    private final Game game;
    private AssistantCardDeckFigures deck;

    /**
     * Class constructor
     * @param messageHandler represents the message handler used for the message
     * @param game represents the current game
     */
    public GetChosenAssistantCardDeck(MessageHandler messageHandler, Game game) {
        this.messageHandler = messageHandler;
        this.game = game;
    }

    /**
     * Method that handles the messages to get the chosen deck
     * @throws AssetErrorException when an object of the game isn't found in the asset
     * @throws MalformedMessageException when a received message isn't correct
     * @throws ClientDisconnectedException when the player disconnects from the game
     */
    public void handle() throws AssetErrorException, MalformedMessageException, ClientDisconnectedException {
        this.messageHandler.read();
        int id = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(OWNER.getFragment()));
        Gamer owner = null;
        for (Gamer gamer : game.getGamers()) {
            if (gamer.getId() == id) {
                owner = gamer;
            }
        }
        if (owner==null)
            throw new AssetErrorException();
        String name = this.messageHandler.getMessagePayloadFromStream(ASSISTANT_CARD_DECK.getFragment());
        for (AssistantCardDeckFigures deckFigures: AssistantCardDeckFigures.values()) {
            if (name.equals(deckFigures.name())) {
                deck = deckFigures;
            }
        }
        if (deck == null)
            throw new AssetErrorException();
        int topicId = this.messageHandler.getMessagesUniqueTopic();
        Message message = new Message(ASSISTANT_CARD_DECK.getFragment(), OK.getFragment(), topicId);
        this.messageHandler.write(message);
        this.messageHandler.writeOut();
        owner.updateFigure(deck);
    }
}

