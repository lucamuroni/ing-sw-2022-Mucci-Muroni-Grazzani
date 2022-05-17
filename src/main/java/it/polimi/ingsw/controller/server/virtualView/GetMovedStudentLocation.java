package it.polimi.ingsw.controller.server.virtualView;

import it.polimi.ingsw.controller.networking.ConnectionTimings;
import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;

import java.util.ArrayList;

import static it.polimi.ingsw.controller.networking.MessageFragment.STUDENT_LOCATION;

public class GetMovedStudentLocation {
    private MessageHandler messageHandler;

    public GetMovedStudentLocation(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    public int handle() throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException {
        int topicId = this.messageHandler.getNewUniqueTopicID();
        Message message = new Message(STUDENT_LOCATION.getFragment(), "", topicId);
        this.messageHandler.write(message);
        this.messageHandler.writeOutAndWait(ConnectionTimings.PLAYER_MOVE.getTiming());
        if (!(this.messageHandler.getMessagesUniqueTopic() == topicId)) {
            throw new MalformedMessageException();
        }
        int result = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(STUDENT_LOCATION.getFragment()));
        return result;
    }
}
