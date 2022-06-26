package it.polimi.ingsw.controller.server.virtualView;

import it.polimi.ingsw.controller.networking.messageParts.ConnectionTimings;
import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.model.pawn.PawnColor;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.*;

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
     * @throws ClientDisconnectedException launched if the client disconnects from the game
     */
    public PawnColor handle() throws MalformedMessageException, ClientDisconnectedException {
        this.messageHandler.read();
        String studentColor = this.messageHandler.getMessagePayloadFromStream(STUDENT_COLOR.getFragment());
        PawnColor result = null;
        for (PawnColor color : PawnColor.values()) {
            if (color.toString().equals(studentColor))
                result = color;
        }
        int topicId = this.messageHandler.getMessagesUniqueTopic();
        Message message = new Message(STUDENT_COLOR.getFragment(), OK.getFragment(), topicId);
        this.messageHandler.write(message);
        this.messageHandler.writeOut();
        return result;
    }
}
