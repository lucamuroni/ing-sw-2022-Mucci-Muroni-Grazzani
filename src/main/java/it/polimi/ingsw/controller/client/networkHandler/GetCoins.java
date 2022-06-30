package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.view.asset.game.Game;

import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.COIN;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.OK;

/**
 * @author Luca muroni
 * This class is used to get the coins of the player
 */
public class GetCoins {
    private final MessageHandler messageHandler;
    private final Game game;

    /**
     * Constructor of the class
     * @param messageHandler is the handler of messages
     * @param game is the current game
     */
    public GetCoins(MessageHandler messageHandler, Game game) {
        this.messageHandler = messageHandler;
        this.game = game;
    }

    /**
     * Method that handles the exchange of messages
     * @throws MalformedMessageException when a received message isn't correct
     * @throws ClientDisconnectedException when the player disconnects from the game
     */
    public void handle() throws MalformedMessageException, ClientDisconnectedException {
        this.messageHandler.read();
        String value = this.messageHandler.getMessagePayloadFromStream(COIN.getFragment());
        this.game.setCoins(Integer.parseInt(value));
        Message message = new Message(COIN.getFragment(), OK.getFragment(), this.messageHandler.getMessagesUniqueTopic());
        this.messageHandler.write(message);
        this.messageHandler.writeOut();
    }
}