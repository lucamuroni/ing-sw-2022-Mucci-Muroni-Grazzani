package it.polimi.ingsw.controller.server.virtualView;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;

import java.util.ArrayList;

import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.*;


/**
 * @author Sara Mucci
 * Class that implements the message to send the username of the winner/the usernames of the winners in case of a draw
 */
public class SendWinner {
    ArrayList<String> usernames;
    MessageHandler messageHandler;

    /**
     * Class constructor
     * @param names represents the players' usernames
     * @param messageHandler represents the message-handler used for the message
     */
    public SendWinner(ArrayList<String> names, MessageHandler messageHandler) {
        this.usernames = names;
        this.messageHandler = messageHandler;
    }

    /**
     * Method that handles the message exchange
     * @throws MalformedMessageException launched if the message isn't created the correct way
     * @throws ClientDisconnectedException launched if the client disconnects
     * @throws FlowErrorException launched when the client sends an unexpected response
     */
    public void handle() throws MalformedMessageException, ClientDisconnectedException, FlowErrorException {
        int topicId = this.messageHandler.getNewUniqueTopicID();
        int size = this.usernames.size();
        Message message = new Message(PAYLOAD_SIZE.getFragment(), String.valueOf(size),topicId);
        this.messageHandler.write(message);
        this.messageHandler.writeOut();
        for (String string : this.usernames) {
            message = new Message(WINNER.getFragment(), string, topicId);
            this.messageHandler.write(message);
            this.messageHandler.writeOut();
        }
        this.messageHandler.read();
        if (!(this.messageHandler.getMessagesUniqueTopic() == topicId)) {
            throw new MalformedMessageException();
        }
        this.messageHandler.assertOnEquals(OK.getFragment(), WINNER.getFragment());
    }
}
