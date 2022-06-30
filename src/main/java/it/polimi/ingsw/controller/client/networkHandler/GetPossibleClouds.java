package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.view.asset.exception.AssetErrorException;
import it.polimi.ingsw.view.asset.game.Cloud;
import it.polimi.ingsw.view.asset.game.Game;

import java.util.ArrayList;

import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.CLOUD_ID;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.PAYLOAD_SIZE;

/**
 * @author Luca Muroni
 * @author Sara Mucci
 * Class that implements the message to get the possible clouds
 */
public class GetPossibleClouds {
    MessageHandler messageHandler;
    ArrayList<Cloud> clouds;
    Game game;

    /**
     * Constructor of the class
     * @param messageHandler is the handler of messages
     * @param game is the current game
     */
    public GetPossibleClouds(MessageHandler messageHandler, Game game) {
        this.messageHandler = messageHandler;
        this.game = game;
        this.clouds = new ArrayList<>();
    }

    /**
     * Method that handles the exchange of messages
     * @throws AssetErrorException when an object of the game isn't found in the asset
     * @throws MalformedMessageException when a received message isn't correct
     * @throws ClientDisconnectedException when the player disconnects from the game
     */
    public ArrayList<Cloud> handle() throws ClientDisconnectedException, MalformedMessageException, AssetErrorException {
        this.messageHandler.read();
        int num = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(PAYLOAD_SIZE.getFragment()));
        for (int i = 0; i<num; i++) {
            this.messageHandler.read();
            int result = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(CLOUD_ID.getFragment()));
            boolean check = false;
            for (Cloud cloud: game.getClouds()) {
                if (result == cloud.getId()) {
                    clouds.add(cloud);
                    check = true;
                    break;
                }
            }
            if (!check)
                throw new AssetErrorException();
        }
        return clouds;
    }
}