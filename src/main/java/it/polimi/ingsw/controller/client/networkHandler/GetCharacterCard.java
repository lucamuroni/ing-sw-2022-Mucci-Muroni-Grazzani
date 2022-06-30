package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.model.expert.CharacterCard;
import it.polimi.ingsw.view.asset.exception.AssetErrorException;
import it.polimi.ingsw.view.asset.game.Game;

import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.CHARACTER_CARD;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.OK;

/**
 * @author Luca Muroni
 * This class is used to get possible choices of characterCard to be played
 */
public class GetCharacterCard {
    private final MessageHandler messageHandler;
    private final Game game;

    /**
     * Constructor of the class
     * @param messageHandler is the handler of messages
     * @param game is the current game
     */
    public GetCharacterCard(MessageHandler messageHandler, Game game) {
        this.messageHandler = messageHandler;
        this.game = game;
    }

    /**
     * Method that handles the exchange of messages
     * @throws AssetErrorException when an object of the game isn't found in the asset
     * @throws MalformedMessageException when a received message isn't correct
     * @throws ClientDisconnectedException when the player disconnects from the game
     */
    public void handle() throws AssetErrorException, MalformedMessageException, ClientDisconnectedException {
        this.messageHandler.read();
        String name = this.messageHandler.getMessagePayloadFromStream(CHARACTER_CARD.getFragment());
        boolean check = false;
        for (CharacterCard card : CharacterCard.values()) {
            if (card.getName().equals(name)) {
                this.game.addCard(card);
                check = true;
                break;
            }
        }
        if (!check)
            throw new AssetErrorException();
        Message message = new Message(CHARACTER_CARD.getFragment(), OK.getFragment(), this.messageHandler.getMessagesUniqueTopic());
        this.messageHandler.write(message);
        this.messageHandler.writeOut();
    }
}
