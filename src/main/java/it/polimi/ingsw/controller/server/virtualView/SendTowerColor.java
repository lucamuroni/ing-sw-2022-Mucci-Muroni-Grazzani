package it.polimi.ingsw.controller.server.virtualView;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.model.gamer.Gamer;

import java.util.ArrayList;

import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.*;

/**
 * @author Luca Muroni
 * Class that implements the message to send the color associated to the current player
 */
public class SendTowerColor {
    Gamer gamer;
    MessageHandler messageHandler;

    /**
     * Class constructor
     * @param gamer represents the current player
     * @param messageHandler represents the messageHandler used for the message
     */
    public SendTowerColor(Gamer gamer, MessageHandler messageHandler){
        this.gamer = gamer;
        this.messageHandler = messageHandler;
    }

    /**
     * Method that handles the message exchange
     * @throws MalformedMessageException launched if the message isn't created in the correct way
     * @throws ClientDisconnectedException launched if the client disconnects from the game
     * @throws FlowErrorException launched when the client sends an unexpected response
     */
    public void handle() throws MalformedMessageException, ClientDisconnectedException, FlowErrorException {
        ArrayList<Message> messages = new ArrayList<>();
        int topicId = this.messageHandler.getNewUniqueTopicID();
        messages.add(new Message(OWNER.getFragment(), String.valueOf(this.gamer.getToken()), topicId));
        messages.add(new Message(TOWER_COLOR.getFragment(), this.gamer.getTowerColor().toString(), topicId));
        this.messageHandler.write(messages);
        this.messageHandler.writeOutAndWait();
        this.messageHandler.assertOnEquals(OK.getFragment(), TOWER_COLOR.getFragment());
    }
}
