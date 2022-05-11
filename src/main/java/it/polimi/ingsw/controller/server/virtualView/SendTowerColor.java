package it.polimi.ingsw.controller.server.virtualView;

import it.polimi.ingsw.controller.networking.ConnectionTimings;
import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import java.util.ArrayList;
import static it.polimi.ingsw.controller.networking.MessageFragment.OK;
import static it.polimi.ingsw.controller.networking.MessageFragment.TOWER_COLOR;

/**
 * @author Luca Muroni
 * Class that implements the message to sent the color associated to the current player
 */
public class SendTowerColor {
    TowerColor color;
    MessageHandler messageHandler;

    /**
     * Class constructor
     * @param color represents the color to be sent
     * @param messageHandler represents the messageHandler used for the message
     */
    public SendTowerColor(TowerColor color, MessageHandler messageHandler){
        this.color = color;
        this.messageHandler = messageHandler;
    }

    /**
     * Method that handles the message exchange
     * @throws MalformedMessageException launched if the message isn't created in the correct way
     * @throws TimeHasEndedException launched when the available time for the response has ended
     * @throws ClientDisconnectedException launched if the client disconnects from the game
     * @throws FlowErrorException launched when the client sends an unexpected response
     */
    public void handle() throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException, FlowErrorException {
        ArrayList<Message> messages = new ArrayList<Message>();
        int topicId = this.messageHandler.getNewUniqueTopicID();
        messages.add(new Message(TOWER_COLOR.getFragment(), color.getColor(), topicId));
        this.messageHandler.write(messages);
        messages.clear();
        this.messageHandler.writeOutAndWait(ConnectionTimings.RESPONSE.getTiming());
        this.messageHandler.assertOnEquals(OK.getFragment(), TOWER_COLOR.getFragment());
    }
}
