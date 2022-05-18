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
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.OK;

/**
 * @author Sara Mucci
 * Class that updates the clouds' status in the view
 */
public class GetCloudStatus {
    MessageHandler messageHandler;
    Boolean stop = false;
    Game game;

    /**
     * Class constructor
     * @param messageHandler represents the messageHandler used for the message
     */
    public GetCloudStatus(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    /**
     * Method that handles the messages to update the clouds status
     * @throws TimeHasEndedException launched when the available time for the response has ended
     * @throws ClientDisconnectedException launched if the client disconnects from the game
     * @throws MalformedMessageException launched if the message isn't created in the correct way
     */
    public void handle() throws TimeHasEndedException, ClientDisconnectedException, MalformedMessageException {
        ArrayList<Message> messages = new ArrayList<Message>();
        while (!stop) {
            this.messageHandler.read(PLAYER_MOVE.getTiming());
            String id = this.messageHandler.getMessagePayloadFromStream(PLAYER_ID.getFragment());
            if (id.equals("stop")) {
                stop = true;
            }
            else {
                int result = Integer.parseInt(id);
                for (Cloud cloud: game.getClouds()) {
                    if (result == cloud.getId()) {
                        this.messageHandler.read(PLAYER_MOVE.getTiming());
                        int colorRed = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(PAWN_RED.getFragment()));
                        if (colorRed == 0) {
                            cloud.update();
                        }
                        else if(colorRed > 0) {
                            ArrayList<Student> redStudents = new ArrayList<Student>();
                            for (int i = 0; i < colorRed; i++) {
                                Student redStudent = new Student(PawnColor.RED);
                                redStudents.add(redStudent);
                            }
                            cloud.update(redStudents);
                        }
                        this.messageHandler.read(PLAYER_MOVE.getTiming());
                        int colorBlue = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(PAWN_BLUE.getFragment()));
                        if (colorBlue == 0) {
                            cloud.update();
                        }
                        else if (colorBlue > 0) {
                            ArrayList<Student> blueStudents = new ArrayList<Student>();
                            for(int i = 0; i < colorBlue; i++) {
                                Student blueStudent = new Student(PawnColor.BLUE);
                                blueStudents.add(blueStudent);
                            }
                            cloud.update(blueStudents);
                        }
                        this.messageHandler.read(PLAYER_MOVE.getTiming());
                        int colorYellow = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(PAWN_YELLOW.getFragment()));
                        if (colorYellow == 0) {
                            cloud.update();
                        }
                        else if(colorYellow > 0) {
                            ArrayList<Student> yellowStudents = new ArrayList<Student>();
                            for(int i = 0; i < colorYellow; i++) {
                                Student blueStudent = new Student(PawnColor.YELLOW);
                                yellowStudents.add(blueStudent);
                            }
                            cloud.update(yellowStudents);
                        }
                        this.messageHandler.read(PLAYER_MOVE.getTiming());
                        int colorGreen = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(PAWN_GREEN.getFragment()));
                        if (colorGreen == 0) {
                            cloud.update();
                        }
                        else if(colorGreen > 0) {
                            ArrayList<Student> greenStudents = new ArrayList<Student>();
                            for(int i = 0; i < colorGreen; i++) {
                                Student greenStudent = new Student(PawnColor.GREEN);
                                greenStudents.add(greenStudent);
                            }
                            cloud.update(greenStudents);
                        }
                        this.messageHandler.read(PLAYER_MOVE.getTiming());
                        int colorPink = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(PAWN_PINK.getFragment()));
                        if (colorPink == 0) {
                            cloud.update();
                        }
                        else if(colorPink > 0) {
                            ArrayList<Student> pinkStudents = new ArrayList<Student>();
                            for(int i = 0; i < colorPink; i++) {
                                Student pinkStudent = new Student(PawnColor.PINK);
                                pinkStudents.add(pinkStudent);
                            }
                            cloud.update(pinkStudents);
                        }
                    }
                }
            }
        }
        int topicId = this.messageHandler.getMessagesUniqueTopic();
        messages.add(new Message(OK.getFragment(), "", topicId));
        this.messageHandler.write(messages);
    }
}
