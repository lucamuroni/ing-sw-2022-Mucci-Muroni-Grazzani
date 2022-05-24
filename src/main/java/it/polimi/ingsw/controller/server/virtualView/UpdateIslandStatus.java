package it.polimi.ingsw.controller.server.virtualView;

import it.polimi.ingsw.controller.networking.ConnectionTimings;
import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.model.pawn.Student;
import java.util.ArrayList;
import static it.polimi.ingsw.controller.networking.MessageFragment.*;

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
        Integer val = island.getId();
        messages.add(new Message(ISLAND_ID.getFragment(), val.toString(), topicId));
        int token;
        if (this.island.getOwner().isPresent()){
            token = this.island.getOwner().get().getToken();
        }
        else {

            token = 0;
        }
        messages.add(new Message(OWNER.getFragment(), Integer.toString(token), topicId));
        int num = this.island.getNumTowers();
        messages.add(new Message(NUM_TOWERS.getFragment(), Integer.toString(num), topicId));
        int numStud;
        ArrayList<Student> students = island.getStudents();
        numStud = Math.toIntExact(students.stream().filter(x -> x.getColor().equals(PawnColor.RED)).count());
        messages.add(new Message(PAWN_RED.getFragment(), Integer.toString(numStud), topicId));
        numStud = Math.toIntExact(students.stream().filter(x -> x.getColor().equals(PawnColor.BLUE)).count());
        messages.add(new Message(PAWN_BLUE.getFragment(), Integer.toString(numStud), topicId));
        numStud = Math.toIntExact(students.stream().filter(x -> x.getColor().equals(PawnColor.YELLOW)).count());
        messages.add(new Message(PAWN_YELLOW.getFragment(), Integer.toString(numStud), topicId));
        numStud = Math.toIntExact(students.stream().filter(x -> x.getColor().equals(PawnColor.GREEN)).count());
        messages.add(new Message(PAWN_GREEN.getFragment(), Integer.toString(numStud), topicId));
        numStud = Math.toIntExact(students.stream().filter(x -> x.getColor().equals(PawnColor.PINK)).count());
        messages.add(new Message(PAWN_PINK.getFragment(), Integer.toString(numStud), topicId));
        this.messageHandler.write(messages);
        this.messageHandler.writeOutAndWait(ConnectionTimings.RESPONSE.getTiming());
        if (!(this.messageHandler.getMessagesUniqueTopic() == topicId)) {
            throw new MalformedMessageException();
        }
        this.messageHandler.assertOnEquals(OK.getFragment(), ISLAND.getFragment());
    }
}
