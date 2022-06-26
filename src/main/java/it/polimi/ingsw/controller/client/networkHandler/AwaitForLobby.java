package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;

import it.polimi.ingsw.controller.networking.messageParts.ConnectionTimings;
import it.polimi.ingsw.controller.networking.messageParts.MessageFragment;

public class AwaitForLobby {
    private MessageHandler messageHandler;

    public AwaitForLobby(MessageHandler messageHandler){
        this.messageHandler = messageHandler;
    }

    public void handle() throws ClientDisconnectedException, FlowErrorException, MalformedMessageException {
        this.messageHandler.read();
        this.messageHandler.assertOnEquals(MessageFragment.GREETINGS_STATUS_SUCCESFULL.getFragment(),MessageFragment.GREETINGS.getFragment());
        this.messageHandler.write(new Message(MessageFragment.GREETINGS.getFragment(), MessageFragment.OK.getFragment(), this.messageHandler.getMessagesUniqueTopic()));
        this.messageHandler.writeOut();
    }
}
