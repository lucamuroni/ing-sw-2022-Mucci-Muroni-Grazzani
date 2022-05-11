package it.polimi.ingsw.controller.server.virtualView;

import it.polimi.ingsw.controller.networking.ConnectionTimings;
import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.pawn.Student;
import java.util.ArrayList;
import static it.polimi.ingsw.controller.networking.MessageFragment.*;
import static java.lang.Integer.valueOf;

/**
 * @author Luca Muroni
 * @author Sara Mucci
 * Class that implements the message to update the status of an island
 */
public class UpdateIslandStatus {
    Island island;
    MessageHandler messageHandler;

    /**
     * Class constructor
     * @param island represents the island to update
     * @param messageHandler represents the messageHandler used for the message
     */
    public UpdateIslandStatus(Island island, MessageHandler messageHandler){
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
        ArrayList<Message> messages = new ArrayList<Message>();
        int topicId = this.messageHandler.getNewUniqueTopicID();
        Integer num = valueOf(this.island.getNumTowers());
        messages.add(new Message(NUM_TOWERS.getFragment(), num.toString(), topicId));
        Integer token = null;
        if (this.island.getOwner().isPresent()){
            token = valueOf(this.island.getOwner().get().getToken());
        }
        else {
            //TODO: Controllare con Grazza: se non esiste l'owner dell'isola, allora si invia come token di default "0"
            token = valueOf(0);
        }
        messages.add(new Message(OWNER.getFragment(), token.toString(), topicId));
        if (island.getStudents().isEmpty())
            messages.add(new Message(STUDENT_COLOR.getFragment(), "", topicId));
        else {
            for (Student student : this.island.getStudents()) {
                messages.add(new Message(STUDENT_COLOR.getFragment(), student.getColor().toString(), topicId));
            }
        }
        this.messageHandler.write(messages);
        messages.clear();
        this.messageHandler.writeOutAndWait(ConnectionTimings.RESPONSE.getTiming());
        this.messageHandler.assertOnEquals(OK.getFragment(), ISLAND.getFragment());
    }
}
