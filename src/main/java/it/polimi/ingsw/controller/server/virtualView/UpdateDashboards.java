package it.polimi.ingsw.controller.server.virtualView;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.FlowErrorException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
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
     * @throws ClientDisconnectedException launched if the client disconnects from the game
     * @throws FlowErrorException launched when the client sends an unexpected response
     */
    public void handle() throws MalformedMessageException, ClientDisconnectedException, FlowErrorException {
        ArrayList<Message> messages = new ArrayList<>();
        int topicId = this.messageHandler.getNewUniqueTopicID();
        int result;
        int prof;
        int token = gamer.getToken();
        messages.add(new Message(OWNER.getFragment(), String.valueOf(token), topicId));
        int num = gamer.getDashboard().getNumTowers();
        messages.add(new Message(NUM_TOWERS.getFragment(), Integer.toString(num), topicId));
        //Aggiunge studenti waitingRoom
        ArrayList<Student> waitingStudents = gamer.getDashboard().getWaitingRoom();
        result = Math.toIntExact(waitingStudents.stream().filter(x -> x.getColor().equals(PawnColor.RED)).count());
        messages.add(new Message(WAITING_PAWN_RED.getFragment(), String.valueOf(result), topicId));
        result = Math.toIntExact(waitingStudents.stream().filter(x -> x.getColor().equals(PawnColor.BLUE)).count());
        messages.add(new Message(WAITING_PAWN_BLUE.getFragment(), String.valueOf(result), topicId));
        result = Math.toIntExact(waitingStudents.stream().filter(x -> x.getColor().equals(PawnColor.YELLOW)).count());
        messages.add(new Message(WAITING_PAWN_YELLOW.getFragment(), String.valueOf(result), topicId));
        result = Math.toIntExact(waitingStudents.stream().filter(x -> x.getColor().equals(PawnColor.GREEN)).count());
        messages.add(new Message(WAITING_PAWN_GREEN.getFragment(), String.valueOf(result), topicId));
        result = Math.toIntExact(waitingStudents.stream().filter(x -> x.getColor().equals(PawnColor.PINK)).count());
        messages.add(new Message(WAITING_PAWN_PINK.getFragment(), String.valueOf(result), topicId));
        //Aggiunge studenti hall
        ArrayList<Student> hallStudents = gamer.getDashboard().getHall();
        result = Math.toIntExact(hallStudents.stream().filter(x -> x.getColor().equals(PawnColor.RED)).count());
        messages.add(new Message(HALL_PAWN_RED.getFragment(), String.valueOf(result), topicId));
        result = Math.toIntExact(hallStudents.stream().filter(x -> x.getColor().equals(PawnColor.BLUE)).count());
        messages.add(new Message(HALL_PAWN_BLUE.getFragment(), String.valueOf(result), topicId));
        result = Math.toIntExact(hallStudents.stream().filter(x -> x.getColor().equals(PawnColor.YELLOW)).count());
        messages.add(new Message(HALL_PAWN_YELLOW.getFragment(), String.valueOf(result), topicId));
        result = Math.toIntExact(hallStudents.stream().filter(x -> x.getColor().equals(PawnColor.GREEN)).count());
        messages.add(new Message(HALL_PAWN_GREEN.getFragment(), String.valueOf(result), topicId));
        result = Math.toIntExact(hallStudents.stream().filter(x -> x.getColor().equals(PawnColor.PINK)).count());
        messages.add(new Message(HALL_PAWN_PINK.getFragment(), String.valueOf(result), topicId));
        //Aggiunge prof
        ArrayList<Professor> professors = game.getProfessorsByGamer(gamer);
        prof = Math.toIntExact(professors.stream().filter(x -> x.getColor().equals(PawnColor.RED)).count());
        messages.add(new Message(PAWN_RED.getFragment(), String.valueOf(prof), topicId));
        prof = Math.toIntExact(professors.stream().filter(x -> x.getColor().equals(PawnColor.BLUE)).count());
        messages.add(new Message(PAWN_BLUE.getFragment(), String.valueOf(prof), topicId));
        prof = Math.toIntExact(professors.stream().filter(x -> x.getColor().equals(PawnColor.YELLOW)).count());
        messages.add(new Message(PAWN_YELLOW.getFragment(), String.valueOf(prof), topicId));
        prof = Math.toIntExact(professors.stream().filter(x -> x.getColor().equals(PawnColor.GREEN)).count());
        messages.add(new Message(PAWN_GREEN.getFragment(), String.valueOf(prof), topicId));
        prof = Math.toIntExact(professors.stream().filter(x -> x.getColor().equals(PawnColor.PINK)).count());
        messages.add(new Message(PAWN_PINK.getFragment(), String.valueOf(prof), topicId));
        this.messageHandler.write(messages);
        this.messageHandler.writeOutAndWait();
        this.messageHandler.assertOnEquals(OK.getFragment(), DASHBOARD.getFragment());
    }
}
