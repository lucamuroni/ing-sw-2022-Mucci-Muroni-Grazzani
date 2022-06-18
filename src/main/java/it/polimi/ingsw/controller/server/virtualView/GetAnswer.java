package it.polimi.ingsw.controller.server.virtualView;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;

import static it.polimi.ingsw.controller.networking.messageParts.ConnectionTimings.PLAYER_MOVE;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.*;

public class GetAnswer {
    private MessageHandler messageHandler;
    public GetAnswer(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    public boolean handle() throws ClientDisconnectedException, MalformedMessageException {
        this.messageHandler.read();
        boolean result;
        String answer = this.messageHandler.getMessagePayloadFromStream(ANSWER.getFragment());
        if (answer.equals("true"))
            result = true;
        else
            result = false;
        int topicId = this.messageHandler.getMessagesUniqueTopic();
        Message message = new Message(ANSWER.getFragment(), OK.getFragment(), topicId);
        this.messageHandler.write(message);
        this.messageHandler.writeOut();
        return result;
    }
}
