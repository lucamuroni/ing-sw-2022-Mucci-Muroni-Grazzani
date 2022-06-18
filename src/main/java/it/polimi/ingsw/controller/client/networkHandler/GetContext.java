package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.messageParts.ConnectionTimings;
import it.polimi.ingsw.controller.networking.messageParts.MessageFragment;
import it.polimi.ingsw.view.asset.exception.AssetErrorException;

import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.CONTEXT;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.OK;

public class GetContext {
    MessageHandler messageHandler;

    public GetContext(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    public String handle() throws ClientDisconnectedException, MalformedMessageException, AssetErrorException {
        this.messageHandler.read();
        String context = this.messageHandler.getMessagePayloadFromStream(CONTEXT.getFragment());
        boolean check = false;
        for (MessageFragment fragment : MessageFragment.values()) {
            if (fragment.getFragment().equals(context)) {
                check = true;
                break;
            }
        }
        if (!check)
            throw new AssetErrorException();
        int topicId = this.messageHandler.getMessagesUniqueTopic();
        Message message = new Message(CONTEXT.getFragment(), OK.getFragment(), topicId);
        this.messageHandler.write(message);
        this.messageHandler.writeOut();
        return context;
    }
}
