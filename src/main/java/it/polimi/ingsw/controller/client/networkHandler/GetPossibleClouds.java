package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import java.util.ArrayList;
import it.polimi.ingsw.view.asset.game.Cloud;
import static it.polimi.ingsw.controller.networking.messageParts.ConnectionTimings.PLAYER_MOVE;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.CLOUD;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.CLOUD_ID;

import it.polimi.ingsw.view.asset.game.Game;

/**
 * @author Sara Mucci
 * Class that implements the message to get the possible clouds
 */
public class GetPossibleClouds {
    MessageHandler messageHandler;
    Boolean stop = false;
    ArrayList<Cloud> clouds;
    Game game;

    /**
     * Class constructor
     * @param messageHandler represents the messageHandler used for the message
     */
    public GetPossibleClouds(MessageHandler messageHandler, Game game) {
        this.messageHandler = messageHandler;
        this.game = game;
        this.clouds = new ArrayList<>();
    }

    /**
     * Method that handles the messages to get the available clouds
     * @return the arraylist of possible clouds
     * @throws TimeHasEndedException launched when the available time for the response has ended
     * @throws ClientDisconnectedException launched if the client disconnects from the game
     * @throws MalformedMessageException launched if the message isn't created the correct way
     */
    public ArrayList<Cloud> handle() throws TimeHasEndedException, ClientDisconnectedException, MalformedMessageException {
        this.messageHandler.read(PLAYER_MOVE.getTiming());
        int result = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(CLOUD_ID.getFragment()));
        //TODO: il metodo non può ricevere più di una nuvola alla volta dato che il server non può inviare più messaggi con lo stesso header
            for (Cloud cloud: game.getClouds()) {
                if (result == cloud.getId()) {
                        clouds.add(cloud);
                    }
                }


        return clouds;
    }
}
