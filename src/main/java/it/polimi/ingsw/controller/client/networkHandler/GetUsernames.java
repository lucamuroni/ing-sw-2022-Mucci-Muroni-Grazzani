package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.messageParts.ConnectionTimings;
import it.polimi.ingsw.view.asset.game.Game;
import it.polimi.ingsw.view.asset.game.Gamer;

import java.util.ArrayList;

import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.*;

public class GetUsernames {
    private MessageHandler messageHandler;
    private Game game;

    public GetUsernames(MessageHandler messageHandler, Game game) {
        this.messageHandler = messageHandler;
        this.game = game;
    }

    public void handle() throws ClientDisconnectedException, MalformedMessageException {
        this.messageHandler.read();
        int id = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(PLAYER_ID.getFragment()));
        String username = this.messageHandler.getMessagePayloadFromStream(PLAYER_NAME.getFragment());
        int topicId = this.messageHandler.getMessagesUniqueTopic();
        Message message = new Message(PLAYER.getFragment(), OK.getFragment(), topicId);
        this.messageHandler.write(message);
        this.messageHandler.writeOut();
        Gamer gamer = new Gamer(id);
        gamer.setUsername(username,false);
        this.game.getGamers().add(gamer);
    }
}
