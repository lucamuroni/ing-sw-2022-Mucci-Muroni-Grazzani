package it.polimi.ingsw.controller.server.virtualView;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.Player;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.controller.networking.messageParts.ConnectionTimings;

import java.util.ArrayList;

import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.*;

public class SendUsernames {
    private MessageHandler messageHandler;
    private Player player;

    public SendUsernames(Player player, MessageHandler messageHandler) {
        this.player = player;
        this.messageHandler = messageHandler;
    }

    public void handle() throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException, FlowErrorException {
        ArrayList<Message> messages = new ArrayList<>();
        int topicId = this.messageHandler.getNewUniqueTopicID();
        messages.add(new Message(PLAYER_ID.getFragment(), String.valueOf(player.getToken()), topicId));
        messages.add(new Message(PLAYER_NAME.getFragment(), player.getUsername(), topicId));
        this.messageHandler.write(messages);
        this.messageHandler.writeOutAndWait(ConnectionTimings.RESPONSE.getTiming());
        this.messageHandler.assertOnEquals(OK.getFragment(), PLAYER.getFragment());
    }
}
