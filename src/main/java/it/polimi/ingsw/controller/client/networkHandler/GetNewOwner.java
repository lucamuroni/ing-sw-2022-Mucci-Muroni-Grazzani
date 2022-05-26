package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.model.pawn.TowerColor;
import it.polimi.ingsw.view.asset.game.Game;
import static it.polimi.ingsw.controller.networking.messageParts.ConnectionTimings.PLAYER_MOVE;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.OK;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.TOWER_COLOR;

/**
 * @author Sara Mucci
 * Class that implements the message to get the new owner of the island mother nature is on
 */
public class GetNewOwner {
    MessageHandler messageHandler;
    Game game;

    /**
     * Class constructor
     * @param messageHandler represents the messageHandler used for the message
     */
    public GetNewOwner(MessageHandler messageHandler, Game game) {
        this.messageHandler = messageHandler;
        this.game = game;
    }

    /**
     * Method that handles the messages to get the color of the island's new owner
     * @throws TimeHasEndedException
     * @throws ClientDisconnectedException
     * @throws MalformedMessageException
     */
    public void handle() throws TimeHasEndedException, ClientDisconnectedException, MalformedMessageException {
        this.messageHandler.read(PLAYER_MOVE.getTiming());
        String result = this.messageHandler.getMessagePayloadFromStream(TOWER_COLOR.getFragment());
        TowerColor color = null;
        for (TowerColor tc : TowerColor.values()) {
            if (tc.toString().equals(result)) {
                color = tc;
            }
        }
        int topicId = this.messageHandler.getMessagesUniqueTopic();
        Message message = new Message(TOWER_COLOR.getFragment(), OK.getFragment(), topicId);
        this.messageHandler.write(message);
        this.messageHandler.writeOut();
        this.game.getMotherNaturePosition().updateOwner(color);
    }
}
//TODO: serve un metodo o un attributo in island per mostrare il colore della torre sull'isola