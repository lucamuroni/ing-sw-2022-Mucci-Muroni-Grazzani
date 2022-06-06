package it.polimi.ingsw.controller.server.virtualView;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.controller.networking.messageParts.ConnectionTimings;

import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.OK;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.PLAYER_NAME;

public class SendUsernames {
    private MessageHandler messageHandler;
    private String username;

    public SendUsernames(String username, MessageHandler messageHandler) {
        this.username = username;
        this.messageHandler = messageHandler;
    }

    public void handle() throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException, FlowErrorException {
        int topicId = this.messageHandler.getNewUniqueTopicID();
        Message message = new Message(PLAYER_NAME.getFragment(), username, topicId);
        this.messageHandler.write(message);
        this.messageHandler.writeOutAndWait(ConnectionTimings.RESPONSE.getTiming());
        //if (!(this.messageHandler.getMessagesUniqueTopic() == topicId)) {
        //    throw new MalformedMessageException();
        //}
        this.messageHandler.assertOnEquals(OK.getFragment(), PLAYER_NAME.getFragment());
    }
}
