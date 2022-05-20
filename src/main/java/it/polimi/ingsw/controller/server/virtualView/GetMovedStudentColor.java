package it.polimi.ingsw.controller.server.virtualView;

import it.polimi.ingsw.controller.networking.messageParts.ConnectionTimings;
import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.model.pawn.PawnColor;

import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.STUDENT_COLOR;

/**
 * @author Luca Muroni
 * @author Sara Mucci
 * Class that implements the message to get the color of the student the current player moves
 */
public class GetMovedStudentColor {
    MessageHandler messageHandler;

    /**
     * Class constructor
     * @param messageHandler represents the messageHandler used for the message
     */
    public GetMovedStudentColor(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    /**
     * Method that handles the message exchange
     * @return the color of the chosen student
     * @throws MalformedMessageException launched if the message isn't created in the correct way
     * @throws TimeHasEndedException launched when the available time for the response has ended
     * @throws ClientDisconnectedException launched if the client disconnects from the game
     */
    public PawnColor handle() throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException {
        int topicId = this.messageHandler.getNewUniqueTopicID();
        Message message = new Message(STUDENT_COLOR.getFragment(), "", topicId);
        this.messageHandler.write(message);
        this.messageHandler.writeOutAndWait(ConnectionTimings.PLAYER_MOVE.getTiming());
        if (!(this.messageHandler.getMessagesUniqueTopic() == topicId)) {
            throw  new MalformedMessageException();
        }
        String studentColor = this.messageHandler.getMessagePayloadFromStream(STUDENT_COLOR.getFragment());
        PawnColor result = null;
        for (PawnColor color : PawnColor.values()) {
            if (color.toString().equals(studentColor))
                result = color;
        }
        return result;
    }
}
