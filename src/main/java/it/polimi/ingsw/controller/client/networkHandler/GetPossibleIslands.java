package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.view.asset.game.Game;
import it.polimi.ingsw.view.asset.game.Island;
import java.util.ArrayList;
import static it.polimi.ingsw.controller.networking.messageParts.ConnectionTimings.PLAYER_MOVE;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.ISLAND_ID;

/**
 * @author Sara Mucci
 * Class that implements the message to get the possible islands where mother nature can move
 */
public class GetPossibleIslands {
    MessageHandler messageHandler;
    Boolean stop = false;
    ArrayList<Island> islands;
    Game game;

    /**
     * Class constructor
     * @param messageHandler represents the messageHandler used for the message
     */
    public GetPossibleIslands(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    /**
     * Method that handles the messages to get the possible islands
     * @return the arraylist of possible islands
     * @throws TimeHasEndedException launched when the available time for the response has ended
     * @throws ClientDisconnectedException launched if the client disconnects from the game
     * @throws MalformedMessageException launched if the message isn't created the correct way
     */
    public ArrayList<Island> handle() throws TimeHasEndedException, ClientDisconnectedException, MalformedMessageException {
        while (!stop) {
            this.messageHandler.read(PLAYER_MOVE.getTiming());
            String string = this.messageHandler.getMessagePayloadFromStream(ISLAND_ID.getFragment());
            if (string.equals("stop")) {
                stop = true;
            }
            else {
                int result = Integer.parseInt(string);
                for (Island island: game.getIslands()) {
                    if (result == island.getId()) {
                        islands.add(island);
                    }
                }
            }
        }
        return islands;
    }
}
