package it.polimi.ingsw.controller.server.virtualView;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;

import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.*;

/**
 * @author Luca Muroni
 * This class is used to send to a player his coins
 */
public class SendCoins {
    private final MessageHandler messageHandler;
    private final int coins;

    /**
     * Constructor of the class
     * @param messageHandler is the handler of messages
     * @param coins is the number of coins owned by the player
     */
    public SendCoins(MessageHandler messageHandler, int coins) {
        this.messageHandler = messageHandler;
        this.coins = coins;
    }

    /**
     * Method that handles the exchange of messages
     * @throws ClientDisconnectedException when the player disconnects from the game
     * @throws FlowErrorException when there is an error in the synchronization
     * @throws MalformedMessageException when a received message isn't correct
     */
    public void handle() throws ClientDisconnectedException, FlowErrorException, MalformedMessageException {
        int topicId = this.messageHandler.getNewUniqueTopicID();
        Message message = new Message(COIN.getFragment(), String.valueOf(coins), topicId);
        this.messageHandler.write(message);
        this.messageHandler.writeOutAndWait();
        this.messageHandler.assertOnEquals(OK.getFragment(), COIN.getFragment());
    }
}