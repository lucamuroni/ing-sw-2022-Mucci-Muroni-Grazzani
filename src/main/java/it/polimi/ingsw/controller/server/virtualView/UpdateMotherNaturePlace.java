package it.polimi.ingsw.controller.server.virtualView;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.model.Island;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.MN_LOCATION;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.OK;


/**
 * @author Davide Grazzani
 * @author Luca Muroni
 * This class is used to send infos about the location of motherNature
 */
class UpdateMotherNaturePlace {
    Island island;
    MessageHandler messageHandler;

    /**
     * Constructor of the class
     * @param island represents the location of motherNature
     * @param messageHandler is the handler of messages
     */
    public UpdateMotherNaturePlace(Island island, MessageHandler messageHandler){
        this.island = island;
        this.messageHandler = messageHandler;
    }

    /**
     * Method that handles the exchange of messages
     * @throws ClientDisconnectedException when the player disconnects from the game
     * @throws FlowErrorException when there is an error in the synchronization
     * @throws MalformedMessageException when a received message isn't correct
     */
    public void handle() throws ClientDisconnectedException, FlowErrorException, MalformedMessageException {
        int topicId = this.messageHandler.getNewUniqueTopicID();
        Integer id = island.getId();
        Message message = new Message(MN_LOCATION.getFragment(), id.toString(), topicId);
        this.messageHandler.write(message);
        this.messageHandler.writeOutAndWait();
        this.messageHandler.assertOnEquals(OK.getFragment(), MN_LOCATION.getFragment());
    }
}