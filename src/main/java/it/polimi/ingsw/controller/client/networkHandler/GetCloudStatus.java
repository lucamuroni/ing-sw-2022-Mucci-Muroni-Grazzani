package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.model.pawn.Student;
import it.polimi.ingsw.view.asset.exception.AssetErrorException;
import it.polimi.ingsw.view.asset.game.Cloud;
import it.polimi.ingsw.view.asset.game.Game;

import java.util.ArrayList;

import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.*;

/**
 * @author Luca Muroni
 * @author Sara Mucci
 * This class is used to get the infos about clouds
 */
public class GetCloudStatus {
    MessageHandler messageHandler;
    ArrayList<Student> students;
    Game game;

    /**
     * Constructor of the class
     * @param messageHandler is the handler of messages
     * @param game is the current game
     */
    public GetCloudStatus(MessageHandler messageHandler, Game game) {
        this.messageHandler = messageHandler;
        this.game = game;
        this.students = new ArrayList<>();
    }

    /**
     * Method that handles the exchange of messages
     * @throws AssetErrorException when an object of the game isn't found in the asset
     * @throws MalformedMessageException when a received message isn't correct
     * @throws ClientDisconnectedException when the player disconnects from the game
     */
    public void handle() throws AssetErrorException, MalformedMessageException, ClientDisconnectedException {
        this.messageHandler.read();
        String id = this.messageHandler.getMessagePayloadFromStream(CLOUD_ID.getFragment());
        int result = Integer.parseInt(id);
        Cloud cloud = null;
        for (Cloud cl : game.getClouds()) {
            if (cl.getId() == result) {
                cloud = cl;
            }
        }
        if(cloud == null) {
            throw new AssetErrorException();
        }
        int colorRed = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(PAWN_RED.getFragment()));
        if(colorRed > 0) {
            for (int i = 0; i < colorRed; i++) {
                Student redStudent = new Student(PawnColor.RED);
                students.add(redStudent);
            }
        }
        int colorBlue = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(PAWN_BLUE.getFragment()));
        if (colorBlue > 0) {
            for(int i = 0; i < colorBlue; i++) {
                Student blueStudent = new Student(PawnColor.BLUE);
                students.add(blueStudent);
            }
        }
        int colorYellow = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(PAWN_YELLOW.getFragment()));
        if(colorYellow > 0) {
            for(int i = 0; i < colorYellow; i++) {
                Student blueStudent = new Student(PawnColor.YELLOW);
                students.add(blueStudent);
            }
        }
        int colorGreen = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(PAWN_GREEN.getFragment()));
        if(colorGreen > 0) {
            for(int i = 0; i < colorGreen; i++) {
                Student greenStudent = new Student(PawnColor.GREEN);
                students.add(greenStudent);
            }
        }
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