package it.polimi.ingsw.controller.server.virtualView;

import it.polimi.ingsw.controller.networking.ConnectionTimings;
import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.model.pawn.PawnColor;

import java.util.ArrayList;

import static it.polimi.ingsw.controller.networking.MessageFragment.STUDENT_COLOR;

public class GetMovedStudentColor {
    private MessageHandler messageHandler;

    public GetMovedStudentColor(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    public PawnColor handle() throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException {
        ArrayList<Message> messages = new ArrayList<Message>();
        int topicId = this.messageHandler.getNewUniqueTopicID();
        messages.add(new Message(STUDENT_COLOR.getFragment(), "", topicId));
        this.messageHandler.write(messages);
        messages.clear();
        this.messageHandler.writeOutAndWait(ConnectionTimings.PLAYER_MOVE.getTiming());
        String studentColor = this.messageHandler.getMessagePayloadFromStream(STUDENT_COLOR.getFragment());
        PawnColor result = null;
        for (PawnColor color : PawnColor.values()) {
            //TODO: Controllare con Grazza: non sono sicuro che il controllo sia corretto
            if (color.equals(studentColor))
                result = color;
        }
        return result;
    }
}
