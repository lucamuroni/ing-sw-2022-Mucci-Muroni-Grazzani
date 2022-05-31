package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.controller.networking.messageParts.ConnectionTimings;
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
     * Class constructor
     * @param color represents the color to be sent
     * @param messageHandler represents the messageHandler used for the message
     */
    public SendStudentColor(PawnColor color, MessageHandler messageHandler) {
        this.color = color;
        this.messageHandler = messageHandler;
    }

    /**
     * Method that handles the messages to send the color of the student moved
     * @throws MalformedMessageException launched if the message isn't created the correct way
     * @throws FlowErrorException launched when the client sends an unexpected response
     * @throws TimeHasEndedException launched when the available time for the response has ended
     */
    public void handle() throws MalformedMessageException, FlowErrorException, TimeHasEndedException, ClientDisconnectedException {
        int topicId = this.messageHandler.getNewUniqueTopicID();
        Message message = new Message(STUDENT_COLOR.getFragment(), color.toString(), topicId);
        this.messageHandler.write(message);
        this.messageHandler.writeOutAndWait(ConnectionTimings.RESPONSE.getTiming());
        if(!(this.messageHandler.getMessagesUniqueTopic() == topicId)) {
            throw  new MalformedMessageException();
        }
        this.messageHandler.assertOnEquals(OK.getFragment(), STUDENT_COLOR.getFragment());
    }
}