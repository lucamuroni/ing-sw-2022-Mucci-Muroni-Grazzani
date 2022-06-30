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
 * Class that implements the message to send the color of the tower associated with the current player
 */
public class SendTowerColor {
    Gamer gamer;
    MessageHandler messageHandler;

    /**
     * Constructor of the class
     * @param gamer represents the current player
     * @param messageHandler is the handler of messages
     */
    public SendTowerColor(Gamer gamer, MessageHandler messageHandler){
        this.gamer = gamer;
        this.messageHandler = messageHandler;
    }

    /**
     * Method that handles the exchange of messages
     * @throws ClientDisconnectedException when the player disconnects from the game
     * @throws FlowErrorException when there is an error in the synchronization
     * @throws MalformedMessageException when a received message isn't correct
     */
    public void handle() throws ClientDisconnectedException, FlowErrorException, MalformedMessageException {
        ArrayList<Message> messages = new ArrayList<>();
        int topicId = this.messageHandler.getNewUniqueTopicID();
        messages.add(new Message(OWNER.getFragment(), String.valueOf(this.gamer.getToken()), topicId));
        messages.add(new Message(TOWER_COLOR.getFragment(), this.gamer.getTowerColor().toString(), topicId));
        this.messageHandler.write(messages);
        this.messageHandler.writeOutAndWait();
        this.messageHandler.assertOnEquals(OK.getFragment(), TOWER_COLOR.getFragment());
    }
}