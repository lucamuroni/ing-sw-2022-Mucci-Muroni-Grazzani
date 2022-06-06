package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.controller.networking.messageParts.ConnectionTimings;
import it.polimi.ingsw.view.asset.game.Game;
import it.polimi.ingsw.view.asset.game.Gamer;

import java.util.ArrayList;

import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.OK;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.PLAYER_NAME;

public class GetUsernames {
    private MessageHandler messageHandler;
    private Game game;

    public GetUsernames(MessageHandler messageHandler, Game game) {
        this.messageHandler = messageHandler;
        this.game = game;
    }

    public void handle() throws TimeHasEndedException, ClientDisconnectedException, MalformedMessageException {
        this.messageHandler.read(ConnectionTimings.INFINITE.getTiming());
        String username = this.messageHandler.getMessagePayloadFromStream(PLAYER_NAME.getFragment());
        int topicId = this.messageHandler.getMessagesUniqueTopic();
        Message message = new Message(PLAYER_NAME.getFragment(), OK.getFragment(), topicId);
        this.messageHandler.write(message);
        this.messageHandler.writeOut();
        ArrayList<Gamer> gamers = new ArrayList<>(this.game.getGamers());
        gamers.remove(this.game.getSelf());
        for (Gamer gamer : gamers) {
            if (gamer.getUsername() == null)
                gamer.setUsername(username);
        }
    }
}
