package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.model.Cloud;
import it.polimi.ingsw.model.Island;
import java.util.ArrayList;

/**
 * @author Sara Mucci
 * Class that implements the message to get the possible islands where mother nature can move
 */
public class GetPossibleIslands {
    MessageHandler messageHandler;

    /**
     * Class constructor
     * @param messageHandler represents the messageHandler used for the message
     */
    public GetPossibleIslands(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    public ArrayList<Island> handle() throws TimeHasEndedException, ClientDisconnectedException {
        ArrayList<Message> messages = new ArrayList<Message>();
        int topicId = this.messageHandler.getNewUniqueTopicID();
        this.messageHandler.read(10000);
        ArrayList<Island> islands = ; //metodo per ottenere le cloud dalla read
        return islands;
    }
}
