package it.polimi.ingsw.controller.server.virtualView;

import it.polimi.ingsw.controller.networking.ConnectionTimings;
import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.model.pawn.TowerColor;

import java.util.ArrayList;

import static it.polimi.ingsw.controller.networking.MessageFragment.OK;
import static it.polimi.ingsw.controller.networking.MessageFragment.TOWER_COLOR;

public class SendTowerColor {
    private TowerColor color;
    private MessageHandler messageHandler;

    public SendTowerColor(TowerColor color, MessageHandler messageHandler){
        this.color = color;
        this.messageHandler = messageHandler;
    }

    public void handle() throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException, FlowErrorException {
        ArrayList<Message> messages = new ArrayList<Message>();
        int topicId = this.messageHandler.getNewUniqueTopicID();
        messages.add(new Message(TOWER_COLOR.getFragment(), color.getColor(), topicId));
        this.messageHandler.write(messages);
        messages.clear();
        messages.addAll(this.messageHandler.writeOutAndWait(ConnectionTimings.PLAYER_MOVE.getTiming()));
        this.messageHandler.assertOnEquals(OK.getFragment(), TOWER_COLOR.getFragment(), messages);
    }
}
