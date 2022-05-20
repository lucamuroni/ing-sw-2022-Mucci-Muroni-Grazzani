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
import it.polimi.ingsw.model.pawn.Professor;
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
        Integer val = valueOf(island.getId());
        messages.add(new Message(ISLAND_ID.getFragment(), val.toString(), topicId));
        Integer token;
        if (this.island.getOwner().isPresent()){
            token = this.island.getOwner().get().getToken();
        }
        else {

            token = 0;
        }
        messages.add(new Message(OWNER.getFragment(), token.toString(), topicId));
        Integer num = this.island.getNumTowers();
        messages.add(new Message(NUM_TOWERS.getFragment(), num.toString(), topicId));
        //Non ritengo necessario anche l'invio di motherNature perchè tanto viene mossa solo in una fase ed esiste un messaggio a lei dedicato
        int numStud;
        Integer result = null;
        ArrayList<Student> students = island.getStudents();
        numStud = Math.toIntExact(students.stream().filter(x -> x.getColor().equals(PawnColor.RED)).count());
        result = numStud;
        messages.add(new Message(PAWN_RED.getFragment(), result.toString(), topicId));
        numStud = Math.toIntExact(students.stream().filter(x -> x.getColor().equals(PawnColor.BLUE)).count());
        result = numStud;
        messages.add(new Message(PAWN_BLUE.getFragment(), result.toString(), topicId));
        numStud = Math.toIntExact(students.stream().filter(x -> x.getColor().equals(PawnColor.YELLOW)).count());
        result = numStud;
        messages.add(new Message(PAWN_YELLOW.getFragment(), result.toString(), topicId));
        numStud = Math.toIntExact(students.stream().filter(x -> x.getColor().equals(PawnColor.GREEN)).count());
        result = numStud;
        messages.add(new Message(PAWN_GREEN.getFragment(), result.toString(), topicId));
        numStud = Math.toIntExact(students.stream().filter(x -> x.getColor().equals(PawnColor.PINK)).count());
        result = numStud;
        messages.add(new Message(PAWN_PINK.getFragment(), result.toString(), topicId));
        this.messageHandler.write(messages);
        messages.clear();
        this.messageHandler.writeOutAndWait(ConnectionTimings.RESPONSE.getTiming());
        this.messageHandler.assertOnEquals(OK.getFragment(), ISLAND.getFragment());
    }
}
