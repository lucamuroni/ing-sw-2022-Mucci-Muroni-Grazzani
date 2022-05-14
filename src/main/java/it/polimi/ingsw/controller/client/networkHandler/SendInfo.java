package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.Player;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.messageParts.ConnectionTimings;
import it.polimi.ingsw.model.game.Game;
import java.util.ArrayList;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.OK;

/**
 * @author Sara Mucci
 * Class that implements the mssage to send to the server the infos about the player and the game
 */
public class SendInfo {
    Player player;
    Game game;
    MessageHandler messageHandler;

    /**
     * Class constructor
     * @param player represents the player associated to the client
     * @param game represents the game to be created
     * @param messageHandler represents the messageHandler used for the message
     */
    public SendInfo(Player player, Game game, MessageHandler messageHandler) {
        this.player = player;
        this.game = game;
        this.messageHandler = messageHandler;
    }

    public void handle() throws MalformedMessageException {
        ArrayList<Message> messages = new ArrayList<Message>();
        int topicId = this.messageHandler.getNewUniqueTopicID();
        messages.add(new Message(PLAYER.getFragment(), this.player, topicId));
        messages.add(new Message(GAME.getFragment(), this.game, topicId));
        this.messageHandler.write(messages);
        messages.clear();
        this.messageHandler.writeOutAndWait(ConnectionTimings.RESPONSE.getTimings());
        this.messageHandler.assertOnEquals(OK.getFragment(), INFO.getFragment());
    }
}
