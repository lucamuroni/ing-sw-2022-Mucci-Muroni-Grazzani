package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.controller.networking.messageParts.ConnectionTimings;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.*;
import it.polimi.ingsw.view.asset.game.Game;

import java.util.ArrayList;


public class BroadcastPlayerInfo {
    private MessageHandler messageHandler;
    private Game game;

    public BroadcastPlayerInfo(MessageHandler messageHandler, Game game){
        this.messageHandler = messageHandler;
        this.game = game;
    }

    public void handle() throws MalformedMessageException {
        int uniqueId = this.messageHandler.getMessagesUniqueTopic();
        ArrayList<Message> msg = new ArrayList<Message>();
        msg.add(new Message(AUTH_ID.getFragment(),String.valueOf(this.game.getSelf().getId()),uniqueId));
        msg.add(new Message(PLAYER_NAME.getFragment(),this.game.getSelf().getUsername(),uniqueId));
        msg.add(new Message(AUTH_ID.getFragment(),String.valueOf(this.game.getSelf().getId()),uniqueId));
        msg.add(new Message(GAME_TYPE.getFragment(),this.game.getGameType(),uniqueId));
        msg.add(new Message(LOBBY_SIZE.getFragment(),String.valueOf(this.game.getLobbySize()),uniqueId));
        this.messageHandler.write(msg);
        this.messageHandler.writeOut();
    }
}
