package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.view.asset.game.Game;

import java.util.ArrayList;

import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.*;

/**
 * @author Davide Grazzani
 * This class is used to send to server all the info asked the player at the start of the game
 */
public class BroadcastPlayerInfo {
    private final MessageHandler messageHandler;
    private final Game game;

    /**
     * Constructor of the class
     * @param messageHandler is the handler of messages
     * @param game is the current game
     */
    public BroadcastPlayerInfo(MessageHandler messageHandler, Game game){
        this.messageHandler = messageHandler;
        this.game = game;
    }

    /**
     * Method that handles the exchange of messages
     * @throws MalformedMessageException when a received message isn't correct
     */
    public void handle() throws MalformedMessageException {
        int uniqueId = this.messageHandler.getMessagesUniqueTopic();
        ArrayList<Message> msg = new ArrayList<>();
        msg.add(new Message(AUTH_ID.getFragment(),String.valueOf(this.game.getSelf().getId()),uniqueId));
        msg.add(new Message(PLAYER_NAME.getFragment(),this.game.getSelf().getUsername(),uniqueId));
        msg.add(new Message(GAME_TYPE.getFragment(),this.game.getGameType(),uniqueId));
        msg.add(new Message(LOBBY_SIZE.getFragment(),String.valueOf(this.game.getLobbySize()),uniqueId));
        this.messageHandler.write(msg);
        this.messageHandler.writeOut();
    }
}