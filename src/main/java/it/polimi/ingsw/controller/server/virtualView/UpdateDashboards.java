package it.polimi.ingsw.controller.server.virtualView;

import it.polimi.ingsw.controller.networking.ConnectionTimings;
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
import static it.polimi.ingsw.controller.networking.MessageFragment.*;
import static java.lang.Integer.valueOf;

/**
 * @author LucaMuroni
 * Class that implements the mssage to update the status of the dashboards
 */
public class UpdateDashboards {
    ArrayList<Gamer> gamers;
    Game game;
    MessageHandler messageHandler;
    //Game game;

    /**
     * Class constructor
     * @param gamers represents the players whose dashboards have to be updated
     * @param messageHandler represents the messageHandler used for the message
     */
    public UpdateDashboards(ArrayList<Gamer> gamers, Game game, MessageHandler messageHandler){
        this.gamers = gamers;
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
        ArrayList<Message> messages = new ArrayList<Message>();
        int topicId = this.messageHandler.getNewUniqueTopicID();
        int numStud;
        int prof;
        Integer result = null;
        Integer profs = null;
        for (Gamer gamer : this.gamers) {
            Integer token = valueOf(gamer.getToken());
            messages.add(new Message(OWNER.getFragment(), token.toString(), topicId));
            Integer num = valueOf(gamer.getDashboard().getNumTowers());
            messages.add(new Message(NUM_TOWERS.getFragment(), num.toString(), topicId));

            //Aggiunge studenti waitingRoom
            ArrayList<Student> waitingStudents = gamer.getDashboard().getWaitingRoom();
            numStud = Math.toIntExact(waitingStudents.stream().filter(x -> x.getColor().equals(PawnColor.RED)).count());
            result = valueOf(numStud);
            messages.add(new Message(WAITING_PAWN_RED.getFragment(), result.toString(), topicId));
            numStud = Math.toIntExact(waitingStudents.stream().filter(x -> x.getColor().equals(PawnColor.BLUE)).count());
            result = valueOf(numStud);
            messages.add(new Message(WAITING_PAWN_BLUE.getFragment(), result.toString(), topicId));
            numStud = Math.toIntExact(waitingStudents.stream().filter(x -> x.getColor().equals(PawnColor.YELLOW)).count());
            result = valueOf(numStud);
            messages.add(new Message(WAITING_PAWN_YELLOW.getFragment(), result.toString(), topicId));
            numStud = Math.toIntExact(waitingStudents.stream().filter(x -> x.getColor().equals(PawnColor.GREEN)).count());
            result = valueOf(numStud);
            messages.add(new Message(WAITING_PAWN_GREEN.getFragment(), result.toString(), topicId));
            numStud = Math.toIntExact(waitingStudents.stream().filter(x -> x.getColor().equals(PawnColor.PINK)).count());
            result = valueOf(numStud);
            messages.add(new Message(WAITING_PAWN_PINK.getFragment(), result.toString(), topicId));

            //Aggiunge studenti hall
            ArrayList<Student> hallStudents = gamer.getDashboard().getHall();
            numStud = Math.toIntExact(hallStudents.stream().filter(x -> x.getColor().equals(PawnColor.RED)).count());
            result = valueOf(numStud);
            messages.add(new Message(HALL_PAWN_RED.getFragment(), result.toString(), topicId));
            numStud = Math.toIntExact(hallStudents.stream().filter(x -> x.getColor().equals(PawnColor.BLUE)).count());
            result = valueOf(numStud);
            messages.add(new Message(HALL_PAWN_BLUE.getFragment(), result.toString(), topicId));
            numStud = Math.toIntExact(hallStudents.stream().filter(x -> x.getColor().equals(PawnColor.YELLOW)).count());
            result = valueOf(numStud);
            messages.add(new Message(HALL_PAWN_YELLOW.getFragment(), result.toString(), topicId));
            numStud = Math.toIntExact(hallStudents.stream().filter(x -> x.getColor().equals(PawnColor.GREEN)).count());
            result = valueOf(numStud);
            messages.add(new Message(HALL_PAWN_GREEN.getFragment(), result.toString(), topicId));
            numStud = Math.toIntExact(hallStudents.stream().filter(x -> x.getColor().equals(PawnColor.PINK)).count());
            result = valueOf(numStud);
            messages.add(new Message(HALL_PAWN_PINK.getFragment(), result.toString(), topicId));

            //Aggiunge prof
            ArrayList<Professor> professors = game.getProfessorsByGamer(gamer);
            prof = Math.toIntExact(professors.stream().filter(x -> x.getColor().equals(PawnColor.RED)).count());
            profs = valueOf(prof);
            messages.add(new Message(PAWN_RED.getFragment(), profs.toString(), topicId));
            prof = Math.toIntExact(professors.stream().filter(x -> x.getColor().equals(PawnColor.BLUE)).count());
            profs = valueOf(prof);
            messages.add(new Message(PAWN_BLUE.getFragment(), profs.toString(), topicId));
            prof = Math.toIntExact(professors.stream().filter(x -> x.getColor().equals(PawnColor.YELLOW)).count());
            profs = valueOf(prof);
            messages.add(new Message(PAWN_YELLOW.getFragment(), profs.toString(), topicId));
            prof = Math.toIntExact(professors.stream().filter(x -> x.getColor().equals(PawnColor.GREEN)).count());
            profs = valueOf(prof);
            messages.add(new Message(PAWN_GREEN.getFragment(), profs.toString(), topicId));
            prof = Math.toIntExact(professors.stream().filter(x -> x.getColor().equals(PawnColor.PINK)).count());
            profs = valueOf(prof);
            messages.add(new Message(PAWN_PINK.getFragment(), profs.toString(), topicId));
            messages.add(new Message(STOP.getFragment(), "", topicId));
        }
        this.messageHandler.write(messages);
        messages.clear();
        this.messageHandler.writeOutAndWait(ConnectionTimings.RESPONSE.getTiming());
        this.messageHandler.assertOnEquals(OK.getFragment(), DASHBOARD.getFragment());
    }
}
