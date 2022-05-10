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
        ArrayList<Message> messages = new ArrayList<Message>();
        int topicId = this.messageHandler.getNewUniqueTopicID();
        messages.add(new Message(STUDENT_LOCATION.getFragment(), "", topicId));
        this.messageHandler.write(messages);
        messages.clear();
        this.messageHandler.writeOutAndWait(ConnectionTimings.PLAYER_MOVE.getTiming());
        //TODO : modificare funzione in modo tale che restituisca un' isola e non un intero (passando anche come parametro l'array di isole
        int result = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(STUDENT_LOCATION.getFragment()));
        return result;
    }
}
