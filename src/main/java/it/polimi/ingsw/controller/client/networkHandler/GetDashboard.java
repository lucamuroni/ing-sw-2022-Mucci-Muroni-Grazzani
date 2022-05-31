package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.model.pawn.Student;
import it.polimi.ingsw.view.asset.game.Game;
import it.polimi.ingsw.view.asset.game.Gamer;
import java.util.ArrayList;
import static it.polimi.ingsw.controller.networking.messageParts.ConnectionTimings.PLAYER_MOVE;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.*;

/**
 * @author Luca Muroni
 * @author Sara Mucci
 * Class that updates the dashboards in the view
 */
public class GetDashboard {
    MessageHandler messageHandler;
    Game game;
    ArrayList<Student> studentsWaitingRoom;
    ArrayList<Student> studentsHall;
    int numTowers;
    ArrayList<PawnColor> professors;

    /**
     * Class constructor
     * @param messageHandler represents the messageHandler used for the message
     * @param game represents the current game
     */
    public GetDashboard(MessageHandler messageHandler, Game game) {
        this.messageHandler = messageHandler;
        this.studentsWaitingRoom = new ArrayList<>();
        this.professors = new ArrayList<>();
        this.studentsHall = new ArrayList<>();
        this.game = game;
    }

    /**
     * Method that handles the update of the dashboards in the view
     * @throws TimeHasEndedException launched when the available time for the response has ended
     * @throws ClientDisconnectedException launched if the client disconnects from the game
     * @throws MalformedMessageException launched if the message isn't created in the correct way
     */
    public void handle() throws TimeHasEndedException, ClientDisconnectedException, MalformedMessageException {
        this.messageHandler.read(PLAYER_MOVE.getTiming());
        int result = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(OWNER.getFragment()));
        Gamer gamer = null;
        for (Gamer gm : game.getGamers()) {
            if (gm.getId() == result) {
                gamer = gm;
            }
        }
        numTowers = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(NUM_TOWERS.getFragment()));
        //Prendo studenti WaitingRoom
        int waitingRed = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(WAITING_PAWN_RED.getFragment()));
         if (waitingRed > 0) {
             for (int i = 0; i < waitingRed; i++) {
                 Student redStudent = new Student(PawnColor.RED);
                 studentsWaitingRoom.add(redStudent);
             }
         }
         int waitingBlue = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(WAITING_PAWN_BLUE.getFragment()));
         if (waitingBlue > 0) {
             for (int i = 0; i < waitingBlue; i++) {
                 Student blueStudent = new Student(PawnColor.BLUE);
                 studentsWaitingRoom.add(blueStudent);
             }
         }
         int waitingYellow = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(WAITING_PAWN_YELLOW.getFragment()));
         if (waitingYellow > 0) {
             for (int i = 0; i < waitingYellow; i++) {
                 Student yellowStudent = new Student(PawnColor.YELLOW);
                 studentsWaitingRoom.add(yellowStudent);
             }
         }
         int waitingGreen = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(WAITING_PAWN_GREEN.getFragment()));
         if (waitingGreen > 0) {
             for (int i = 0; i < waitingGreen; i++) {
                 Student greenStudent = new Student(PawnColor.GREEN);
                 studentsWaitingRoom.add(greenStudent);
             }
         }
         int waitingPink = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(WAITING_PAWN_PINK.getFragment()));
         if (waitingPink > 0) {
             for (int i = 0; i < waitingPink; i++) {
                 Student pinkStudent = new Student(PawnColor.PINK);
                 studentsWaitingRoom.add(pinkStudent);
             }
         }
         //Prendo studenti Hall
         int hallRed = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(HALL_PAWN_RED.getFragment()));
         if (hallRed > 0) {
             for (int i = 0; i < hallRed; i++) {
                 Student redStudent = new Student(PawnColor.RED);
                 studentsHall.add(redStudent);
             }
         }
         int hallBlue = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(HALL_PAWN_BLUE.getFragment()));
         if (hallBlue > 0) {
             for (int i = 0; i < hallBlue; i++) {
                 Student blueStudent = new Student(PawnColor.BLUE);
                 studentsHall.add(blueStudent);
             }
         }
         int hallYellow = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(HALL_PAWN_YELLOW.getFragment()));
         if (hallYellow > 0) {
             for (int i = 0; i < hallYellow; i++) {
                 Student yellowStudent = new Student(PawnColor.YELLOW);
                 studentsHall.add(yellowStudent);
             }
         }
         int hallGreen = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(HALL_PAWN_GREEN.getFragment()));
         if (hallGreen > 0) {
             for (int i = 0; i < hallGreen; i++) {
                 Student greenStudent = new Student(PawnColor.GREEN);
                 studentsHall.add(greenStudent);
             }
         }
         int hallPink = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(HALL_PAWN_PINK.getFragment()));
         if (hallPink > 0) {
             for (int i = 0; i < hallPink; i++) {
                 Student pinkStudent = new Student(PawnColor.PINK);
                 studentsHall.add(pinkStudent);
             }
         }
         //Prendo i Professor
         int profRed = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(PAWN_RED.getFragment()));
         if (profRed == 1) {
             professors.add(PawnColor.RED);
         }
         int profBlue = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(PAWN_BLUE.getFragment()));
         if (profBlue == 1) {
             professors.add(PawnColor.BLUE);
         }
         int profYellow = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(PAWN_YELLOW.getFragment()));
         if (profYellow == 1) {
             professors.add(PawnColor.YELLOW);
         }
         int profGreen = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(PAWN_GREEN.getFragment()));
         if (profGreen == 1) {
             professors.add(PawnColor.GREEN);
         }
         int profPink = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(PAWN_PINK.getFragment()));
         if (profPink == 1) {
             professors.add(PawnColor.PINK);
         }
         int topicId = this.messageHandler.getMessagesUniqueTopic();
         Message message = new Message(DASHBOARD.getFragment(), OK.getFragment(), topicId);
         this.messageHandler.write(message);
         this.messageHandler.writeOut();
         assert gamer != null;
         gamer.getDashBoard().updateDashBoard(numTowers, studentsWaitingRoom, studentsHall, professors);
    }
}