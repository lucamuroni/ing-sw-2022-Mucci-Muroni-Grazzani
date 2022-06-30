package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import java.util.ArrayList;

import it.polimi.ingsw.view.asset.exception.AssetErrorException;
import it.polimi.ingsw.view.asset.game.Cloud;
import static it.polimi.ingsw.controller.networking.messageParts.ConnectionTimings.PLAYER_MOVE;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.CLOUD_ID;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.PAYLOAD_SIZE;
import it.polimi.ingsw.view.asset.game.Game;

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
     * Class constructor
     * @param messageHandler represents the messageHandler used for the message
     * @param game represents the current game
     */
    public GetPossibleClouds(MessageHandler messageHandler, Game game) {
        this.messageHandler = messageHandler;
        this.game = game;
        this.clouds = new ArrayList<>();
    }

    /**
     * Method that handles the messages to get the available clouds
     * @return the arraylist of possible clouds
     * @throws ClientDisconnectedException launched if the client disconnects from the game
     * @throws MalformedMessageException launched if the message isn't created the correct way
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