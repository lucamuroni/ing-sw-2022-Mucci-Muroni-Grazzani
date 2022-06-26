package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;

import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.ANSWER;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.OK;

public class SendAnswer {
    private final MessageHandler messageHandler;
    private final boolean answer;

    public SendAnswer(MessageHandler messageHandler, boolean answer) {
        this.messageHandler = messageHandler;
        this.answer = answer;
    }

    public void handle() throws FlowErrorException, MalformedMessageException, ClientDisconnectedException {
        int topic = this.messageHandler.getNewUniqueTopicID();
        Message message = new Message(ANSWER.getFragment(), String.valueOf(answer), topic);
        this.messageHandler.write(message);
        this.messageHandler.writeOutAndWait();
        this.messageHandler.assertOnEquals(OK.getFragment(), ANSWER.getFragment());
    }
}
