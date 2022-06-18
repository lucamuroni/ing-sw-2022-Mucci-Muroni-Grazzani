package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;

import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.OK;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.STUDENT_LOCATION;

/**
 * @author Luca Muroni
 * @author Sara Mucci
 * Class that implements the message to send the new location of the moved student
 */
public class SendStudentLocation {
    int location;
    MessageHandler messageHandler;

    /**
     * Class constructor
     * @param location represents the student's new location
     * @param messageHandler represents the messageHandles used for the message
     */
    public SendStudentLocation(int location, MessageHandler messageHandler) {
        this.location = location;
        this.messageHandler = messageHandler;
    }

    /**
     * Method that handles the messages to send the new location of the moved student
     * @throws MalformedMessageException launched if the message isn't created the correct way
     * @throws FlowErrorException launched when the client sends an unexpected response
     */
    public void handle() throws MalformedMessageException, FlowErrorException, ClientDisconnectedException {
        int topicId = this.messageHandler.getNewUniqueTopicID();
        Message message = new Message(STUDENT_LOCATION.getFragment(), Integer.toString(location), topicId);
        this.messageHandler.write(message);
        this.messageHandler.writeOutAndWait();
        this.messageHandler.assertOnEquals(OK.getFragment(), STUDENT_LOCATION.getFragment());
    }
}

