package it.polimi.ingsw.controller.server.virtualView;

import it.polimi.ingsw.controller.networking.messageParts.ConnectionTimings;
import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.*;

/**
 * @author Luca Muroni
 * Class that implements the message to get the location of the student the current player moves
 */
public class GetMovedStudentLocation {
    MessageHandler messageHandler;

    /**
     * Class constructor
     * @param messageHandler represents the messageHandler used for the message
     */
    public GetMovedStudentLocation(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    /**
     * Method that handles the message exchange
     * @return an int. If it is 0, it represents the dashboard's hall; otherwise, it represents the island position in the arrayList
     * @throws MalformedMessageException launched if the message isn't created in the correct way
     * @throws TimeHasEndedException launched when the available time for the response has ended
     * @throws ClientDisconnectedException launched if the client disconnects from the game
     */
    public int handle() throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException {
        this.messageHandler.read(ConnectionTimings.PLAYER_MOVE.getTiming());
        int result = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(STUDENT_LOCATION.getFragment()));
        int topicId = this.messageHandler.getMessagesUniqueTopic();
        Message message = new Message(STUDENT_LOCATION.getFragment(), OK.getFragment(), topicId);
        this.messageHandler.write(message);
        this.messageHandler.writeOut();
        return result;
    }
}
