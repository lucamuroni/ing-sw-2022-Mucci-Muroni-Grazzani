package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.Player;
import java.util.ArrayList;

/**
 * @author Sara Mucci
 * Class that implements the message to get the winner (or winner in case of a draw) for the game
 */
public class GetWinner {
    MessageHandler messageHandler;

    /**
     * Class constructor
     * @param messageHandler represents the messageHandler used for the message
     */
    public GetWinner(MessageHandler messageHandler)  {
        this.messageHandler = messageHandler;
    }

    public ArrayList<Player> handle() {
        ArrayList<Message> messages = new ArrayList<Message>();
        int topicId = this.messageHandler.getNewUniqueTopicID();
    }
}
//TODO: da fare