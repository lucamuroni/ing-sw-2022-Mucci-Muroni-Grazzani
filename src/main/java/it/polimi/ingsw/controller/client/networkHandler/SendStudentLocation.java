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
     * Constructor of the class
     * @param location represents the student's new location
     * @param messageHandler is the handler of messages
     */
    public SendStudentLocation(int location, MessageHandler messageHandler) {
        this.location = location;
        this.messageHandler = messageHandler;
    }

    /**
     * Method that handles the exchange of messages
     * @throws ClientDisconnectedException when the player disconnects from the game
     * @throws FlowErrorException when there is an error in the synchronization
     * @throws MalformedMessageException when a received message isn't correct
     */
    public void handle() throws ClientDisconnectedException, FlowErrorException, MalformedMessageException {
        int topicId = this.messageHandler.getNewUniqueTopicID();
        Message message = new Message(STUDENT_LOCATION.getFragment(), Integer.toString(location), topicId);
        this.messageHandler.write(message);
        this.messageHandler.writeOutAndWait();
        this.messageHandler.assertOnEquals(OK.getFragment(), STUDENT_LOCATION.getFragment());
    }
}