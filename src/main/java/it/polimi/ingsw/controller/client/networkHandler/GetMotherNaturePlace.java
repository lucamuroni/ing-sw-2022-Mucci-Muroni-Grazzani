package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.view.asset.game.Game;
import it.polimi.ingsw.view.asset.game.Island;
import static it.polimi.ingsw.controller.networking.messageParts.ConnectionTimings.PLAYER_MOVE;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.ISLAND_ID;

/**
 * @author Sara Mucci
 * Class that updates the mother nature location in the view
 */
public class GetMotherNaturePlace {
    MessageHandler messageHandler;
    Boolean stop = false;
    Island MNIsland;
    Game game;

    /**
     * Class constructor
     * @param messageHandler represents the messageHandler used for the message
     */
    public GetMotherNaturePlace(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    /**
     * Method that handles the messages to get the mother nature position
     * @return the mother nature location
     * @throws TimeHasEndedException launched when the available time for the response has ended
     * @throws ClientDisconnectedException launched if the client disconnects from the game
     * @throws MalformedMessageException launched if the message isn't created the correct way
     */
    public Island handle() throws TimeHasEndedException, ClientDisconnectedException, MalformedMessageException {
        while (!stop) {
            this.messageHandler.read(PLAYER_MOVE.getTiming());
            String string = this.messageHandler.getMessagePayloadFromStream(ISLAND_ID.getFragment());
            if (string.equals("stop")) {
                stop = true;
            } else {
                for (Island island : game.getIslands()) {
                    if (game.getMotherNaturePosition().equals(island)) {
                        MNIsland = island;
                    }
                }
            }
        }
        return MNIsland;
    }
}

