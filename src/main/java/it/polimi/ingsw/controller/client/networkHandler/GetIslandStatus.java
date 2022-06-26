package it.polimi.ingsw.controller.client.networkHandler;

import it.polimi.ingsw.controller.networking.Message;
import it.polimi.ingsw.controller.networking.MessageHandler;
import it.polimi.ingsw.controller.networking.exceptions.ClientDisconnectedException;
import it.polimi.ingsw.controller.networking.exceptions.MalformedMessageException;
import it.polimi.ingsw.model.pawn.PawnColor;
import it.polimi.ingsw.model.pawn.Student;
import it.polimi.ingsw.view.asset.exception.AssetErrorException;
import it.polimi.ingsw.view.asset.game.Game;
import it.polimi.ingsw.view.asset.game.Gamer;
import it.polimi.ingsw.view.asset.game.Island;
import java.util.ArrayList;

import static it.polimi.ingsw.controller.networking.messageParts.ConnectionTimings.CONNECTION_STARTUP;
import static it.polimi.ingsw.controller.networking.messageParts.ConnectionTimings.PLAYER_MOVE;
import static it.polimi.ingsw.controller.networking.messageParts.MessageFragment.*;

/**
 * @author Luca Muroni
 * @author Sara Mucci
 * Class that updates the islands' status in the view
 */
public class GetIslandStatus {
    MessageHandler messageHandler;
    Game game;
    int numTowers;
    ArrayList<Student> students;

    /**
     * Class constructor
     * @param messageHandler represents the messageHandler used for the message
     * @param game represents the current game
     */
    public GetIslandStatus(MessageHandler messageHandler, Game game) {
        this.messageHandler = messageHandler;
        this.game = game;
        this.students = new ArrayList<>();
    }

    /**
     * Method that handles the update of the island in the view
     * @throws ClientDisconnectedException launched if the client disconnects from the game
     * @throws MalformedMessageException   launched if the message isn't created in the correct way
     */
    public void handle() throws ClientDisconnectedException, MalformedMessageException, AssetErrorException {
        this.messageHandler.read();
        int result = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(ISLAND_ID.getFragment()));
        Island island = null;
        for (Island isl : game.getIslands()) {
            if (isl.getId() == result) {
                island = isl;
            }
        }
        if (island==null)
            throw new AssetErrorException();
        //Prendo owner dell'isola
        int owner = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(OWNER.getFragment()));
        Gamer gamer = null;
        if (owner!=0) {
            for (Gamer gm : game.getGamers()) {
                if (gm.getId() == owner) {
                    gamer = gm;
                }
            }
        }
        if (owner!=0 && gamer==null)
            throw new AssetErrorException();
        numTowers = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(NUM_TOWERS.getFragment()));
        //Prendo gli Student
        int colorRed = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(PAWN_RED.getFragment()));
        if (colorRed > 0) {
            for (int i = 0; i < colorRed; i++) {
                Student redStudent = new Student(PawnColor.RED);
                students.add(redStudent);
            }
        }
        int colorBlue = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(PAWN_BLUE.getFragment()));
        if (colorBlue > 0) {
            for (int i = 0; i < colorBlue; i++) {
                Student blueStudent = new Student(PawnColor.BLUE);
                students.add(blueStudent);
            }
        }
        int colorYellow = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(PAWN_YELLOW.getFragment()));
        if (colorYellow > 0) {
            for (int i = 0; i < colorYellow; i++) {
                Student yellowStudent = new Student(PawnColor.YELLOW);
                students.add(yellowStudent);
            }
        }
        int colorGreen = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(PAWN_GREEN.getFragment()));
        if (colorGreen > 0) {
            for (int i = 0; i < colorGreen; i++) {
                Student greenStudent = new Student(PawnColor.GREEN);
                students.add(greenStudent);
            }
        }
        int colorPink = Integer.parseInt(this.messageHandler.getMessagePayloadFromStream(PAWN_PINK.getFragment()));
        if (colorPink > 0) {
            for (int i = 0; i < colorPink; i++) {
                Student pinkStudent = new Student(PawnColor.PINK);
                students.add(pinkStudent);
            }
        }
        int topicId = this.messageHandler.getMessagesUniqueTopic();
        Message message = new Message(ISLAND.getFragment(), OK.getFragment(), topicId);
        this.messageHandler.write(message);
        this.messageHandler.writeOut();
        if (owner==0) {
            island.updateIsland(students, numTowers);
        } else {
            island.updateIsland(students, numTowers, gamer.getColor());
        }
    }
}
