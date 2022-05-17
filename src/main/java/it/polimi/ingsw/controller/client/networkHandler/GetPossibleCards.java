package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.model.AssistantCard;
import java.util.ArrayList;

/**
 * @author Sara Mucci
 * Class that implements the message to get the possible assistant cards
 */
public class GetPossibleCards {
    MessageHandler messageHandler;

    /**
     * Class constructor
     * @param messageHandler represents the messageHandler used for the message
     */
    public GetPossibleCards(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    public ArrayList<AssistantCard> handle() throws TimeHasEndedException, ClientDisconnectedException {
        ArrayList<Message> messages = new ArrayList<Message>();
        int topicId = this.messageHandler.getNewUniqueTopicID();
        this.messageHandler.read(10000);
        ArrayList<AssistantCard> cards = ;    //Mi serve un metodo per accedere ai messaggi che ottengo con la read
        return cards;
    }
}
