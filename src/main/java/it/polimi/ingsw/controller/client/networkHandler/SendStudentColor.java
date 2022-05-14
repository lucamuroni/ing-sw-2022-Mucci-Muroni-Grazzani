package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.model.pawn.PawnColor;
import java.util.ArrayList;

/**
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

    public void handle() {
        ArrayList<Message> messages = new ArrayList<Message>();
        int topicId = this.messageHandler.getNewUniqueTopicID();

    }
}
