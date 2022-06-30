package it.polimi.ingsw.controller.server.virtualView;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.*;

/**
 * @author Luca Muroni
 * Class that implements the message to get the location of the student the current player moves
 */
public class GetMovedStudentLocation {
    MessageHandler messageHandler;

    /**
     * Constructor of the class
     * @param messageHandler is the handler of messages
     */
    public GetMovedStudentLocation(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    /**
     * Method that handles the exchange of messages
     * @return an int: o for dashboard's hall, 1-12 for an island
     * @throws ClientDisconnectedException when the player disconnects from the game
     *      * @throws MalformedMessageException when a received message isn't correct
     */
    public int handle() throws ClientDisconnectedException, MalformedMessageException {
        this.messageHandler.read();
        int result = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(STUDENT_LOCATION.getFragment()));
        int id = this.messageHandler.getMessagesUniqueTopic();
        Message message = new Message(STUDENT_LOCATION.getFragment(), OK.getFragment(), id);
        this.messageHandler.write(message);
        this.messageHandler.writeOut();
        return result;
    }
}