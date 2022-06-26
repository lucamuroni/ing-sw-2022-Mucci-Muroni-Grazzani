package it.polimi.ingsw.controller.server.virtualView;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;

import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.*;

public class SendCoins {
    private final MessageHandler messageHandler;
    private final int coins;

    public SendCoins(MessageHandler messageHandler, int coins) {
        this.messageHandler = messageHandler;
        this.coins = coins;
    }

    public void handle() throws MalformedMessageException, ClientDisconnectedException, FlowErrorException {
        int topicId = this.messageHandler.getNewUniqueTopicID();
        Message message = new Message(COIN.getFragment(), String.valueOf(coins), topicId);
        this.messageHandler.write(message);
        this.messageHandler.writeOutAndWait();
        this.messageHandler.assertOnEquals(OK.getFragment(), COIN.getFragment());
    }
}
