package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.model.pawn.TowerColor;
import it.polimi.ingsw.view.asset.exception.AssetErrorException;
import it.polimi.ingsw.view.asset.game.Game;
import static it.polimi.ingsw.controller.networking.messageParts.ConnectionTimings.PLAYER_MOVE;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.OK;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.TOWER_COLOR;

/**
 * @author Sara Mucci
 * Class that implements the message to get the new owner of the island mother nature is on
 */
public class GetOwner {
    MessageHandler messageHandler;
    Game game;

    /**
     * Class constructor
     * @param messageHandler represents the messageHandler used for the message
     */
    public GetOwner(MessageHandler messageHandler, Game game) {
        this.messageHandler = messageHandler;
        this.game = game;
    }

    /**
     * Method that handles the messages to get the color of the island's new owner
     * @throws TimeHasEndedException launched when the available time for the response has ended
     * @throws ClientDisconnectedException launched if the client disconnects from the game
     * @throws MalformedMessageException launched if the message isn't created the correct way
     */
    public void handle() throws TimeHasEndedException, ClientDisconnectedException, MalformedMessageException, AssetErrorException {
        this.messageHandler.read(PLAYER_MOVE.getTiming());
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