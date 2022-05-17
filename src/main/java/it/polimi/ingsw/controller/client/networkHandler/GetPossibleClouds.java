package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import java.util.ArrayList;
import it.polimi.ingsw.view.asset.game.Cloud;
import static it.polimi.ingsw.controller.networking.messageParts.ConnectionTimings.PLAYER_MOVE;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.CLOUD;
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
    public GetPossibleClouds(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    /**
     * Method that handles the messages to get the available clouds
     * @return the arraylist of possible clouds
     * @throws TimeHasEndedException launched when the available time for the response has ended
     * @throws ClientDisconnectedException launched if the client disconnects from the game
     * @throws MalformedMessageException launched if the message isn't created the correct way
     */
    public ArrayList<Cloud> handle() throws TimeHasEndedException, ClientDisconnectedException, MalformedMessageException {
        while (!stop) {
            this.messageHandler.read(PLAYER_MOVE.getTiming());
            String string = this.messageHandler.getMessagePayloadFromStream(CLOUD.getFragment());
            if (string.equals("stop")) {
                stop = true;
            }
            else {
                int result = Integer.parseInt(string);
                for (Cloud cloud: game.getClouds()) {
                    if (result == cloud.getId()) {
                        clouds.add(cloud);
                    }
                }
            }
        }
        return clouds;
    }
}
