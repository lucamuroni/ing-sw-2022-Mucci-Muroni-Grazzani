package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.view.asset.game.Gamer;

import java.util.ArrayList;

import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.*;

/**
 * @author Luca Muroni
 * @author Sara Mucci
 * Class that implements the mssage to send to the server the infos about the player and the game
 * This class is used at the beginning of the connection phase
 */
public class SendInfo {
    Gamer gamer;
    int players;
    String lobby;
    String gameType;
    MessageHandler messageHandler;

    /**
     * Constructor of the class
     * @param gamer represents the player associated to the client
     * @param gameType represents the type of game the player wants to play
     * @param messageHandler is the handler of messages
     */
    public SendInfo(Gamer gamer, String gameType, int players, String lobby, MessageHandler messageHandler) {
        this.gamer = gamer;
        this.lobby = lobby;
        this.players = players;
        this.gameType = gameType;
        this.messageHandler = messageHandler;
    }

    /**
     * Method that handles the exchange of messages
     * @throws MalformedMessageException when a received message isn't correct
     */
    public void handle() throws MalformedMessageException {
        ArrayList<Message> messages = new ArrayList<>();
        int topicId = this.messageHandler.getMessagesUniqueTopic();
        messages.add(new Message(PLAYER_NAME.getFragment(), this.gamer.getUsername(), topicId));
        messages.add(new Message(GAME_TYPE.getFragment(), this.gameType, topicId));
        messages.add(new Message(LOBBY_SIZE.getFragment(), String.valueOf(this.players), topicId));
        messages.add(new Message(LOBBY.getFragment(), this.lobby, topicId));
        this.messageHandler.write(messages);
        this.messageHandler.writeOut();
    }
}