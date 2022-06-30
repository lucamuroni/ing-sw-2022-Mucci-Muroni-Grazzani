package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.view.asset.exception.AssetErrorException;
import it.polimi.ingsw.view.asset.game.Game;
import it.polimi.ingsw.view.asset.game.Gamer;

import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.OK;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.PLAYER_NAME;

/**
 * @author Luca Muroni
 * This class is used to get the name of the player that is currently playing
 */
public class GetCurrentPlayer {
    MessageHandler messageHandler;
    Game game;

    /**
     * Constructor of the class
     * @param messageHandler is the handler of messages
     * @param game is the current game
     */
    public GetCurrentPlayer(MessageHandler messageHandler, Game game) {
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
        String player = this.messageHandler.getMessagePayloadFromStream(PLAYER_NAME.getFragment());
        boolean check = false;
        for (Gamer gamer : this.game.getGamers()) {
            if (gamer.getUsername().equals(player)) {
                check = true;
                break;
            }
        }
        if (!check)
            throw new AssetErrorException();
        int topicId = this.messageHandler.getMessagesUniqueTopic();
        Message message = new Message(PLAYER_NAME.getFragment(), OK.getFragment(), topicId);
        this.messageHandler.write(message);
        this.messageHandler.writeOut();
        this.game.setCurrentPlayer(player);
    }
}