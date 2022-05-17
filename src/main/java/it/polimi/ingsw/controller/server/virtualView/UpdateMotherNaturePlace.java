package it.polimi.ingsw.controller.server.virtualView;

import it.polimi.ingsw.controller.networking.ConnectionTimings;
import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.model.Island;
import java.util.ArrayList;
import static it.polimi.ingsw.controller.networking.MessageFragment.MN_LOCATION;
import static it.polimi.ingsw.controller.networking.MessageFragment.OK;
import static java.lang.Integer.valueOf;

//TODO : classe che gestisce l'aggiornamento della posizione di madre natura

/**
 * @author Davide Grazzani
 * @author Luca Muroni
 * Class that implements the message to update the mother nature location
 */
class UpdateMotherNaturePlace {
    Island island;
    MessageHandler messageHandler;

    /**
     * Class constructor
     * @param island represents the new mother nature location
     * @param messageHandler represents the messageHandler used for the message
     */
    public UpdateMotherNaturePlace(Island island, MessageHandler messageHandler){
        this.island = island;
        this.messageHandler = messageHandler;
    }

    /**
     * Method that handles the message exchange
     * @throws MalformedMessageException launched if the message isn't created in the correct way
     * @throws TimeHasEndedException launched when the available time for the response has ended
     * @throws ClientDisconnectedException launched if the client disconnects from the game
     * @throws FlowErrorException launched when the client sends an unexpected response
     */
    public void handle() throws MalformedMessageException, TimeHasEndedException, ClientDisconnectedException, FlowErrorException {
        ArrayList<Message> messages = new ArrayList<>();
        int topicId = this.messageHandler.getNewUniqueTopicID();
        Integer id = valueOf(island.getId());
        messages.add(new Message(MN_LOCATION.getFragment(), id.toString(), topicId));
        this.messageHandler.write(messages);
        messages.clear();
        this.messageHandler.writeOutAndWait(ConnectionTimings.RESPONSE.getTiming());
        this.messageHandler.assertOnEquals(OK.getFragment(), MN_LOCATION.getFragment());
    }
}
