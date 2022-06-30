package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.model.pawn.PawnColor;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.OK;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.STUDENT_COLOR;

/**
 * @author Luca Muroni
 * @author Sara Mucci
 * Class that implements the message to send the color of the moved student
 */
public class SendStudentColor {
    PawnColor color;
    MessageHandler messageHandler;

    /**
     * Constructor of the class
     * @param color represents the color to be sent
     * @param messageHandler is the handler of messages
     */
    public SendStudentColor(PawnColor color, MessageHandler messageHandler) {
        this.color = color;
        this.messageHandler = messageHandler;
    }

    /**
     * Method that handles the exchange of messages
     * @throws FlowErrorException when there is an error in the synchronization
     * @throws MalformedMessageException when a received message isn't correct
     */
    public void handle() throws MalformedMessageException, FlowErrorException, ClientDisconnectedException {
        int topicId = this.messageHandler.getNewUniqueTopicID();
        Message message = new Message(STUDENT_COLOR.getFragment(), color.toString(), topicId);
        this.messageHandler.write(message);
        this.messageHandler.writeOutAndWait();
        this.messageHandler.assertOnEquals(OK.getFragment(), STUDENT_COLOR.getFragment());
    }
}