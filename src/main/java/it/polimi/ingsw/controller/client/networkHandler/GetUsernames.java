package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.view.asset.game.Game;
import it.polimi.ingsw.view.asset.game.Gamer;

import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.*;

/**
 * @author Luca Muroni
 * This class is used to get the usernames of all others players
 */
public class GetUsernames {
    private final MessageHandler messageHandler;
    private final Game game;

    /**
     * Constructor of the class
     * @param messageHandler is the handler of messages
     * @param game is the current game
     */
    public GetUsernames(MessageHandler messageHandler, Game game) {
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
        int id = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(PLAYER_ID.getFragment()));
        String username = this.messageHandler.getMessagePayloadFromStream(PLAYER_NAME.getFragment());
        int topicId = this.messageHandler.getMessagesUniqueTopic();
        Message message = new Message(PLAYER.getFragment(), OK.getFragment(), topicId);
        this.messageHandler.write(message);
        this.messageHandler.writeOut();
        Gamer gamer = new Gamer(id);
        gamer.setUsername(username,false);
        this.game.getGamers().add(gamer);
    }
}