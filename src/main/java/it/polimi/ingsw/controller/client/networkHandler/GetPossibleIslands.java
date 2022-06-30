package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.view.asset.exception.AssetErrorException;
import it.polimi.ingsw.view.asset.game.Game;
import it.polimi.ingsw.view.asset.game.Island;

import java.util.ArrayList;

import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.ISLAND_ID;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.PAYLOAD_SIZE;

/**
 * @author Luca Muroni
 * @author Sara Mucci
 * Class that implements the message to get the possible islands on which mother nature can move on
 */
public class GetPossibleIslands {
    MessageHandler messageHandler;
    ArrayList<Island> islands;
    Game game;

    /**
     * Constructor of the class
     * @param messageHandler is the handler of messages
     * @param game is the current game
     */
    public GetPossibleIslands(MessageHandler messageHandler, Game game) {
        this.messageHandler = messageHandler;
        this.game = game;
        this.islands = new ArrayList<>();
    }

    /**
     * Method that handles the exchange of messages
     * @throws AssetErrorException when an object of the game isn't found in the asset
     * @throws MalformedMessageException when a received message isn't correct
     * @throws ClientDisconnectedException when the player disconnects from the game
     */
    public ArrayList<Island> handle() throws AssetErrorException, MalformedMessageException, ClientDisconnectedException {
        this.messageHandler.read();
        int num = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(PAYLOAD_SIZE.getFragment()));
        for (int i = 0; i<num; i++) {
            this.messageHandler.read();
            int result = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(ISLAND_ID.getFragment()));
            boolean check = false;
            for (Island island: game.getIslands()) {
                if (result == island.getId()) {
                    islands.add(island);
                    check = true;
                }
            }
            if (!check)
                throw new AssetErrorException();
        }
        return islands;
    }
}
