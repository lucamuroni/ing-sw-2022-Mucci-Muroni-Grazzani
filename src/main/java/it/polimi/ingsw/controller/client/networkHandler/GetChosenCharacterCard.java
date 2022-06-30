package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.model.expert.CharacterCard;
import it.polimi.ingsw.view.asset.exception.AssetErrorException;
import it.polimi.ingsw.view.asset.game.Game;
import it.polimi.ingsw.view.asset.game.Gamer;

import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.CHARACTER_CARD;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.OK;

/**
 * @author Luca Muroni
 * This class is used to get the characterCard played by the currentPlayer
 */
public class GetChosenCharacterCard {
    private final MessageHandler messageHandler;
    private final Game game;

    /**
     * Constructor of the class
     * @param messageHandler is the handler of messages
     * @param game is the current game
     */
    public GetChosenCharacterCard(MessageHandler messageHandler, Game game) {
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
        CharacterCard card = null;
        for (CharacterCard card1 : this.game.getCards()) {
            if (card1.getName().equals(name))
                card = card1;
        }
        if (card == null)
            throw new AssetErrorException();
        Message message = new Message(CHARACTER_CARD.getFragment(), OK.getFragment(), this.messageHandler.getMessagesUniqueTopic());
        this.messageHandler.write(message);
        this.messageHandler.writeOut();
        Gamer current = null;
        for (Gamer gamer : this.game.getGamers()) {
            if (gamer.getUsername().equals(this.game.getCurrentPlayer())) {
                current = gamer;
                break;
            }
        }
        if (current == null)
            throw new AssetErrorException();
        current.setCurrentExpertCardSelection(card);
    }
}