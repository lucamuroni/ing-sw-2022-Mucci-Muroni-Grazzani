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
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.PAYLOAD_SIZE;

/**
 * @author Luca Muroni
 * @author Sara Mucci
 * Class that implements the message to get the possible islands on which mother nature can move
 */
public class GetPossibleIslands {
    MessageHandler messageHandler;
    ArrayList<Island> islands;
    Game game;

    /**
     * Class constructor
     * @param messageHandler represents the messageHandler used for the message
     * @param game represents the current game
     */
    public GetPossibleIslands(MessageHandler messageHandler, Game game) {
        this.messageHandler = messageHandler;
        this.game = game;
        this.islands = new ArrayList<>();
    }

    /**
     * Method that handles the messages to get the possible islands
     * @return the arraylist of possible islands
     * @throws TimeHasEndedException launched when the available time for the response has ended
     * @throws ClientDisconnectedException launched if the client disconnects from the game
     * @throws MalformedMessageException launched if the message isn't created the correct way
     */
    public ArrayList<Island> handle() throws TimeHasEndedException, ClientDisconnectedException, MalformedMessageException {
        this.messageHandler.read(PLAYER_MOVE.getTiming());
        int num = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(PAYLOAD_SIZE.getFragment()));
        for (int i = 0; i<num; i++) {
            this.messageHandler.read(PLAYER_MOVE.getTiming());
            int result = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(ISLAND_ID.getFragment()));
            for (Island island: game.getIslands()) {
                if (result == island.getId()) {
                    islands.add(island);
                }
            }
        }
        return islands;
    }
}
