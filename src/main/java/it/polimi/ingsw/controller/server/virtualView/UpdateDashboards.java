package it.polimi.ingsw.controller.server.virtualView;

import it.polimi.ingsw.controller.networking.messageParts.ConnectionTimings;
import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.gamer.Gamer;
import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.model.pawn.Professor;
import it.polimi.ingsw.model.pawn.Student;
import java.util.ArrayList;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.*;

/**
 * @author LucaMuroni
 * Class that implements the mssage to update the status of the dashboards
 */
public class UpdateDashboards {
    Gamer gamer;
    Game game;
    MessageHandler messageHandler;

    /**
     * Class constructor
     * @param gamer represents the player whose dashboard have to be updated
     * @param messageHandler represents the messageHandler used for the message
     */
    public UpdateDashboards(Gamer gamer, Game game, MessageHandler messageHandler){
        this.gamer = gamer;
        this.messageHandler = messageHandler;
        this.game = game;
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
        int result;
        int prof;
        int token = gamer.getToken();
        messages.add(new Message(OWNER.getFragment(), Integer.toString(token), topicId));
        int num = gamer.getDashboard().getNumTowers();
        messages.add(new Message(NUM_TOWERS.getFragment(), Integer.toString(num), topicId));
        //Aggiunge studenti waitingRoom
        ArrayList<Student> waitingStudents = gamer.getDashboard().getWaitingRoom();
        result = Math.toIntExact(waitingStudents.stream().filter(x -> x.getColor().equals(PawnColor.RED)).count());
        messages.add(new Message(WAITING_PAWN_RED.getFragment(), Integer.toString(result), topicId));
        result = Math.toIntExact(waitingStudents.stream().filter(x -> x.getColor().equals(PawnColor.BLUE)).count());
        messages.add(new Message(WAITING_PAWN_BLUE.getFragment(), Integer.toString(result), topicId));
        result = Math.toIntExact(waitingStudents.stream().filter(x -> x.getColor().equals(PawnColor.YELLOW)).count());
        messages.add(new Message(WAITING_PAWN_YELLOW.getFragment(), Integer.toString(result), topicId));
        result = Math.toIntExact(waitingStudents.stream().filter(x -> x.getColor().equals(PawnColor.GREEN)).count());
        messages.add(new Message(WAITING_PAWN_GREEN.getFragment(), Integer.toString(result), topicId));
        result = Math.toIntExact(waitingStudents.stream().filter(x -> x.getColor().equals(PawnColor.PINK)).count());
        messages.add(new Message(WAITING_PAWN_PINK.getFragment(), Integer.toString(result), topicId));
        //Aggiunge studenti hall
        ArrayList<Student> hallStudents = gamer.getDashboard().getHall();
        result = Math.toIntExact(hallStudents.stream().filter(x -> x.getColor().equals(PawnColor.RED)).count());
        messages.add(new Message(HALL_PAWN_RED.getFragment(), Integer.toString(result), topicId));
        result = Math.toIntExact(hallStudents.stream().filter(x -> x.getColor().equals(PawnColor.BLUE)).count());
        messages.add(new Message(HALL_PAWN_BLUE.getFragment(), Integer.toString(result), topicId));
        result = Math.toIntExact(hallStudents.stream().filter(x -> x.getColor().equals(PawnColor.YELLOW)).count());
        messages.add(new Message(HALL_PAWN_YELLOW.getFragment(), Integer.toString(result), topicId));
        result = Math.toIntExact(hallStudents.stream().filter(x -> x.getColor().equals(PawnColor.GREEN)).count());
        messages.add(new Message(HALL_PAWN_GREEN.getFragment(), Integer.toString(result), topicId));
        result = Math.toIntExact(hallStudents.stream().filter(x -> x.getColor().equals(PawnColor.PINK)).count());
        messages.add(new Message(HALL_PAWN_PINK.getFragment(), Integer.toString(result), topicId));
        //Aggiunge prof
        ArrayList<Professor> professors = game.getProfessorsByGamer(gamer);
        prof = Math.toIntExact(professors.stream().filter(x -> x.getColor().equals(PawnColor.RED)).count());
        messages.add(new Message(PAWN_RED.getFragment(), Integer.toString(prof), topicId));
        prof = Math.toIntExact(professors.stream().filter(x -> x.getColor().equals(PawnColor.BLUE)).count());
        messages.add(new Message(PAWN_BLUE.getFragment(), Integer.toString(prof), topicId));
        prof = Math.toIntExact(professors.stream().filter(x -> x.getColor().equals(PawnColor.YELLOW)).count());
        messages.add(new Message(PAWN_YELLOW.getFragment(), Integer.toString(prof), topicId));
        prof = Math.toIntExact(professors.stream().filter(x -> x.getColor().equals(PawnColor.GREEN)).count());
        messages.add(new Message(PAWN_GREEN.getFragment(), Integer.toString(prof), topicId));
        prof = Math.toIntExact(professors.stream().filter(x -> x.getColor().equals(PawnColor.PINK)).count());
        messages.add(new Message(PAWN_PINK.getFragment(), Integer.toString(prof), topicId));
        this.messageHandler.write(messages);
        this.messageHandler.writeOutAndWait(ConnectionTimings.RESPONSE.getTiming());
        if (!(this.messageHandler.getMessagesUniqueTopic() == topicId)) {
            throw new MalformedMessageException();
        }
        this.messageHandler.assertOnEquals(OK.getFragment(), DASHBOARD.getFragment());
    }
}
