package it.polimi.ingsw.controller.server.virtualView;

import it.polimi.ingsw.controller.networking.ConnectionTimings;
import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import java.util.ArrayList;
import static it.polimi.ingsw.controller.networking.MessageFragment.STUDENT_LOCATION;

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
        ArrayList<Message> messages = new ArrayList<Message>();
        int topicId = this.messageHandler.getNewUniqueTopicID();
        messages.add(new Message(STUDENT_LOCATION.getFragment(), "", topicId));
        this.messageHandler.write(messages);
        messages.clear();
        this.messageHandler.writeOutAndWait(ConnectionTimings.PLAYER_MOVE.getTiming());
        //TODO : modificare funzione in modo tale che restituisca un' isola e non un intero (passando anche come parametro l'array di isole)
        int result = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(STUDENT_LOCATION.getFragment()));
        return result;
    }
}
