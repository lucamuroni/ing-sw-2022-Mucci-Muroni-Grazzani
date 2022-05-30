package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.view.asset.game.Game;
import it.polimi.ingsw.view.asset.game.Island;
import static it.polimi.ingsw.controller.networking.messageParts.ConnectionTimings.PLAYER_MOVE;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.*;

/**
 * @author Luca Muroni
 * @author Sara Mucci
 * Class that updates the mother nature location in the view
 */
public class GetMotherNaturePlace {
    MessageHandler messageHandler;
    Island MNIsland;
    Game game;

    /**
     * Class constructor
     * @param messageHandler represents the messageHandler used for the message
     * @param game represents the current game
     */
    public GetMotherNaturePlace(MessageHandler messageHandler, Game game) {
        this.messageHandler = messageHandler;
        this.game = game;
    }

    /**
     * Method that handles the messages to get the mother nature position
     * @throws TimeHasEndedException launched when the available time for the response has ended
     * @throws ClientDisconnectedException launched if the client disconnects from the game
     * @throws MalformedMessageException launched if the message isn't created the correct way
     */
    public void handle() throws TimeHasEndedException, ClientDisconnectedException, MalformedMessageException {
        this.messageHandler.read(PLAYER_MOVE.getTiming());
        int result = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(MN_LOCATION.getFragment()));
        Island MNIsland = null;
        for (Island island : game.getIslands()) {
            if (island.getId() == result) {
                MNIsland = island;
            }
        }
        int topicId = this.messageHandler.getMessagesUniqueTopic();
        Message message = new Message(MN_LOCATION.getFragment(), OK.getFragment(), topicId);
        this.messageHandler.write(message);
        this.messageHandler.writeOut();
        game.setMotherNaturePosition(MNIsland);
    }
}
