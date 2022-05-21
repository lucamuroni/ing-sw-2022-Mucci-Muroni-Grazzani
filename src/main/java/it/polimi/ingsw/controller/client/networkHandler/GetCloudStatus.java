package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.controller.networking.exceptions.TimeHasEndedException;
import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.model.pawn.Student;
import it.polimi.ingsw.view.asset.game.Cloud;
import it.polimi.ingsw.view.asset.game.Game;
import java.util.ArrayList;
import static it.polimi.ingsw.controller.networking.messageParts.ConnectionTimings.PLAYER_MOVE;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.*;

/**
 * @author Sara Mucci
 * Class that updates the clouds' status in the view
 */
public class GetCloudStatus {
    MessageHandler messageHandler;
    ArrayList<Student> students;
    Game game;

    /**
     * Class constructor
     * @param messageHandler represents the messageHandler used for the message
     */
    public GetCloudStatus(MessageHandler messageHandler, Game game) {
        this.messageHandler = messageHandler;
        this.game = game;
        this.students = new ArrayList<>();
    }

    /**
     * Method that handles the messages to update the clouds status
     * @throws TimeHasEndedException launched when the available time for the response has ended
     * @throws ClientDisconnectedException launched if the client disconnects from the game
     * @throws MalformedMessageException launched if the message isn't created in the correct way
     */
    public void handle() throws TimeHasEndedException, ClientDisconnectedException, MalformedMessageException {
        //ArrayList<Message> messages = new ArrayList<Message>();
        this.messageHandler.read(PLAYER_MOVE.getTiming());
        String id = this.messageHandler.getMessagePayloadFromStream(CLOUD_ID.getFragment());
        int result = Integer.parseInt(id);
        Cloud cloud = null;
        for (Cloud cl : game.getClouds()) {
            if (cl.getId() == result) {
                cloud = cl;
            }
        }
        //this.messageHandler.read(PLAYER_MOVE.getTiming());
        int colorRed = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(PAWN_RED.getFragment()));
        if(colorRed > 0) {
            for (int i = 0; i < colorRed; i++) {
                Student redStudent = new Student(PawnColor.RED);
                students.add(redStudent);
            }
        }
        //this.messageHandler.read(PLAYER_MOVE.getTiming());
        int colorBlue = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(PAWN_BLUE.getFragment()));
        if (colorBlue > 0) {
            for(int i = 0; i < colorBlue; i++) {
                Student blueStudent = new Student(PawnColor.BLUE);
                students.add(blueStudent);
            }
        }
        //this.messageHandler.read(PLAYER_MOVE.getTiming());
        int colorYellow = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(PAWN_YELLOW.getFragment()));
        if(colorYellow > 0) {
            for(int i = 0; i < colorYellow; i++) {
                Student blueStudent = new Student(PawnColor.YELLOW);
                students.add(blueStudent);
            }
        }
        //this.messageHandler.read(PLAYER_MOVE.getTiming());
        int colorGreen = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(PAWN_GREEN.getFragment()));
        if(colorGreen > 0) {
            for(int i = 0; i < colorGreen; i++) {
                Student greenStudent = new Student(PawnColor.GREEN);
                students.add(greenStudent);
            }
        }
        //this.messageHandler.read(PLAYER_MOVE.getTiming());
        int colorPink = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(PAWN_PINK.getFragment()));
        if(colorPink > 0) {
            for(int i = 0; i < colorPink; i++) {
                Student pinkStudent = new Student(PawnColor.PINK);
                students.add(pinkStudent);
            }
        }
        int topicId = this.messageHandler.getMessagesUniqueTopic();
        Message message = new Message(CLOUD.getFragment(), OK.getFragment(), topicId);
        this.messageHandler.write(message);
        this.messageHandler.writeOut();
        cloud.update(students);
    }
}
