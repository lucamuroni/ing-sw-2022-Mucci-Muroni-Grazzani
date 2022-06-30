package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.model.pawn.TowerColor;
import it.polimi.ingsw.view.asset.exception.AssetErrorException;
import it.polimi.ingsw.view.asset.game.Game;

import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.OK;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.TOWER_COLOR;

/**
 * @author Sara Mucci
 * Class that implements the message to get the new owner of the island where mother nature is on
 */
public class GetOwner {
    MessageHandler messageHandler;
    Game game;

    /**
     * Constructor of the class
     * @param messageHandler is the handler of messages
     */
    public GetOwner(MessageHandler messageHandler, Game game) {
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
        String result = this.messageHandler.getMessagePayloadFromStream(TOWER_COLOR.getFragment());
        TowerColor color = null;
        for (TowerColor tc : TowerColor.values()) {
            if (tc.toString().equals(result)) {
                color = tc;
            }
        }
        if (color == null)
            throw new AssetErrorException();
        int topicId = this.messageHandler.getMessagesUniqueTopic();
        Message message = new Message(TOWER_COLOR.getFragment(), OK.getFragment(), topicId);
        this.messageHandler.write(message);
        this.messageHandler.writeOut();
        this.game.getMotherNaturePosition().updateOwner(color);
    }
}