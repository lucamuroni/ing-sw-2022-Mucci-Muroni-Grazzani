package it.polimi.ingsw.controller.server.virtualView;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.Player;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;

import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.*;

public class SendActiveUsername {
    private MessageHandler messageHandler;
    private Player player;

    public SendActiveUsername(MessageHandler messageHandler, Player player) {
        this.messageHandler = messageHandler;
        this.player = player;
    }

    public void handle() throws MalformedMessageException, ClientDisconnectedException, FlowErrorException {
        int topicId = this.messageHandler.getNewUniqueTopicID();
        Message message = new Message(PLAYER_NAME.getFragment(), player.getUsername(), topicId);
        this.messageHandler.write(message);
        this.messageHandler.writeOutAndWait();
        this.messageHandler.assertOnEquals(OK.getFragment(), PLAYER_NAME.getFragment());
    }
}
