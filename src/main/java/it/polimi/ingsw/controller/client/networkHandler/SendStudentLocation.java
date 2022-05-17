package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.controller.networking.messageParts.ConnectionTimings;
import java.util.ArrayList;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.OK;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.STUDENT_LOCATION;

/**
 * @author Sara Mucci
 * Class that implements the message to send the new location of the moved student
 */
public class SendStudentLocation {
    int location;
    MessageHandler messageHandler;

    /**
     * Class constructor
     * @param location represents the new student's location
     * @param messageHandler represents the messageHandles used for the message
     */
    public SendStudentLocation(int location, MessageHandler messageHandler) {
        this.location = location;
        this.messageHandler = messageHandler;
    }

    /**
     * Method that handles the messages to send the location of the moved student
     * @throws MalformedMessageException launched if the message isn't created the correct way
     * @throws FlowErrorException launched when the client sends an unexpected response
     * @throws TimeHasEndedException launched when the available time for the response has ended
     */
    public void handle() throws MalformedMessageException, FlowErrorException, TimeHasEndedException {
        ArrayList<Message> messages = new ArrayList<Message>();
        int topicId = this.messageHandler.getMessagesUniqueTopic(); //TODO: è giusto prendere il topicId del messaggio che manda il server?
        messages.add(new Message(STUDENT_LOCATION.getFragment(), Integer.toString(location), topicId));
        this.messageHandler.write(messages);
        messages.clear();
        this.messageHandler.writeOutAndWait(ConnectionTimings.RESPONSE.getTiming());
        this.messageHandler.assertOnEquals(OK.getFragment(), STUDENT_LOCATION.getFragment());
        //TODO: nel server non si deve aggiungere un messaggio OK per confermare la ricezione?
    }
}
