package it.polimi.ingsw.controller.server.virtualView;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.model.pawn.Student;
import java.util.ArrayList;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.*;


/**
 * @author Luca Muroni
 * This class is used to send infos about islands
 */
public class UpdateIslandStatus {
    Island island;
    MessageHandler messageHandler;

    /**
     * Constructor of the class
     * @param island represents the island to update
     * @param messageHandler is the handler of messages
     */
    public UpdateIslandStatus(Island island, MessageHandler messageHandler){
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
        ArrayList<Message> messages = new ArrayList<>();
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
        this.messageHandler.writeOutAndWait();
        this.messageHandler.assertOnEquals(OK.getFragment(), ISLAND.getFragment());
    }
}